package com.pepris.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pepris.model.system.SysRole;
import com.pepris.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRole> selectPage(Page<SysRole> page, @Param("vo") SysRoleQueryVo roleQueryVo);
}
