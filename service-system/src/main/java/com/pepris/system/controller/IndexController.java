package com.pepris.system.controller;

import com.pepris.common.result.Result;
import com.pepris.common.utils.JwtHelper;
import com.pepris.common.utils.MD5;
import com.pepris.model.system.SysUser;
import com.pepris.model.vo.LoginVo;
import com.pepris.system.exception.HansException;
import com.pepris.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/system/index")
@Api(tags = "用户登录接口")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        //根据用户名查询数据库
        SysUser sysUser = sysUserService.getUserInfoByName(loginVo.getUsername());
        //如果查询数据为空，提示用户不存在
        if (sysUser == null) {
            throw new HansException(20001, "用户不存在");
        }
        //判断密码是否正确
        String password = loginVo.getPassword();
        String md5Password = MD5.encrypt(password);
        if (!sysUser.getPassword().equals(md5Password)) {
            throw new HansException(20001, "密码错误");
        }
        //判断用户状态，是否可用
        if (sysUser.getStatus() == 0) {
            throw new HansException(20001, "用户已被禁用");
        }
        //根据用户id和名称生成token字符串，通过map进行返回
        String token = JwtHelper.createToken(Long.parseLong(sysUser.getId()), loginVo.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    //获取用户信息
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        //获取请求头token字符串
        String token = request.getHeader("token");
        //从token字符串获取用户名称和id
        String username = JwtHelper.getUsername(token);
        //根据用户名获取用户信息(基本信息+菜单权限+按钮权限)
        Map<String, Object> map = sysUserService.getUserInfo(username);
/*        map.put("roles", "[admin]");
        map.put("name", "admin hs");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");*/
        return Result.ok(map);
    }

    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }


}
