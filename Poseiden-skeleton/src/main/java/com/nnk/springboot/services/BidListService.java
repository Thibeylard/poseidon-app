package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;

import java.util.Collection;

public interface BidListService {

    Collection<BidList> findAll();

    BidList findById(Integer bidListId);

    BidList save(BidListAddDTO bidList);

    BidList update(Integer bidListId, BidList updatedBidList);

    void delete(Integer bidListId);
}
