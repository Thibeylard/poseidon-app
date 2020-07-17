package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.Collection;

public interface BidListService {

    Collection<BidList> findAll();

    BidList findById(Integer bidListId);

    BidList save(BidList bidList);

    BidList update(Integer bidListId, BidList updatedBidList);

    void delete(Integer bidListId);
}
