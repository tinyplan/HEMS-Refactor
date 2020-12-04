package com.tinyplan.exam.portal.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.form.PortalLoginForm;
import com.tinyplan.exam.entity.form.RegisterForm;
import com.tinyplan.exam.entity.form.UpdateUserDetailForm;
import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.UserType;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController("portalUserController")
@RequestMapping({"/candidate"})
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    /**
     * 用户注册
     *
     * @param registerForm 注册表单
     */
    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody RegisterForm registerForm) {
        // TODO 注册参数校验
        // 先把表单中的信息封装好
        User user = dataInjectService.injectUser(registerForm);
        CandidateDetail detail = dataInjectService.injectCandidateDetail(registerForm);
        userService.register(user, detail);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
    }

    /**
     * 登录表单
     *
     * @param loginForm 登录表单
     */
    @PostMapping("/login")
    public ApiResult<TokenVO> login(@RequestBody PortalLoginForm loginForm) {
        // TODO 监考教师的登录
        TokenVO token = userService.login(loginForm.getUsername(), loginForm.getPassword(), UserType.CANDIDATE);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, token);
    }

    /**
     * 获取用户信息
     *
     * @param request 请求体
     */
    @GetMapping("")
    @Authorization
    public ApiResult<DetailVO> info(HttpServletRequest request) {
        String token = RequestUtil.getToken(request);
        DetailVO userInfo = userService.getUserInfo(token);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, userInfo);
    }

    /**
     * 修改用户信息
     */
    @PatchMapping("")
    @Authorization
    public ApiResult<Object> updateInfo(HttpServletRequest request,
                                        @RequestBody UpdateUserDetailForm form){
        String token = RequestUtil.getToken(request);
        // 将表单中的信息封装成对象
        CandidateDetail detail = dataInjectService.injectCandidateDetail(form);
        userService.updateUserInfo(token, detail);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, null);
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
