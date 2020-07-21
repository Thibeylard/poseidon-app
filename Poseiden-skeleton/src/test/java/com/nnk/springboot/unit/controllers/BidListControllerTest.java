package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.domain.BidList;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    public void getAll() throws Exception {
        ArrayList<BidList> allBids = new ArrayList<>();
        BidList bidA = new BidList("account_a", "type_a", 41.5);
        BidList bidB = new BidList("account_b", "type_b", 14.3);
        allBids.add(bidA);
        allBids.add(bidB);
        when(bidListService.findAll()).thenReturn(allBids);

        this.mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("allBids",
                        contains(bidA, bidB)));
    }

    @Test
    @WithMockUser
    public void addAndValidate() throws Exception {
        BidList bidA = new BidList("account_a", "type_a", 41.5);

        when(bidListService.findById(1)).thenReturn(bidA);

        this.mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        this.mockMvc.perform(post("/bidList/validate")
                .param("account", "")
                .param("type", bidA.getType())
                .param("bidQuantity", bidA.getBidQuantity().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasErrors());

        when(bidListService.save(any(BidList.class))).thenReturn(bidA);

        this.mockMvc.perform(post("/bidList/validate")
                .param("account", bidA.getAccount())
                .param("type", bidA.getType())
                .param("bidQuantity", bidA.getBidQuantity().toString())
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void update() throws Exception {
        BidList bidA = new BidList("account_a", "type_a", 41.5);

        when(bidListService.findById(1)).thenReturn(bidA);

        this.mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList",
                        equalTo(bidA)));

        this.mockMvc.perform(get("/bidList/update"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(post("/bidList/update/1")
                .param("account", bidA.getAccount())
                .param("type", bidA.getType())
                .param("bidQuantity", "-41.5")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().hasErrors());

        this.mockMvc.perform(post("/bidList/update/1")
                .param("account", bidA.getAccount())
                .param("type", bidA.getType())
                .param("bidQuantity", bidA.getBidQuantity().toString())
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void delete() throws Exception {

        this.mockMvc.perform(get("/bidList/delete")
                .with(csrf()))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get("/bidList/delete/1")
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());
    }
}
