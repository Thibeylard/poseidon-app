package com.nnk.springboot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
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
public class RatingRestRepositoryIT {

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

        //Get all Ratings
        mockMvc.perform(get("/restApi/ratings")
                .accept("application/*"))
                .andExpect(status().isOk());


        //Get Rating 1
        MvcResult response = mockMvc.perform(get("/restApi/ratings/1")
                .accept("application/*"))
                .andExpect(status().isOk())
                .andReturn();

        Logger.debug(response.getResponse().getContentAsString());

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);

        // No Rating 85 : Not Found
        response = mockMvc.perform(get("/restApi/ratings/85")
                .accept("application/*"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Add new ratingList

        Rating addedRating = new Rating("moodysRating5", "sandPRating5", "fitchRating5", 10);
        Rating invalidRating = new Rating("moodysRating5", "sandPRating5", "fitchRating5", null);

        response = mockMvc.perform(post("/restApi/ratings")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(addedRating))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("moodysRating").asText())
                .isEqualTo(addedRating.getMoodysRating());
        assertThat(bodyResponse.get("sandPRating").asText())
                .isEqualTo(addedRating.getSandPRating());
        assertThat(bodyResponse.get("fitchRating").asText())
                .isEqualTo(addedRating.getFitchRating());
        assertThat(bodyResponse.get("orderNumber").asInt())
                .isEqualTo(addedRating.getOrderNumber());

        // Invalid DTO

        mockMvc.perform(post("/restApi/ratings")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(invalidRating))
                .with(csrf()))
                .andExpect(status().isBadRequest());


        // Successful partial update on ratinglist 1

        Rating updateRating = new Rating("otherMoodysRating", "sandPRating1", "otherFitchRating", 1);

        response = mockMvc.perform(patch("/restApi/ratings/1")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateRating))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());
        assertThat(bodyResponse.get("id").asInt())
                .isEqualTo(1);
        assertThat(bodyResponse.get("moodysRating").asText())
                .isEqualTo(updateRating.getMoodysRating());
        assertThat(bodyResponse.get("sandPRating").asText())
                .isEqualTo(updateRating.getSandPRating());
        assertThat(bodyResponse.get("fitchRating").asText())
                .isEqualTo(updateRating.getFitchRating());
        assertThat(bodyResponse.get("orderNumber").asInt())
                .isEqualTo(updateRating.getOrderNumber());

        // Put ID 86
        // Creates a resource identical to rating except with next generated ID
        response = mockMvc.perform(put("/restApi/ratings/86")
                .accept("application/*")
                .content(objectMapper.writeValueAsString(updateRating))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        bodyResponse = objectMapper.reader().readTree(response.getResponse().getContentAsString());

        int putRatingId = bodyResponse.get("id").asInt();

        assertThat(bodyResponse.get("moodysRating").asText())
                .isEqualTo(updateRating.getMoodysRating());
        assertThat(bodyResponse.get("sandPRating").asText())
                .isEqualTo(updateRating.getSandPRating());
        assertThat(bodyResponse.get("fitchRating").asText())
                .isEqualTo(updateRating.getFitchRating());
        assertThat(bodyResponse.get("orderNumber").asInt())
                .isEqualTo(updateRating.getOrderNumber());

        // Successful Delete of ratingList with last put ID
        response = mockMvc.perform(delete("/restApi/ratings/" + putRatingId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEmpty();

        // Element already suppressed : error occurred
        mockMvc.perform(delete("/restApi/ratings/" + putRatingId)
                .accept("application/*")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
