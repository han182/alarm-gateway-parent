package com.pepris.system.controller;

import com.pepris.system.service.SysRoleService;
import com.pepris.system.system.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "查询所有")
    @GetMapping("/findAll")
    public List<SysRole> findAll(){
        SysRole sysRole = new SysRole();
        List<SysRole> list = sysRoleService.list();
        return list;
    }
    @ApiOperation(value = "删除")
    @PostMapping("/remove/{id}")
    public boolean removeRole(@PathVariable Long id){
        boolean b = sysRoleService.removeById(id);
        return b;
    }
}
