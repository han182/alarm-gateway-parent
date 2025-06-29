package com.pepris.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pepris.common.result.Result;
import com.pepris.model.system.SysUser;
import com.pepris.model.vo.SysUserQueryVo;
import com.pepris.system.mapper.SysUserMapper;
import com.pepris.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hhhhhhhhhh
 * @since 2025-06-29
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation(value = "分页获取用户列表")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable
            Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = false)
            @PathVariable Long limit,
            @ApiParam(name = "sysUserQueryVo",value = "查询对象",required = true)
            SysUserQueryVo sysUserQueryVo) {

        //创建配置对象
        Page<SysUser> pageParam = new Page<>(page, limit);
        //调用service方法
        IPage<SysUser> pageModel=sysUserService.selectPage(pageParam,sysUserQueryVo);
        return Result.ok(pageModel);

    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser sysUser){
        boolean b = sysUserService.save(sysUser);
        if(b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    // 用于修改
    @ApiOperation(value = "根据ID获取用户")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/update")
    public Result update(@RequestBody SysUser sysUser){
        sysUserService.updateById(sysUser);
        return Result.ok();
    }
    @ApiOperation(value = "删除用户")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable Long id){
        boolean b = sysUserService.removeById(id);
        if(b){
            return Result.ok();
        }else {
            return Result.fail();
        }

    }
    @ApiOperation(value = "根据ID列表删除")
    @PostMapping("/batchRemove")
    public Result batchDelete(@RequestBody List<Long> ids){
        boolean b = sysUserService.removeByIds(ids);
        if(b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
}

