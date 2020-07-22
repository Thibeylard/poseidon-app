package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface BidListService {

    Collection<BidList> findAll() throws DataAccessException;

    BidList findById(Integer bidListId) throws DataAccessException, ResourceIdNotFoundException;

    BidList save(BidListAddDTO bidList) throws DataAccessException;

    BidList update(BidListUpdateDTO bidList) throws DataAccessException, ResourceIdNotFoundException;

    void delete(Integer bidListId) throws DataAccessException, ResourceIdNotFoundException;
}
