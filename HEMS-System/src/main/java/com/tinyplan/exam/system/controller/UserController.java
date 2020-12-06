package com.tinyplan.exam.system.controller;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.form.SystemLoginForm;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.UserType;
import com.tinyplan.exam.entity.vo.DetailVO;
import com.tinyplan.exam.entity.vo.TokenVO;
import com.tinyplan.exam.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("systemUserController")
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("/login")
    public ApiResult<TokenVO> login(@RequestBody SystemLoginForm loginForm) {
        // TODO 参数校验
        TokenVO token = userService.login(loginForm.getUsername(), loginForm.getPassword(), UserType.EDU_ADMIN);
        return new ApiResult<>(ResultStatus.RES_SUCCESS, token);
    }

    @GetMapping("/info")
    @Authorization
    public ApiResult<DetailVO> info(HttpServletRequest request) {
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
