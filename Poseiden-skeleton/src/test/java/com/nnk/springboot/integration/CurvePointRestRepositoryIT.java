package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
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
import org.tinylog.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("testH2")
public class CurvePointRestRepositoryIT {

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

        //Get all CurvePoints
        mockMvc.perform(get("/restApi/curvePoints")
                .accept("application/*"))
                .andExpect(status().isOk());


        //Get CurvePoint 1
        MvcResult response = mockMvc.perform(get("/restApi/curvePoints/1")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

        Logger.debug(response.getResponse().getContentAsString());

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);

        // No CurvePoint 85 : Not Found
        response = mockMvc.perform(get("/restApi/curvePoints/85")
                .accept("application/*"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Add new curvePointList

        CurvePoint addedCurvePoint = new CurvePoint(5, 3.6, 4.98);
        CurvePoint invalidCurvePoint = new CurvePoint(5, -3.2, 41.2);

        response = mockMvc.perform(post("/restApi/curvePoints")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedCurvePoint))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("curveId").asInt())
                .isEqualTo(addedCurvePoint.getCurveId());
        assertThat(bodyResponse.get("term").asDouble())
                .isEqualTo(addedCurvePoint.getTerm());
        assertThat(bodyResponse.get("value").asDouble())
                .isEqualTo(addedCurvePoint.getValue());

        // Invalid DTO

        mockMvc.perform(post("/restApi/curvePoints")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidCurvePoint))
                .with(csrf()))
                .andExpect(status().isBadRequest());


        // Successful partial update on curvePointlist 1

        CurvePoint updateCurvePoint = new CurvePoint(1, 3.6, 4.98);

        response = mockMvc.perform(patch("/restApi/curvePoints/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateCurvePoint))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);
        assertThat(bodyResponse.get("term").asDouble())
                .isEqualTo(updateCurvePoint.getTerm());
        assertThat(bodyResponse.get("value").asDouble())
                .isEqualTo(updateCurvePoint.getValue());

        // Put ID 86
        // Creates a resource identical to curvePoint except with next generated ID
        response = mockMvc.perform(put("/restApi/curvePoints/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateCurvePoint))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());

        int putCurvePointId = bodyResponse.get("id").asInt();

        assertThat(bodyResponse.get("curveId").asInt())
                .isEqualTo(updateCurvePoint.getCurveId());
        assertThat(bodyResponse.get("term").asDouble())
                .isEqualTo(updateCurvePoint.getTerm());
        assertThat(bodyResponse.get("value").asDouble())
                .isEqualTo(updateCurvePoint.getValue());

        // Successful Delete of curvePointList with last put ID
        response = mockMvc.perform(delete("/restApi/curvePoints/" + putCurvePointId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Element already suppressed : error occurred
        mockMvc.perform(delete("/restApi/curvePoints/" + putCurvePointId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
