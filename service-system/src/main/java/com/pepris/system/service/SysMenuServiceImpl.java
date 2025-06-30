package com.pepris.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pepris.model.system.SysMenu;
import com.pepris.system.exception.HansException;
import com.pepris.system.mapper.SysMenuMapper;
import com.pepris.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    //菜单列表。树形
    public List<SysMenu> findNodes() {
        //获取所有菜单
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        //所有菜单数据转换成要求的数据格式
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);
        return result;
    }

    //删除菜单
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
}
