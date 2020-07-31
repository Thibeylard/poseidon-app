package com.nnk.springboot.integration.daos;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRestRepository;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles("testH2")
public class RatingRepositoryIT {

	@Autowired
	private RatingRestRepository ratingRestRepository;

	@Test
	@FlywayTest
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRestRepository.save(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertEquals(10, (int) rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		rating = ratingRestRepository.save(rating);
		Assert.assertEquals(20, (int) rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRestRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRestRepository.delete(rating);
		Optional<Rating> ratingList = ratingRestRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}
}
