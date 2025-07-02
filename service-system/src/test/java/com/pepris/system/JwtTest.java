package com.pepris.system;

import com.pepris.common.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class JwtTest {
    @Test
    public void jwt() {
        String token = JwtHelper.createToken(100L, "admin");
        System.out.println("Generated JWT token: " + token);

        String userId = JwtHelper.getUserId(token);
        System.out.println("User ID from token: " + userId);

        String username = JwtHelper.getUsername(token);
    }
}
