package com.pepris.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pepris.system.mapper.SysRoleMapper;
import com.pepris.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest()
//@RunWith(SpringRunner.class)
public class SysRoleMapperTest {

    //@Resource
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void testSelectList() {
        System.out.println("TEST...");
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }

    }
    @Test
    public void testInsert() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色");
        sysRole.setRoleCode("123");
        sysRole.setDescription("测试添加");
        int i = sysRoleMapper.insert(sysRole);
        System.out.println(i);
        System.out.println(sysRole.getId());


    }

    @Test
    public void testUpdate() {
        SysRole sysRole = new SysRole();
        sysRole.setId("1");
        sysRole.setDescription("测试修改系统管理员");
        int i = sysRoleMapper.updateById(sysRole);
        System.out.println(i);



    }

    @Test
    public void testDelete() {
        int i = sysRoleMapper.deleteById("1937050474586214401");
        System.out.println(i);
    }

    @Test
    public void testDeleteBatchIDs() {
        int i = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(i);
    }

    @Test
    public void testQueryWrapper() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>();
        queryWrapper.eq("role_name", "测试角色");
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }
    }
}
