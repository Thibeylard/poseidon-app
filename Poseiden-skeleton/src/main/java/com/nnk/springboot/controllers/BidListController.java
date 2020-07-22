package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.services.BidListService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@RestController
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
public class BidListController implements BidListAPI {

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
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                BidList added = bidListService.save(body);
                return new ResponseEntity<BidList>(added, HttpStatus.CREATED);
            } catch (DataAccessException e) {
                Logger.error("Database could not be reach.", e);
                return new ResponseEntity<BidList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BidList>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<Boolean> deleteBidList(@NotNull @ApiParam(value = "ID of BidList to delete", required = true)
                                                 @Valid @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                bidListService.delete(bidListId);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                Logger.error("BidList with specified ID not found", e);
                return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                Logger.error("Database could not be reach", e);
                return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Boolean>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<List<BidList>> getAllBidLists() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                List<BidList> allBids = new ArrayList<>();
                allBids.addAll(bidListService.findAll());
                return new ResponseEntity<List<BidList>>(allBids, HttpStatus.OK);
            } catch (DataAccessException e) {
                Logger.error("Database could not be reach", e);
                return new ResponseEntity<List<BidList>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BidList>>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<BidList> getBidList(@NotNull @ApiParam(value = "ID of BidList to find", required = true)
                                              @Valid @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                BidList bidList = bidListService.findById(bidListId);
                return new ResponseEntity<BidList>(bidList, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                Logger.error("BidList with specified ID not found", e);
                return new ResponseEntity<BidList>(HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                Logger.error("Database could not be reach", e);
                return new ResponseEntity<BidList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BidList>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<BidList> updateBidList(@ApiParam(value = "DTO used as parameter to update corresponding BidList to database")
                                                 @Valid @RequestBody BidListUpdateDTO body
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                BidList bidList = bidListService.update(body);
                return new ResponseEntity<BidList>(bidList, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                Logger.error("BidList with specified ID not found", e);
                return new ResponseEntity<BidList>(HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                Logger.error("Database could not be reach", e);
                return new ResponseEntity<BidList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<BidList>(HttpStatus.NOT_ACCEPTABLE);
    }

}
