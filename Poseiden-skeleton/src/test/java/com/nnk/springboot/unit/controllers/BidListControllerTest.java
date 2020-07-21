package com.nnk.springboot.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.services.BidListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

        when(bidListService.findAll()).thenReturn(allBids);

        MvcResult response = mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andReturn();

        String bidListJson = response.getResponse().getContentAsString();

        assertThat(bidListJson).isEqualTo(
                objectMapper.writeValueAsString(allBids)
        );


    }

    @Test
    @WithMockUser
    public void Given_getByIdBidListURI_When_getRequest_Then_returnSpecifiedBidList() throws Exception {
        BidList askedBid = new BidList("account_a", "type_a", 42.3);

        when(bidListService.findById(2)).thenReturn(askedBid);

        MvcResult response = mockMvc.perform(get("/bidList")
                .param("bidListId", "2"))
                .andExpect(status().isOk())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(askedBid)
        );
    }

    @Test
    @WithMockUser
    public void Given_addBidListURI_When_postRequest_Then_returnAddedBidList() throws Exception {
        BidListAddDTO bidForm = new BidListAddDTO("account_a", "type_a", 42.3);
        BidList addedBid = new BidList(bidForm);

        when(bidListService.save(any(BidListAddDTO.class))).thenReturn(addedBid);

        MvcResult response = mockMvc.perform(post("/bidList/add")
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bidForm))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(addedBid)
        );
    }

    @Test
    @WithMockUser
    public void Given_updateBidListURI_When_putRequest_Then_returnUpdatedBidList() throws Exception {
        BidList updatedBid = new BidList("account_a", "type_a", 42.3);

        when(bidListService.update(1, any(BidList.class))).thenReturn(updatedBid);

        MvcResult response = mockMvc.perform(put("/bidList/update")
                .param("bidListId", "1")
                .param("account", updatedBid.getAccount())
                .param("type", updatedBid.getType())
                .param("bidQuantity", updatedBid.getBidQuantity().toString()))
                .andExpect(status().isOk())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(updatedBid)
        );
    }

    @Test
    @WithMockUser
    public void Given_deleteBidListURI_When_deleteRequest_Then_returnTrue() throws Exception {
        doNothing().when(bidListService).delete(1);

        MvcResult response = mockMvc.perform(delete("/bidList/delete")
                .param("bidListId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String bidJson = response.getResponse().getContentAsString();

        assertThat(bidJson).isEqualTo(
                objectMapper.writeValueAsString(true)
        );
    }
}
