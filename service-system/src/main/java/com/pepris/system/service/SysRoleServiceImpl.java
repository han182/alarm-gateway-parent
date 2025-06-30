package com.pepris.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pepris.model.system.SysMenu;
import com.pepris.model.system.SysRoleMenu;
import com.pepris.model.system.SysUserRole;
import com.pepris.model.vo.AssginRoleVo;
import com.pepris.model.vo.SysRoleQueryVo;
import com.pepris.system.mapper.SysMenuMapper;
import com.pepris.system.mapper.SysRoleMapper;
import com.pepris.model.system.SysRole;
import com.pepris.system.mapper.SysRoleMenuMapper;
import com.pepris.system.mapper.SysUserRoleMapper;
import com.pepris.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //条件分页查询
    //@Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectPage(pageParam, sysRoleQueryVo);
        return pageModel;
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        //获取所有角色
        List<SysRole> roles = baseMapper.selectList(null);
        //根据用户id查询
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        //获取用户已分配的角色
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(queryWrapper);
        //获取用户已分配的角色id
        List<String> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            userRoleIds.add(Long.toString((userRole.getRoleId())));
        }
        //创建返回的Map
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles", roles);
        returnMap.put("userRoleIds", userRoleIds);
        return returnMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除原来分配的角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", assginRoleVo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);
        //获取所有的角色id,添加角色用户关系表
        // 角色id列表
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        for (String roleId : roleIdList) {
            if (roleId != null) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(Long.parseLong(assginRoleVo.getUserId()));
                sysUserRole.setRoleId(Long.parseLong(roleId));
                //保存
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    //根据角色获取菜单
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        QueryWrapper<SysMenu> sysMenuStatus = queryWrapper.eq("status", 1);
        // TODO sysMenuList代表所有菜单对象
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(sysMenuStatus);
        //List<SysMenu> sysMenu = baseMapper.selectList(sysMenuStatus);

        //根据角色id查询 角色分配过的菜单列表 select * from sys_role_menu t where role_id='2'
        QueryWrapper<SysRoleMenu> queryWrapper1 = new QueryWrapper<>();
        QueryWrapper<SysRoleMenu> sysRoleMenuStatus = queryWrapper1.eq("role_id", roleId);
        // TODO sysRoleMenuList代表根据前端传来的用户id，获取到的sys_role_menu中的菜单对象
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(sysRoleMenuStatus);

        //从第二步查询的列表中，获取角色分配的所有菜单的id select menu_id from sys_role_menu t where role_id='2'
        List<String> sysRoleMenuIds = new ArrayList<>();
        //遍历第二步查出的集合，取到菜单id,把取到的id放到sysRoleMenuIds中
        for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
            String menuId = sysRoleMenu.getMenuId();
            sysRoleMenuIds.add(menuId);
        }


        //数据处理：isselect如果菜单选中为true,否则false
        //遍历
        for (SysMenu sysMenu : sysMenuList) {
            //contains代表是否包含，包含代表已分配
            if (sysRoleMenuIds.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);//已分配前端应该显示为被选中
            } else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenuList1 = MenuHelper.buildTree(sysMenuList);
        return sysMenuList1;

    }


}
