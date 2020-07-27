package com.nnk.springboot.integration.daos;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRestRepository;
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
public class CurvePointRepositoryIT {

	@Autowired
	private CurvePointRestRepository curvePointRestRepository;

	@Test
	@FlywayTest
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePoint = curvePointRestRepository.save(curvePoint);
		Assert.assertNotNull(curvePoint.getId());
		Assert.assertEquals(10, (int) curvePoint.getCurveId());

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRestRepository.save(curvePoint);
		Assert.assertEquals(20, (int) curvePoint.getCurveId());

		// Find
		List<CurvePoint> listResult = curvePointRestRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRestRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRestRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	}

}
