package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<BidList> findAll() throws DataAccessException {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer bidListId) throws DataAccessException, ResourceIdNotFoundException {
        return bidListRepository.findById(bidListId).orElseThrow(() -> new ResourceIdNotFoundException(bidListId));
    }

    @Override
    public BidList save(BidListAddDTO bidList) throws DataAccessException {
        return bidListRepository.save(new BidList(bidList));
    }

    @Override
    public BidList update(BidListUpdateDTO bidList) throws DataAccessException, ResourceIdNotFoundException {
        int bidListId = bidList.getBidListId();
        BidList originalBidList = bidListRepository.findById(bidListId).orElseThrow(() -> new ResourceIdNotFoundException(bidListId));
        originalBidList.setAccount(bidList.getAccount());
        originalBidList.setType(bidList.getType());
        originalBidList.setBidQuantity(bidList.getBidQuantity());
        return originalBidList;
    }

    @Override
    public void delete(Integer bidListId) throws DataAccessException, ResourceIdNotFoundException {
        try {
            bidListRepository.deleteById(bidListId);
        } catch (IllegalArgumentException e) {
            throw new ResourceIdNotFoundException(bidListId);
        }
    }
}
