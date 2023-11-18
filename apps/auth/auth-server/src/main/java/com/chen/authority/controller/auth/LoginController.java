package com.chen.authority.controller.auth;

import com.chen.authority.biz.service.auth.CampusStuService;
import com.chen.authority.biz.service.auth.UserRoleService;
import com.chen.authority.biz.service.auth.UserService;
import com.chen.authority.biz.service.auth.ValidateCodeService;
import com.chen.authority.biz.service.auth.impl.AuthManager;
import com.chen.authority.dto.auth.LoginDTO;
import com.chen.authority.dto.auth.LoginParamDTO;
import com.chen.authority.dto.auth.StuRegisterDTO;
import com.chen.authority.entity.auth.CampusStu;
import com.chen.authority.entity.auth.User;
import com.chen.authority.entity.auth.UserRole;
import com.chen.base.BaseController;
import com.chen.base.R;
import com.chen.dozer.DozerUtils;
import com.chen.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录（认证）控制器
 */
@RestController
@RequestMapping("/anno")
@Api(tags = "登录控制器",value = "LoginController")
public class LoginController extends BaseController{

    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private AuthManager authManager;
    @Autowired
    private DozerUtils dozer;
    @Autowired
    private UserService userService;
    @Autowired
    private CampusStuService campusStuService;
    @Autowired
    private UserRoleService userRoleService;



    //为前端系统生成验证码
    @GetMapping(value = "/captcha",produces = "image/png")
    @ApiOperation(notes = "验证码 返回的Captcha-key在响应头",value = "验证码")
    @SysLog("生成验证码")
    public void captcha(HttpServletResponse response) throws IOException{
        validateCodeService.create(response);
    }

    //登录认证
    @PostMapping("/login")
    @ApiOperation(notes = "登录",value = "登录")
    @SysLog("登录")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO loginParamDTO){
//        //校验验证码是否正确
//        boolean check = validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());
//        if(check){
//            //验证码校验通过，执行具体的登录认证逻辑
            R<LoginDTO> r = authManager.login(loginParamDTO.getAccount(),loginParamDTO.getPassword());
            return r;
//        }
        //验证码校验不通过，直接返回
//        return this.success(null);
    }

//    //校验验证码
//    @PostMapping("/check")
//    @ApiOperation(notes = "校验验证码",value = "校验验证码")
//    public boolean check(@RequestBody LoginParamDTO loginParamDTO){
//        //校验验证码是否正确
//        return validateCodeService.check(loginParamDTO.getKey(),loginParamDTO.getCode());
//    }

    //学生注册
    @PostMapping("/stu/register")
    @ApiOperation(notes = "学生注册",value = "学生注册")
    @SysLog("学生注册")
    @Transactional
    public R<StuRegisterDTO> stuRegister(@RequestBody @Validated StuRegisterDTO stuRegisterDTO){
        User user = dozer.map(stuRegisterDTO, User.class);
        User res = userService.saveUser(user);

        CampusStu campusStu = dozer.map(stuRegisterDTO, CampusStu.class);
        campusStu.setId(res.getId());
        campusStuService.save(campusStu);
        userRoleService.save(new UserRole(1175448531104694369L, res.getId()));

        stuRegisterDTO.setPassword("");
        stuRegisterDTO.setId(campusStu.getId());
        return R.success(stuRegisterDTO);
    }

}
