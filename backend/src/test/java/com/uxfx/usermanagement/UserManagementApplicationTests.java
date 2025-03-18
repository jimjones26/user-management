package com.uxfx.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.Import;
import com.uxfx.usermanagement.config.TestConfig;

@SpringBootTest
@ActiveProfiles("test")
@Import({TestConfig.class})
class UserManagementApplicationTests {

    @Test
    void contextLoads() {
        // This will verify that the application context loads successfully
    }
}
