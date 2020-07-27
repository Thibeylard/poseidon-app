package com.nnk.springboot.integration.daos;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRestRepository;
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
public class TradeRestRepositoryIT {

	@Autowired
	private TradeRestRepository tradeRestRepository;

	@Test
	@FlywayTest
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeRestRepository.save(trade);
		Assert.assertNotNull(trade.getTradeId());
		Assert.assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRestRepository.save(trade);
		Assert.assertEquals("Trade Account Update", trade.getAccount());

		// Find
		List<Trade> listResult = tradeRestRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRestRepository.delete(trade);
		Optional<Trade> tradeList = tradeRestRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
