package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
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
public class BidListRestRepositoryIT {

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

        //Get all BidLists
        mockMvc.perform(get("/restApi/bidLists")
                .accept("application/*"))
                .andExpect(status().isOk());


        //Get BidList 1
        MvcResult response = mockMvc.perform(get("/restApi/bidLists/1")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("bidListId").asInt())
                .isEqualTo(1);

        // No BidList 85 : Not Found
        response = mockMvc.perform(get("/restApi/bidLists/85")
                .accept("application/*"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Add new bidList

        BidList addedBid = new BidList("account_x", "type_x", 65.4);
        BidList invalidBid = new BidList("account_x", "", 65.4);

        response = mockMvc.perform(post("/restApi/bidLists")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedBid))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("account").asText())
                .isEqualTo(addedBid.getAccount());
        assertThat(bodyResponse.get("type").asText())
                .isEqualTo(addedBid.getType());
        assertThat(bodyResponse.get("bidQuantity").asDouble())
                .isEqualTo(addedBid.getBidQuantity());

        // Invalid DTO

        mockMvc.perform(post("/restApi/bidLists")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidBid))
                .with(csrf()))
                .andExpect(status().isBadRequest());


        // Successful partial update on bidlist 1

        BidList updateBid = new BidList("account_a", "type_a", 42.3);

        response = mockMvc.perform(patch("/restApi/bidLists/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateBid))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("bidListId").asInt())
                .isEqualTo(1);
        assertThat(bodyResponse.get("bidQuantity").asDouble())
                .isEqualTo(updateBid.getBidQuantity());

        // Put ID 86
        // Creates a resource identical to bid except with next generated ID
        response = mockMvc.perform(put("/restApi/bidLists/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateBid))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        int putBidId = bodyResponse.get("bidListId").asInt();
        assertThat(bodyResponse.get("account").asText())
                .isEqualTo(updateBid.getAccount());
        assertThat(bodyResponse.get("type").asText())
                .isEqualTo(updateBid.getType());
        assertThat(bodyResponse.get("bidQuantity").asDouble())
                .isEqualTo(updateBid.getBidQuantity());

        // Successful Delete of bidList with last put ID
        response = mockMvc.perform(delete("/restApi/bidLists/" + putBidId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Element already suppressed : error occurred
        mockMvc.perform(delete("/restApi/bidLists/" + putBidId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
