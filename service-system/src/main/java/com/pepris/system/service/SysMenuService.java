package com.pepris.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pepris.model.system.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SysMenuService extends IService<SysMenu>  {
    List<SysMenu> findNodes();

    //删除菜单
    void removeMenuById(String id);
}
