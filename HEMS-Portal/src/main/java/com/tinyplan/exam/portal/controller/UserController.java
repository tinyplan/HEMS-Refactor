package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.form.PortalLoginForm;
import com.tinyplan.exam.entity.form.RegisterForm;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;
import com.tinyplan.exam.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController("portalUserController")
@RequestMapping({"/user", "/candidate"})
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody RegisterForm registerForm) {
        // TODO 注册参数校验
        // 先把表单中的信息封装好
        User user = new User();
        user.setAccountName(registerForm.getUsername());
        // 这里先设置为原始密码
        user.setPassword(registerForm.getPassword());
        // 只有学生可以注册
        user.setRoleId("r1003");
        CandidateDetail detail = new CandidateDetail();
        detail.setContact(registerForm.getTelephone());
        detail.setEmail(registerForm.getEmail());
        // 设置头像
        detail.setAvatar("default_admin_avatar.png");
        userService.register(user, detail);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    @PostMapping("/login")
    public ApiResult<TokenVO> login(@RequestBody PortalLoginForm loginForm) throws UnsupportedEncodingException {
        // TODO 监考教师的登录
        TokenVO token = userService.login(loginForm.getUsername(), loginForm.getPassword(), UserType.CANDIDATE);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, token);
    }

    @GetMapping("/info")
    @Authorization
    public ApiResult<DetailVO> info(HttpServletRequest request) throws UnsupportedEncodingException {
        String token = RequestUtil.getToken(request);
        DetailVO userInfo = userService.getUserInfo(token);
        // 这里再找不到的话, 则用户不存在(不太可能发生这种情况)
        if (userInfo == null) {
            throw new BusinessException(ResultStatus.RES_INFO_NOT_EXIST);
        }
        return new ApiResult<>(ResultStatus.RES_SUCCESS, userInfo);
    }

    @PostMapping("/logout")
    @Authorization
    public ApiResult<Object> logout(HttpServletRequest request) {
        String token = RequestUtil.getToken(request);
        // 一般不会出现token为null的情况, 因为前面的拦截器已经检查过
        // token 若为 null, 说明运行到这里的时候刚好失效(老非酋了), 那就不用去删了, token已经自我消亡
        if (token == null) {
            throw new BusinessException(ResultStatus.RES_ILLEGAL_REQUEST);
        }
        userService.logout(token);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }


}
