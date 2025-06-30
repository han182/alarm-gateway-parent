package com.pepris.system.controller;

import com.pepris.common.result.Result;
import com.pepris.model.system.SysMenu;
import com.pepris.model.vo.AssginMenuVo;
import com.pepris.model.vo.AssginRoleVo;
import com.pepris.system.service.SysMenuService;
import com.pepris.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("获取菜单")
    @GetMapping("/findNodes")
    public Result findNodes() {
        List<SysMenu> sysMenuList= sysMenuService.findNodes();
        return Result.ok(sysMenuList);

    }

    @ApiOperation("新增菜单")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu) {
        boolean b = sysMenuService.save(sysMenu);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }


    }

    @ApiOperation("修改菜单")
    @PostMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu) {
        boolean b = sysMenuService.updateById(sysMenu);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("删除菜单")
    @PostMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        boolean b = sysMenuService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> list= sysRoleService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }


}
