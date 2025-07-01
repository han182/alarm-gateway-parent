package com.pepris.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pepris.model.system.SysMenu;
import com.pepris.model.vo.AssginMenuVo;
import com.pepris.model.vo.RouterVo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findNodes();

    //删除菜单
    void removeMenuById(String id);

    //给角色分配菜单
    void doAssign(AssginMenuVo assginMenuVo);
    //根据userid查询菜单权限
    List<RouterVo> getUserMenuList(String id);
    //根据userid查询按钮权限
    List<String> getUserButtonList(String id);
}
