package com.nnk.springboot.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.services.BidListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unitTest")
public class BidListControllerTest {

    private MockMvc mockMvc;
    // Beans
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private BidListService bidListService;
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
        Collection<BidList> allBids = new ArrayList<>();
        allBids.add(new BidList("account_a", "type_a", 42.3));
        allBids.add(new BidList("account_b", "type_b", 86.52));
        allBids.add(new BidList("account_c", "type_c", 14.2));

        //Successful request

        when(bidListService.findAll()).thenReturn(allBids);

        MvcResult response = mockMvc.perform(get("/bidList/list")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String bidListJson = response.getResponse().getContentAsString();

        assertThat(bidListJson).isEqualTo(
                objectMapper.writeValueAsString(allBids)
        );

        // Database error occurred
        doThrow(DataRetrievalFailureException.class).when(bidListService).findAll();

        mockMvc.perform(get("/bidList/list")
                .accept("application/json"))
                .andExpect(status().isInternalServerError());

    }

    @Test
    @WithMockUser
    public void Given_getByIdBidListURI_When_getRequest_Then_returnSpecifiedBidList() throws Exception {
        BidList askedBid = new BidList("account_a", "type_a", 42.3);

        //Successful request

        when(bidListService.findById(2)).thenReturn(askedBid);

        MvcResult response = mockMvc.perform(get("/bidList")
                .accept("application/json")
                .param("bidListId", "2"))
                .andExpect(status().isOk())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(askedBid)
        );

        // No element with ID 2 error occurred
        doThrow(IllegalArgumentException.class).when(bidListService).findById(2);

        mockMvc.perform(get("/bidList")
                .accept("application/json")
                .param("bidListId", "2"))
                .andExpect(status().isNotFound());

        // Database error occurred
        doThrow(DataRetrievalFailureException.class).when(bidListService).findById(2);

        mockMvc.perform(get("/bidList")
                .accept("application/json")
                .param("bidListId", "2"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void Given_addBidListURI_When_postRequest_Then_returnBidListServiceValue() throws Exception {
        BidListAddDTO body = new BidListAddDTO("account_a", "type_a", 42.3);
        BidList addedBid = new BidList(body);

        // Successful save

        when(bidListService.save(any(BidListAddDTO.class))).thenReturn(addedBid);

        MvcResult response = mockMvc.perform(post("/bidList/add")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(addedBid)
        );

        // Database error occurred
        doThrow(DataIntegrityViolationException.class).when(bidListService).save(any(BidListAddDTO.class));

        mockMvc.perform(post("/bidList/add")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void Given_updateBidListURI_When_putRequest_Then_returnUpdatedBidList() throws Exception {
        BidListUpdateDTO body = new BidListUpdateDTO(1, "account_a", "type_a", 42.3);
        BidList updatedBid = new BidList(body);

        // Successful request

        when(bidListService.update(any(BidListUpdateDTO.class))).thenReturn(updatedBid);

        MvcResult response = mockMvc.perform(put("/bidList/update")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(updatedBid)
        );

        // No element with ID 1 error occurred

        doThrow(IllegalArgumentException.class).when(bidListService).update(any(BidListUpdateDTO.class));

        mockMvc.perform(put("/bidList/update")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isNotFound());

        // Database error occurred

        doThrow(DataRetrievalFailureException.class).when(bidListService).update(any(BidListUpdateDTO.class));

        mockMvc.perform(put("/bidList/update")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body))
                .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void Given_deleteBidListURI_When_deleteRequest_Then_returnTrue() throws Exception {

        // Successful request

        doNothing().when(bidListService).delete(1);

        MvcResult response = mockMvc.perform(delete("/bidList/delete")
                .accept("application/json")
                .param("bidListId", "1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(true)
        );

        // No element with ID 1 error occurred

        doThrow(IllegalArgumentException.class).when(bidListService).delete(1);

        mockMvc.perform(delete("/bidList/delete")
                .accept("application/json")
                .param("bidListId", "1")
                .with(csrf()))
                .andExpect(status().isNotFound());

        // Database error occurred

        doThrow(DataRetrievalFailureException.class).when(bidListService).delete(1);

        mockMvc.perform(delete("/bidList/delete")
                .accept("application/json")
                .param("bidListId", "1")
                .with(csrf()))
                .andExpect(status().isInternalServerError());
    }
}
