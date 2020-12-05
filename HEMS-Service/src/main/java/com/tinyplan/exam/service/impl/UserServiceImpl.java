package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.tinyplan.exam.common.properties.HEMSProperties;
import com.tinyplan.exam.common.service.TokenService;
import com.tinyplan.exam.common.utils.JwtUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.common.utils.RoleUtil;
import com.tinyplan.exam.dao.CandidateMapper;
import com.tinyplan.exam.dao.RoleMapper;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateBack;
import com.tinyplan.exam.entity.pojo.aliyun.CertificateFront;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;
import com.tinyplan.exam.service.ApiService;
import com.tinyplan.exam.service.ImageService;
import com.tinyplan.exam.service.UserHandlerService;
import com.tinyplan.exam.service.UserService;
import com.tinyplan.exam.service.factory.UserHandlerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "hemsProperties")
    private HEMSProperties hemsProperties;

    @Resource(name = "tokenServiceImpl")
    private TokenService tokenService;

    @Resource(name = "imageServiceImpl")
    private ImageService imageService;

    @Resource(name = "apiServiceImpl")
    private ApiService apiService;

    @Resource(name = "roleMapper")
    private RoleMapper roleMapper;

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Override
    @Transactional
    public void register(User user, CandidateDetail detail) {
        // 先检查是否存在重复用户
        if (candidateMapper.getCandidateByUsername(user.getAccountName()) != null) {
            throw new BusinessException(ResultStatus.RES_REGISTER_EXISTED_USER);
        }
        // 加密密码
        user.setPassword(SecureUtil.md5(user.getPassword()));
        // 生成用户ID
        // 获取考生表中当前最大的ID并 加1
        String prefix = PrefixUtil.getUserPrefix(UserType.CANDIDATE);
        String date = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyyMMdd");
        String maxId = Integer.toString(candidateMapper.getMaxId() + 1);
        String userId = String.join("_", prefix, date, maxId);
        user.setId(userId);
        detail.setId(userId);
        // 插入两张表中
        Integer insertUserCount = candidateMapper.insertCandidate(user);
        Integer insertUserDetailCount = candidateMapper.insertCandidateDetail(detail);
        // 任何一张表插入失败，回滚
        if (insertUserCount + insertUserDetailCount != 2) {
            throw new BusinessException(ResultStatus.RES_REGISTER_FAIL);
        }
    }

    /**
     * 用户登录
     *
     * @param username 用户名(用户ID 或 账户名)
     * @param password 密码
     * @param type     登录用户类型
     * @return token
     */
    @Override
    public TokenVO login(String username, String password, UserType type) {
        UserHandlerService handlerService = UserHandlerFactory.getHandlerService(type);
        if (handlerService == null) {
            throw new BusinessException();
        }
        User user = handlerService.getUser(username);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ResultStatus.RES_LOGIN_UNKNOWN_USER);
        }
        // 密码错误
        if (!user.getPassword().equalsIgnoreCase(DigestUtil.md5Hex(password))) {
            throw new BusinessException(ResultStatus.RES_LOGIN_WRONG_PASS);
        }
        // 登录成功后，生成token
        TokenVO token = new TokenVO();
        token.setToken(tokenService.generateToken(user.getId(), user.getRoleId()));
        // 准备详细信息
        DetailVO detail = handlerService.getUserDetail(user);
        // 获取角色信息
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(user.getRoleId());
        detail.setRoles(roleMapper.getRolesByIds(roleIdList));
        // 将用户信息存入redis
        tokenService.setValue(token.getToken(), detail);
        return token;
    }

    @Override
    public DetailVO getUserInfo(String token) {
        // 先从token中取出 userId 和 roleId
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        // 获取用户类型
        UserType type = RoleUtil.getUserType(load.getRoleId());
        UserHandlerService handlerService = UserHandlerFactory.getHandlerService(type);
        if (handlerService == null) {
            throw new BusinessException();
        }
        DetailVO detail = handlerService.convertDetail(tokenService.getValue(token));
        // 命中, 直接返回
        if (detail != null) {
            return detail;
        }
        // 未命中, 再次查询数据库
        User user = handlerService.getUser(load.getUserId());
        if (user == null) {
            throw new BusinessException(ResultStatus.RES_LOGIN_UNKNOWN_USER);
        }
        detail = handlerService.getUserDetail(user);
        // 这里再找不到的话, 则用户不存在(不太可能发生这种情况)
        if (detail == null) {
            throw new BusinessException(ResultStatus.RES_INFO_NOT_EXIST);
        }
        return detail;
    }

    @Override
    public void updateUserInfo(String token, String accountName, CandidateDetail newDetail) {
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        User user = candidateMapper.getCandidateByUsername(load.getUserId());
        if (user != null) {
            throw new BusinessException(ResultStatus.RES_INFO_EXISTED_ACCOUNT_NAME);
        }
        newDetail.setId(load.getUserId());
        Integer result1 = candidateMapper.updateCandidateDetail(newDetail);
        Integer result2 = candidateMapper.updateAccountName(load.getUserId(), accountName);
        if (result1 + result2 != 2) {
            throw new BusinessException(ResultStatus.RES_INFO_UPDATE_FAILED);
        }
        // 获取新的信息并更新缓存
        this.flushCache(token, UserType.CANDIDATE);
    }

    @Override
    public void updatePassword(String token, String oldPassword, String newPassword) {
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        UserHandlerService handlerService = UserHandlerFactory.getHandlerService(RoleUtil.getUserType(load.getRoleId()));
        // 比对旧密码是否正确
        User user = handlerService.getUser(load.getUserId());
        if (!SecureUtil.md5(oldPassword).equals(user.getPassword())) {
            throw new BusinessException(ResultStatus.RES_INFO_WRONG_OLD_PASSWORD);
        }
        handlerService.updatePassword(load.getUserId(), newPassword);
        // 删除token, 让用户重新登录
        tokenService.deleteToken(token);
    }

    @Override
    public void certificate(HttpServletRequest request, MultipartFile front, MultipartFile back, String realName, String idCard) {
        // 保存两张图片到本地路径
        JwtDataLoad load = JwtUtil.getDataLoad(request);
        String path = this.generateTmpPath(request);
        String frontFilename = load.getUserId() + "_" + front.getOriginalFilename();
        String backFilename = load.getUserId() + "_" + back.getOriginalFilename();
        imageService.saveToLocal(path, frontFilename, front);
        imageService.saveToLocal(path, backFilename, back);
        // 识别
        File imageFront = new File(path + File.separator + frontFilename);
        File imageBack = new File(path + File.separator + backFilename);
        CertificateFront certificateFront = apiService.certificateFront(imageFront);
        CertificateBack certificateBack = apiService.certificateBack(imageBack);
        // 删除缓存的图片
        imageService.deleteLocal(imageFront);
        imageService.deleteLocal(imageBack);
        // 数据校验
        Date end = DateUtil.parse(certificateBack.getEnd_date());
        long interval = DateUtil.between(new Date(), end, DateUnit.DAY, false);
        if (interval < 0) {
            throw new BusinessException(ResultStatus.RES_CERTIFICATE_OUT_OF_DATE);
        }
        if (!realName.equals(certificateFront.getName()) || !idCard.equals(certificateFront.getNum())) {
            throw new BusinessException(ResultStatus.RES_CERTIFICATE_FAILED);
        }
        // 保存数据
        candidateMapper.updateCertificateInfo(load.getUserId(), realName, idCard);
        // 更新缓存
        this.flushCache(RequestUtil.getToken(request), UserType.CANDIDATE);
    }

    @Override
    public void logout(String token) {
        // 删除token的时候, 失败了也没有问题(大概), 登录的时候重新生成一个就好了
        tokenService.deleteToken(token);
    }

    /**
     * 刷新缓存中的信息
     *
     * @param token
     */
    private void flushCache(String token, UserType type) {
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        UserHandlerService handlerService = UserHandlerFactory.getHandlerService(type);
        User user = handlerService.getUser(load.getUserId());
        DetailVO detail = handlerService.getUserDetail(user);
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(user.getRoleId());
        detail.setRoles(roleMapper.getRolesByIds(roleIdList));
        tokenService.setValue(token, detail);
    }

    /**
     * 构建文件保存的路径(项目根路径 + 缓存路径 + 用户ID命名的目录)
     *
     * @return 保存路径
     */
    private String generateTmpPath(HttpServletRequest request) {
        JwtDataLoad load = JwtUtil.getDataLoad(request);
        return request.getServletContext().getRealPath(hemsProperties.getCertificateTmpDir() + load.getUserId());
    }

}