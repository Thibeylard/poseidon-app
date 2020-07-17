package com.nnk.springboot.integration.services;

import com.nnk.springboot.domain.BidList;
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
import java.util.NoSuchElementException;

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
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        BidList bidUpdate = new BidList("Account Test", "Other Test", 30d);

        bidListService.save(bid);
        bidListService.update(bid.getBidListId(), bidUpdate);

        bid = bidListService.findById(bid.getBidListId());

        List<BidList> bids = (List<BidList>) bidListService.findAll();

        assertThat(bids)
                .hasSize(1);
        assertThat(bids.get(0))
                .isEqualToComparingFieldByField(bid);

        assertThat(bid).isEqualToIgnoringGivenFields(bidUpdate, "BidListId");

        //BidList Delete
        bidListService.delete(bid.getBidListId());

        int bidId = bid.getBidListId();
        assertThrows(NoSuchElementException.class, () -> bidListService.findById(bidId));
    }
}
