package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.tinyplan.exam.common.service.TokenService;
import com.tinyplan.exam.common.utils.JwtUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.common.utils.RoleUtil;
import com.tinyplan.exam.dao.AdminMapper;
import com.tinyplan.exam.dao.CandidateMapper;
import com.tinyplan.exam.dao.RoleMapper;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.AdminDetailVO;
import com.tinyplan.exam.entity.vo.CandidateDetailVO;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;
import com.tinyplan.exam.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "tokenServiceImpl")
    private TokenService tokenService;

    @Resource(name = "roleMapper")
    private RoleMapper roleMapper;

    @Resource(name = "adminMapper")
    private AdminMapper adminMapper;

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
     * @param type 登录用户类型
     * @return token
     */
    @Override
    public TokenVO login(String username, String password, UserType type) throws UnsupportedEncodingException {
        TokenVO token = null;
        User user = this.getUser(username, type);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ResultStatus.RES_LOGIN_UNKNOWN_USER);
        }
        // 密码错误
        if (!user.getPassword().equalsIgnoreCase(DigestUtil.md5Hex(password))) {
            throw new BusinessException(ResultStatus.RES_LOGIN_WRONG_PASS);
        }
        // 登录成功后，生成token
        token = new TokenVO();
        token.setToken(tokenService.generateToken(user.getId(), user.getRoleId()));
        // 准备详细信息
        DetailVO detail = this.getUserDetail(user, type);
        // 将用户信息存入redis
        tokenService.setValue(token.getToken(), detail);
        return token;
    }

    /**
     * 根据用户类型, 获取用户对象
     *
     * @param username 用户名(用户ID 或 账户名)
     * @param type 用户类型
     * @return 用户基础对象
     */
    private User getUser(String username, UserType type) {
        User user = null;
        // TODO 更换为对应的查询方法
        switch (type){
            case CANDIDATE:
                user = candidateMapper.getCandidateByUsername(username);
                break;
            case INVIGILATOR:

                break;
            case EDU_ADMIN:
            case SYSTEM_ADMIN:
                user = adminMapper.getAdminByUsername(username);
                break;
            default:
                // TODO 这里的状态码有待商榷
                throw new BusinessException();
        }
        return user;
    }

    /**
     * 根据用户类型, 获取详细信息
     *      调用这个方法之前，需要先调用getUser()方法取得user对象, 否则取得的信息可能不全
     * @param user 用户对象
     * @param type 用户类型
     * @return 详细信息
     */
    private DetailVO getUserDetail(User user, UserType type){
        DetailVO detail = null;
        switch (type) {
            case CANDIDATE:
                detail = new CandidateDetailVO(candidateMapper.getCandidateDetail(user.getId()));
                detail.copyValueFromUser(user);
                break;
            case INVIGILATOR:

                break;
            case SYSTEM_ADMIN:
            case EDU_ADMIN:
                detail = new AdminDetailVO();
                detail.copyValueFromUser(user);
                break;
            default:
                // TODO 这里的状态码有待商榷
                throw new BusinessException();
        }
        // 设置角色详细信息
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(user.getRoleId());
        detail.setRoles(roleMapper.getRolesByIds(roleIdList));
        return detail;
    }

    @Override
    public DetailVO getUserInfo(String token) throws UnsupportedEncodingException {
        // 先从token中取出 userId 和 roleId
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        // 获取用户类型
        UserType type = RoleUtil.getUserType(load.getRoleId());
        DetailVO detail = null;
        // 从缓存中查找
        switch (type) {
            case CANDIDATE:
                detail = (CandidateDetailVO) tokenService.getValue(token);
                break;
            case INVIGILATOR:

                break;
            case SYSTEM_ADMIN:
            case EDU_ADMIN:
                detail = (AdminDetailVO) tokenService.getValue(token);
                break;
            default:
                throw new BusinessException();
        }
        // 命中, 直接返回
        if (detail != null) {
            return detail;
        }
        // 未命中, 再次查询数据库
        User user = getUser(load.getUserId(), type);
        if(user == null) {
            throw new BusinessException(ResultStatus.RES_LOGIN_UNKNOWN_USER);
        }
        detail = this.getUserDetail(user, type);
        return detail;
    }

    @Override
    public void logout(String token) {
        // 删除token的时候, 失败了也没有问题(大概), 登录的时候重新生成一个就好了
        tokenService.deleteToken(token);
    }

}
