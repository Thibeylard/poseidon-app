package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
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
    public void Given_getAllBidListURI_When_getRequest_Then_returnAllBidLists() throws Exception {

        //Successful request
        MvcResult response = mockMvc.perform(get("/restApi/bidLists")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @WithMockUser
    public void Given_getByIdBidListURI_When_getRequest_Then_returnSpecifiedBidList() throws Exception {
        //Successful request
        mockMvc.perform(get("/restApi/bidLists/1")
                .accept("application/*"))
                .andExpect(status().isOk());

        // No element with ID 85 error occurred
        mockMvc.perform(get("/restApi/bidLists/85")
                .accept("application/*"))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser
    public void Given_addBidListURI_When_postRequest_Then_returnBidListServiceValue() throws Exception {
        BidListAddDTO body = new BidListAddDTO("account_a", "type_a", 42.3);
        BidListAddDTO invalidBody = new BidListAddDTO("", "type_a", 42.3);
        BidList addedBid = new BidList(body);

        // Successful save

        MvcResult response = mockMvc.perform(post("/restApi/bidLists")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        System.out.println(bidJson);

        assertThat(bidJson)
                .isNotEmpty();

        // Invalid DTO

        mockMvc.perform(post("/restApi/bidLists")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidBody))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void Given_updateBidListURI_When_putRequest_Then_returnUpdatedBidList() throws Exception {
        BidListUpdateDTO body = new BidListUpdateDTO(1, "account_a", "type_a", 42.3);
        BidList updatedBid = new BidList(body);

        // Successful request
        MvcResult response = mockMvc.perform(put("/restApi/bidLists/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();
        System.out.println(bidJson);
        assertThat(bidJson)
                .isNotEmpty();

        // Put ID 86
        mockMvc.perform(put("/restApi/bidLists/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void Given_deleteBidListURI_When_deleteRequest_Then_returnTrue() throws Exception {

        // Successful request
        MvcResult response = mockMvc.perform(delete("/restApi/bidLists/1")
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        System.out.println(bidJson);

        // No element with ID 96 error occurred
        mockMvc.perform(delete("/restApi/bidLists/96")
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
