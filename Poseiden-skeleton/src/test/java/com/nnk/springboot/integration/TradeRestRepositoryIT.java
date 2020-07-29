package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Trade;
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
public class TradeRestRepositoryIT {

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

        //Get all Trades
        mockMvc.perform(get("/restApi/trades")
                .accept("application/*"))
                .andExpect(status().isOk());


        //Get Trade 1
        MvcResult response = mockMvc.perform(get("/restApi/trades/1")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("tradeId").asInt())
                .isEqualTo(1);

        // No Trade 85 : Not Found
        response = mockMvc.perform(get("/restApi/trades/85")
                .accept("application/*"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Add new trade

        Trade addedTrade = new Trade("account_x", "type_x");
        Trade invalidTrade = new Trade("account_x", "");

        response = mockMvc.perform(post("/restApi/trades")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedTrade))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("account").asText())
                .isEqualTo(addedTrade.getAccount());
        assertThat(bodyResponse.get("type").asText())
                .isEqualTo(addedTrade.getType());

        // Invalid DTO

        mockMvc.perform(post("/restApi/trades")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidTrade))
                .with(csrf()))
                .andExpect(status().isBadRequest());


        // Successful partial update on trade 1

        Trade updateTrade = new Trade("account_a", "type_a");

        response = mockMvc.perform(patch("/restApi/trades/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateTrade))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("tradeId").asInt())
                .isEqualTo(1);
        assertThat(bodyResponse.get("account").asText())
                .isEqualTo(updateTrade.getAccount());
        assertThat(bodyResponse.get("type").asText())
                .isEqualTo(updateTrade.getType());

        // Put ID 86
        // Creates a resource identical to trade except with next generated ID
        response = mockMvc.perform(put("/restApi/trades/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateTrade))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        int putTradeId = bodyResponse.get("tradeId").asInt();
        assertThat(bodyResponse.get("account").asText())
                .isEqualTo(updateTrade.getAccount());
        assertThat(bodyResponse.get("type").asText())
                .isEqualTo(updateTrade.getType());

        // Successful Delete of trade with last put ID
        response = mockMvc.perform(delete("/restApi/trades/" + putTradeId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Element already suppressed : error occurred
        mockMvc.perform(delete("/restApi/trades/" + putTradeId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
