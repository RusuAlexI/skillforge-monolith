package com.skillforge.skillforge_monolith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
//@TestContainers
class SkillControllerIntegrationTest {
    // Use Testcontainers for PostgreSQL as we'll do properly tomorrow
    // For today, just test with H2 by switching profile
}