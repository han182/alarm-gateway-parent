package com.pepris.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pepris.model.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
