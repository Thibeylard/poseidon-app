package com.nnk.springboot.integration.daos;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRestRepository;
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
public class RuleRepositoryIT {

	@Autowired
	private RuleNameRestRepository ruleNameRestRepository;

	@Test
	@FlywayTest
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRestRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertEquals("Rule Name", rule.getName());

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRestRepository.save(rule);
		Assert.assertEquals("Rule Name Update", rule.getName());

		// Find
		List<RuleName> listResult = ruleNameRestRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameRestRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRestRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
