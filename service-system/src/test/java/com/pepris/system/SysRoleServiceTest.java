package com.pepris.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pepris.system.service.SysRoleService;
import com.pepris.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest()
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void findAll() {
        List<SysRole> list = sysRoleService.list();
        for (SysRole sysRole : list) {
            System.out.println(sysRole);
        }
    }


    @Test
    public void testInsert() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("SERVICE管理员");
        sysRole.setRoleCode("88");
        boolean b = sysRoleService.save(sysRole);
        System.out.println(b);
    }

    @Test
    public void testUpdate() {
        SysRole sysRole = new SysRole();
        sysRole.setId("1937070633401102338");
        sysRole.setRoleName("service测试");
        boolean b = sysRoleService.updateById(sysRole);
        System.out.println(b);
    }

    @Test
    public void testDelete() {
        SysRole sysRole = new SysRole();
        sysRole.setId("1937070633401102338");
        boolean b = sysRoleService.removeById(sysRole);
        System.out.println(b);
    }

    @Test
    public void testDeleteAll() {
        List<SysRole> list = sysRoleService.list();
        for (SysRole sysRole : list) {
            sysRoleService.removeById(sysRole.getId());
        }
    }

    @Test
    public void testQueryWrapper() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("88");
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRole::getRoleCode, "88");
        List<SysRole> list = sysRoleService.list(queryWrapper);
        for (SysRole sysRole1 : list) {
            System.out.println(sysRole1);
        }
    }


}
