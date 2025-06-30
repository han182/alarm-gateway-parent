package com.pepris.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pepris.model.system.SysUser;
import com.pepris.model.vo.SysUserQueryVo;
import com.pepris.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    //条件分页查询
    //@Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> pageModel = baseMapper.selectPage(pageParam, sysUserQueryVo);
        return pageModel;
    }

    //更改用户状态
    @Override
    public void updateStatus(Long id,Integer status) {
        //根据id查询
        SysUser sysUser = baseMapper.selectById(id);
        //设置修改的状态
        sysUser.setStatus(status);
        //调用方法修改
        baseMapper.updateById(sysUser);
    }

    //根据用户名称查询
    @Override
    public SysUser getUserInfoByName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        SysUser sysUser = baseMapper.selectOne(queryWrapper);
        return sysUser;
    }
}
