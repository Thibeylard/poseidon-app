package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface BidListService {

    Collection<BidList> findAll() throws DataAccessException;

    BidList findById(Integer bidListId) throws DataAccessException, IllegalArgumentException;

    BidList save(BidListAddDTO bidList) throws DataAccessException;

    BidList update(BidListUpdateDTO bidList) throws DataAccessException, IllegalArgumentException;

    void delete(Integer bidListId) throws DataAccessException, IllegalArgumentException;
}
