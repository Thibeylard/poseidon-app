package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import com.nnk.springboot.services.BidListService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
public class BidListController implements BidListAPI, RestController {

    private final BidListService bidListService;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Autowired
    public BidListController(BidListService bidListService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.bidListService = bidListService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<BidList> addBidList(@ApiParam(value = "DTO used as parameter to add corresponding BidList to database")
                                              @Valid @RequestBody BidListAddDTO body
    ) throws HttpMediaTypeNotAcceptableException {
        BidList added = bidListService.save(body);
        return new ResponseEntity<BidList>(added, HttpStatus.CREATED);
    }

    public ResponseEntity<Boolean> deleteBidList(@NotNull @ApiParam(value = "ID of BidList to delete", required = true)
                                                 @Min(0) @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) throws ResourceIdNotFoundException, HttpMediaTypeNotAcceptableException {
        bidListService.delete(bidListId);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    public ResponseEntity<List<BidList>> getAllBidLists() throws HttpMediaTypeNotAcceptableException {
        List<BidList> allBids = new ArrayList<>();
        allBids.addAll(bidListService.findAll());
        return new ResponseEntity<List<BidList>>(allBids, HttpStatus.OK);
    }

    public ResponseEntity<BidList> getBidList(@NotNull @ApiParam(value = "ID of BidList to find", required = true)
                                              @Min(0) @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) throws ResourceIdNotFoundException, HttpMediaTypeNotAcceptableException {
        BidList bidList = bidListService.findById(bidListId);
        return new ResponseEntity<BidList>(bidList, HttpStatus.OK);
    }

    public ResponseEntity<BidList> updateBidList(@ApiParam(value = "DTO used as parameter to update corresponding BidList to database")
                                                 @Valid @RequestBody BidListUpdateDTO body
    ) throws ResourceIdNotFoundException, HttpMediaTypeNotAcceptableException {
        BidList bidList = bidListService.update(body);
        return new ResponseEntity<BidList>(bidList, HttpStatus.OK);
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }
}
