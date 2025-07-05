package com.pepris.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pepris.common.result.Result;
import com.pepris.model.vo.AssginRoleVo;
import com.pepris.model.vo.SysRoleQueryVo;
import com.pepris.system.service.SysRoleService;
import com.pepris.model.system.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

/*    @ApiOperation(value = "查询所有")
    @GetMapping("/findAll")
    public List<SysRole> findAll() {
        SysRole sysRole = new SysRole();
        List<SysRole> list = sysRoleService.list();
        return list;
    }

    @ApiOperation(value = "删除")
    @PostMapping("/remove/{id}")
    public boolean removeRole(@PathVariable Long id) {
        boolean b = sysRoleService.removeById(id);
        return b;
    }
 */

    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public Result save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation(value = "修改角色")
    @PostMapping("/update")
    public Result update(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation(value = "删除角色")
    @PostMapping("/remove/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = sysRoleService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    @ApiOperation(value = "根据ID列表删除")
    @PostMapping("/batchRemove")
    public Result batchDelete(@RequestBody List<Long> ids) {
        boolean b = sysRoleService.removeByIds(ids);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }



/*

    @ApiOperation(value = "查询所有")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll() {
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);

    }

    @ApiOperation(value = "删除")
    @PostMapping("/remove/{id}")
    // todo  @PathVariable是路径变量注解，将 URL 路径中的 {id} 绑定到方法参数 id 上
    public Result removeRole(@PathVariable Long id) {
        boolean b = sysRoleService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
*/

    @GetMapping("/{page}/{limit}")
    @ApiOperation(value = "条件分页查询")
    @PreAuthorize("hasAnyAuthority('bnt.sysRole.list')")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable
            Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = false)
            @PathVariable Long limit,
            @ApiParam(name = "sysRoleQueryVo", value = "查询对象", required = true)
            SysRoleQueryVo sysRoleQueryVo) {

        //创建配置对象
        Page<SysRole> pageParam = new Page<>(page, limit);
        //调用service方法
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, sysRoleQueryVo);
        return Result.ok(pageModel);


    }

    @ApiOperation("获取用户的角色数据")
    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        // TODO
        Map<String, Object> roleMap = sysRoleService.getRolesByUserId(Long.parseLong(userId));
        return Result.ok(roleMap);

    }
    @ApiOperation("给用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }

}
