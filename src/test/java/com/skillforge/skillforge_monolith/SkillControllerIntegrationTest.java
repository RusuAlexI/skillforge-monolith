package com.skillforge.skillforge_monolith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillforge.skillforge_monolith.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class SkillControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSkill_ShouldReturn201_WhenValidRequest() throws Exception {
        // First register a user
        String registerJson = "{\"name\": \"Test User\", \"email\": \"skilltest@example.com\", \"password\": \"password123\"}";
        String registerResponse = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String userId = objectMapper.readTree(registerResponse).get("id").asText();

        // Login to get token
        String loginJson = "{\"email\": \"skilltest@example.com\", \"password\": \"password123\"}";

        String loginResponse = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(loginResponse).get("token").asText();

        // Create a skill
        String skillJson = "{\"name\": \"Java\", \"category\": \"Programming\", \"description\": \"Learning Java\"}";
        mockMvc.perform(post("/api/skills")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(skillJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.category").value("Programming"));
    }

    @Test
    void createSkill_ShouldReturn400_WhenNameBlank() throws Exception {
        // Register and login first (you'll extract this to a helper method)
        String token = registerAndLogin("blanktest@example.com", "password123");

        String skillJson = "{\"name\": \"\", \"category\": \"Programming\", \"description\": \"Test\"}";
        mockMvc.perform(post("/api/skills")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(skillJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[?(@.field == 'name')]").exists());
    }

    @Test
    void getSkill_ShouldReturn404_WhenNotFound() throws Exception {
        String token = registerAndLogin("notfoundtest@example.com", "password123");

        mockMvc.perform(get("/api/skills/non-existent-id")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    // Helper method
    private String registerAndLogin(String email, String password) throws Exception {
        String registerJson = String.format(
                "{\"name\": \"Test\", \"email\": \"%s\", \"password\": \"%s\"}", email, password);
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson));

        String loginJson = String.format(
                "{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("token").asText();
    }
}