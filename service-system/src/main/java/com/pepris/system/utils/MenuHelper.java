package com.pepris.system.utils;

import com.pepris.model.system.SysMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuHelper {
    //构建树形结构
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //创建集合封装最终数据
        List<SysMenu> trees = new ArrayList<>();
        //遍历菜单集合
        for (SysMenu sysMenu : sysMenuList) {
            //找到递归入口
            if (sysMenu.getParentId().longValue() == 0) {
                trees.add(findChildren(sysMenu, sysMenuList));
            }

        }
        return trees;
    }

    //从跟节点进行递归查询，查询子节点，判断id和parentid是否相同，相同就是他的子节点
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        //数据初始化
        sysMenu.setChildren(new ArrayList<>());
        //遍历递归进行查找
        for (SysMenu it : treeNodes) {
            //获取当前菜单id
            String id = sysMenu.getId();
            long l = Long.parseLong(id);
            //获取所有菜单parentid
            Long parentId = it.getParentId();
            //比对
            if (l == parentId) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it, treeNodes));
            }
        }

        return sysMenu;

    }
}
