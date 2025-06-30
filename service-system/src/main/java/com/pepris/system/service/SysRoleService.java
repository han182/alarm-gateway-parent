package com.pepris.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pepris.model.system.SysMenu;
import com.pepris.model.system.SysRole;
import com.pepris.model.vo.AssginRoleVo;
import com.pepris.model.vo.SysRoleQueryVo;

import java.util.List;
import java.util.Map;

public interface SysRoleService extends IService<SysRole> {
    //条件分页查询
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo);

    // 获取用户的角色数据
    Map<String, Object> getRolesByUserId(Long userId);

    //给用户分配角色
    void doAssign(AssginRoleVo assginRoleVo);

    List<SysMenu> findSysMenuByRoleId(Long roleId);
}
