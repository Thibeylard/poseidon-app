package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BidListServiceImpl implements BidListService {

    BidListRepository bidListRepository;

    @Autowired
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer bidListId) {
        return bidListRepository.findById(bidListId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(Integer bidListId, BidList updatedBidList) {
        BidList originalBidList = bidListRepository.findById(bidListId).orElseThrow(NoSuchElementException::new);
        originalBidList.setAccount(updatedBidList.getAccount());
        originalBidList.setType(updatedBidList.getType());
        originalBidList.setBidQuantity(updatedBidList.getBidQuantity());
        return originalBidList;
    }

    @Override
    public void delete(Integer bidListId) {
        bidListRepository.deleteById(bidListId);
    }
}
