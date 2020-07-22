package com.nnk.springboot.integration.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.services.BidListService;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles("testH2")
public class BidListServiceIT {

    @Autowired
    BidListService bidListService;

    @Test
    @FlywayTest
    public void bidListServiceTests() {

        //BidList Save + Find + Update
        int bidsSize = bidListService.findAll().size();

        BidList bidBeforeUpdate = bidListService.save(new BidListAddDTO("Account Test", "Type Test", 10d));

        BidList bidUpdated = bidListService.update(new BidListUpdateDTO(bidBeforeUpdate.getBidListId(), "Account Test", "Other Test", 30d));

        bidBeforeUpdate = bidListService.findById(bidBeforeUpdate.getBidListId());

        List<BidList> bids = (List<BidList>) bidListService.findAll();

        assertThat(bids)
                .hasSize(bidsSize + 1);
        assertThat(bids.get(bidsSize))
                .isEqualToComparingFieldByField(bidBeforeUpdate);

        assertThat(bidBeforeUpdate).isEqualToIgnoringGivenFields(bidUpdated, "BidListId");

        //BidList Delete
        bidListService.delete(bidUpdated.getBidListId());

        int bidId = bidUpdated.getBidListId();
        assertThrows(IllegalArgumentException.class, () -> bidListService.findById(bidId));
    }
}
