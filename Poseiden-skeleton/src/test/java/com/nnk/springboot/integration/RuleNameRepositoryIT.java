package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
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
public class RuleNameRepositoryIT {

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
    @WithMockUser
    public void setOfOperations() throws Exception {

        JsonNode bodyResponse;

        //Get all RuleNames
        mockMvc.perform(get("/restApi/rules")
                .accept("application/*"))
                .andExpect(status().isOk());


        //Get RuleName 1
        MvcResult response = mockMvc.perform(get("/restApi/rules/1")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);

        // No RuleName 85 : Not Found
        response = mockMvc.perform(get("/restApi/rules/85")
                .accept("application/*"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Add new ruleNameList

        RuleName addedRuleName = new RuleName("name4", "description4", "json4", "template4", "sqlStr4", "sqlPart4");
        RuleName invalidRuleName = new RuleName("name4", "", "json4", "template4", "sqlStr4", "sqlPart4");

        response = mockMvc.perform(post("/restApi/rules")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedRuleName))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("name").asText())
                .isEqualTo(addedRuleName.getName());
        assertThat(bodyResponse.get("description").asText())
                .isEqualTo(addedRuleName.getDescription());
        assertThat(bodyResponse.get("json").asText())
                .isEqualTo(addedRuleName.getJson());
        assertThat(bodyResponse.get("template").asText())
                .isEqualTo(addedRuleName.getTemplate());
        assertThat(bodyResponse.get("sqlStr").asText())
                .isEqualTo(addedRuleName.getSqlStr());
        assertThat(bodyResponse.get("sqlPart").asText())
                .isEqualTo(addedRuleName.getSqlPart());

        // Invalid DTO

        mockMvc.perform(post("/restApi/rules")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidRuleName))
                .with(csrf()))
                .andExpect(status().isBadRequest());


        // Successful partial update on ruleNamelist 1

        RuleName updateRuleName = new RuleName("newName", "description1", "json1", "newTemplate", "sqlStr1", "sqlPart1");

        response = mockMvc.perform(patch("/restApi/rules/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateRuleName))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);
        assertThat(bodyResponse.get("name").asText())
                .isEqualTo(updateRuleName.getName());
        assertThat(bodyResponse.get("description").asText())
                .isEqualTo(updateRuleName.getDescription());
        assertThat(bodyResponse.get("json").asText())
                .isEqualTo(updateRuleName.getJson());
        assertThat(bodyResponse.get("template").asText())
                .isEqualTo(updateRuleName.getTemplate());
        assertThat(bodyResponse.get("sqlStr").asText())
                .isEqualTo(updateRuleName.getSqlStr());
        assertThat(bodyResponse.get("sqlPart").asText())
                .isEqualTo(updateRuleName.getSqlPart());

        // Put ID 86
        // Creates a resource identical to ruleName except with next generated ID
        response = mockMvc.perform(put("/restApi/rules/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateRuleName))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());

        int putRuleNameId = bodyResponse.get("id").asInt();

        assertThat(bodyResponse.get("name").asText())
                .isEqualTo(updateRuleName.getName());
        assertThat(bodyResponse.get("description").asText())
                .isEqualTo(updateRuleName.getDescription());
        assertThat(bodyResponse.get("json").asText())
                .isEqualTo(updateRuleName.getJson());
        assertThat(bodyResponse.get("template").asText())
                .isEqualTo(updateRuleName.getTemplate());
        assertThat(bodyResponse.get("sqlStr").asText())
                .isEqualTo(updateRuleName.getSqlStr());
        assertThat(bodyResponse.get("sqlPart").asText())
                .isEqualTo(updateRuleName.getSqlPart());


        // Successful Delete of ruleNameList with last put ID
        response = mockMvc.perform(delete("/restApi/rules/" + putRuleNameId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Element already suppressed : error occurred
        mockMvc.perform(delete("/restApi/rules/" + putRuleNameId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
