package com.pepris.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pepris.system.mapper.SysRoleMapper;
import com.pepris.system.system.SysRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
