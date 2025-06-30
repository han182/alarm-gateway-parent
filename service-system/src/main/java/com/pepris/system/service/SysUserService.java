package com.pepris.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pepris.model.system.SysRole;
import com.pepris.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pepris.model.vo.SysRoleQueryVo;
import com.pepris.model.vo.SysUserQueryVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hhhhhhhhhh
 * @since 2025-06-29
 */
public interface SysUserService extends IService<SysUser> {
    // 用户列表
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo);

    //更改用户状态
    void updateStatus(Long id,Integer status);


}
