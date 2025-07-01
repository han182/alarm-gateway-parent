package com.pepris.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pepris.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据userid查找菜单权限数据
    List<SysMenu> findMenuListUserId(@Param("userId") String userId);
}
