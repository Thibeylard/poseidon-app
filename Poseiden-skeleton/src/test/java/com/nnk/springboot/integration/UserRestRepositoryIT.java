package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("testH2")
public class UserRestRepositoryIT {

    private MockMvc mockMvc;
    // Beans
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void operationsForbiddenForClassicUser() throws Exception {
        // GET FORBIDDEN
        mockMvc.perform(get("/restApi/users")
                .accept("application/*"))
                .andExpect(status().isForbidden());


        MvcResult response = mockMvc.perform(get("/restApi/users/1")
                .accept("application/*"))
                .andExpect(status().isForbidden())
                .andReturn();

        // POST FORBIDDEN
        User addedUser = new User("User WithNameD", "userD", "heLLoW0r!d", "USER");

        response = mockMvc.perform(post("/restApi/users")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedUser))
                .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();


        // PATCH FORBIDDEN
        User updateUser = new User("User WithOtherName", "userOther", "heLLoW0r!d", "USER");

        response = mockMvc.perform(patch("/restApi/users/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateUser))
                .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();


        // PUT FORBIDDEN
        response = mockMvc.perform(put("/restApi/users/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateUser))
                .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();

        // DELETE FORBIDDEN
        response = mockMvc.perform(delete("/restApi/users/1")
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void setOfOperationsForAdmin() throws Exception {

        JsonNode bodyResponse;

        //Get all Users
        mockMvc.perform(get("/restApi/users")
                .accept("application/*"))
                .andExpect(status().isOk());


        //Get User 1
        MvcResult response = mockMvc.perform(get("/restApi/users/1")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);

        // No User 85 : Not Found
        response = mockMvc.perform(get("/restApi/users/85")
                .accept("application/*"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Add new user

        User addedUser = new User("User WithNameD", "userD", "heLLoW0r!d", "USER");
        User invalidUser = new User("User WithNameD", "userD", "helloworld", "USER");

        response = mockMvc.perform(post("/restApi/users")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedUser))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        int postUserId = bodyResponse.get("id").asInt();
        assertThat(bodyResponse.get("fullname").asText())
                .isEqualTo(addedUser.getFullname());
        assertThat(bodyResponse.get("username").asText())
                .isEqualTo(addedUser.getUsername());
        assertThat(bodyResponse.get("password").asText())
                .isEqualTo(addedUser.getPassword());
        assertThat(bodyResponse.get("role").asText())
                .isEqualTo(addedUser.getRole());

        // Invalid DTO

        mockMvc.perform(post("/restApi/users")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidUser))
                .with(csrf()))
                .andExpect(status().isBadRequest());


        // Successful partial update on added user

        User updateUser = new User("User WithOtherName", "userOther", "heLLoW0r!d", "USER");

        response = mockMvc.perform(patch("/restApi/users/" + postUserId)
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateUser))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(postUserId);
        assertThat(bodyResponse.get("fullname").asText())
                .isEqualTo(updateUser.getFullname());
        assertThat(bodyResponse.get("username").asText())
                .isEqualTo(updateUser.getUsername());
        assertThat(bodyResponse.get("password").asText())
                .isEqualTo(updateUser.getPassword());
        assertThat(bodyResponse.get("role").asText())
                .isEqualTo(updateUser.getRole());

        // Put ID 86
        // Creates a resource identical to user except with next generated ID
        response = mockMvc.perform(put("/restApi/users/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateUser))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());

        int putUserId = bodyResponse.get("id").asInt();

        assertThat(bodyResponse.get("id").asInt())
                .isNotEqualTo(postUserId);
        assertThat(bodyResponse.get("fullname").asText())
                .isEqualTo(updateUser.getFullname());
        assertThat(bodyResponse.get("username").asText())
                .isEqualTo(updateUser.getUsername());
        assertThat(bodyResponse.get("password").asText())
                .isEqualTo(updateUser.getPassword());
        assertThat(bodyResponse.get("role").asText())
                .isEqualTo(updateUser.getRole());

        // Successful Delete of user with last put ID
        response = mockMvc.perform(delete("/restApi/users/" + putUserId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Element already suppressed : error occurred
        mockMvc.perform(delete("/restApi/users/" + putUserId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
