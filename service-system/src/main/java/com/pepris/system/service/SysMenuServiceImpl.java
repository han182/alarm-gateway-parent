package com.pepris.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pepris.common.result.Result;
import com.pepris.model.system.SysMenu;
import com.pepris.model.system.SysRoleMenu;
import com.pepris.model.vo.AssginMenuVo;
import com.pepris.model.vo.RouterVo;
import com.pepris.system.exception.HansException;
import com.pepris.system.mapper.SysMenuMapper;
import com.pepris.system.mapper.SysRoleMenuMapper;
import com.pepris.system.utils.MenuHelper;
import com.pepris.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    //菜单列表。树形
    public List<SysMenu> findNodes() {
        //获取所有菜单
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        //所有菜单数据转换成要求的数据格式
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);
        return result;
    }

    // 删除菜单
    @Override
    public void removeMenuById(String id) {
        //查询当前删除菜单下面是否有子菜单
        //根据id=parentid
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer i = baseMapper.selectCount(queryWrapper);
        if (i > 0) {
            throw new HansException(201, "请先删除子菜单");
        }
        baseMapper.deleteById(id);
    }

    // 给角色分配菜单
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //根据角色id删除已分配的权限
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        QueryWrapper<SysRoleMenu> roleId = queryWrapper.eq("role_id", assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(roleId);
        //遍历菜单ID列表，一个个添加
        for (String menuId : assginMenuVo.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            //添加新权限
            sysRoleMenuMapper.insert(sysRoleMenu);

        }


    }

    //根据userid查询菜单权限
    @Override
    public List<RouterVo> getUserMenuList(String userId) {
        List<SysMenu> sysMenuList = null;
        // admin是超级管理员，能操作所有内容
        //判断userid等于1代表超级管理员，查询所有权限数据
        if ("1".equals(userId)) {
            QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
            sysMenuQueryWrapper.eq("status", 1);
            sysMenuQueryWrapper.orderByAsc("sort_value");
            sysMenuList = sysMenuMapper.selectList(sysMenuQueryWrapper);

        } else {
            //如果userid不是1，代表其他类型用户，查询这个用户权限
            sysMenuList = baseMapper.findMenuListUserId(userId);

        }
        //构建树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        //转换成前端要求的格式
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;

    }

    //根据userid查询按钮权限
    @Override
    public List<String> getUserButtonList(String userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if ("1".equals(userId)) {
            sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            sysMenuList = sysMenuMapper.findMenuListUserId(userId);
        }
        //创建返回的集合
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getType() == 2){
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }
        return permissionList;
    }
}
