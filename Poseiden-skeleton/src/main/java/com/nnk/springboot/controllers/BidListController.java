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
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;


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

    public ResponseEntity<BidList> addBidList(@ApiParam(value = "DTO used as parameter to add corresponding BidList to database") @Valid @RequestBody BidListAddDTO body
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

    public ResponseEntity<Boolean> deleteBidList(@NotNull @ApiParam(value = "ID of BidList to delete", required = true) @Valid @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                bidListService.delete(bidListId);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } catch (NoSuchElementException e) {
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
                return new ResponseEntity<List<BidList>>(objectMapper.readValue("[ {\n  \"bidQuantity\" : 0.6027456183070403,\n  \"side\" : \"side\",\n  \"askQuantity\" : 1.4658129805029452,\n  \"bidListDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"revisionDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"book\" : \"book\",\n  \"trader\" : \"trader\",\n  \"BidListId\" : 0,\n  \"type\" : \"type\",\n  \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"dealType\" : \"dealType\",\n  \"sourceListId\" : \"sourceListId\",\n  \"benchmark\" : \"benchmark\",\n  \"creationName\" : \"creationName\",\n  \"dealName\" : \"dealName\",\n  \"security\" : \"security\",\n  \"revisionName\" : \"revisionName\",\n  \"ask\" : 5.637376656633329,\n  \"bid\" : 5.962133916683182,\n  \"account\" : \"account\",\n  \"commentary\" : \"commentary\",\n  \"status\" : \"status\"\n}, {\n  \"bidQuantity\" : 0.6027456183070403,\n  \"side\" : \"side\",\n  \"askQuantity\" : 1.4658129805029452,\n  \"bidListDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"revisionDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"book\" : \"book\",\n  \"trader\" : \"trader\",\n  \"BidListId\" : 0,\n  \"type\" : \"type\",\n  \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"dealType\" : \"dealType\",\n  \"sourceListId\" : \"sourceListId\",\n  \"benchmark\" : \"benchmark\",\n  \"creationName\" : \"creationName\",\n  \"dealName\" : \"dealName\",\n  \"security\" : \"security\",\n  \"revisionName\" : \"revisionName\",\n  \"ask\" : 5.637376656633329,\n  \"bid\" : 5.962133916683182,\n  \"account\" : \"account\",\n  \"commentary\" : \"commentary\",\n  \"status\" : \"status\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                Logger.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BidList>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BidList>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<BidList> getBidList(@NotNull @ApiParam(value = "ID of BidList to find", required = true) @Valid @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BidList>(objectMapper.readValue("{\n  \"bidQuantity\" : 0.6027456183070403,\n  \"side\" : \"side\",\n  \"askQuantity\" : 1.4658129805029452,\n  \"bidListDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"revisionDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"book\" : \"book\",\n  \"trader\" : \"trader\",\n  \"BidListId\" : 0,\n  \"type\" : \"type\",\n  \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"dealType\" : \"dealType\",\n  \"sourceListId\" : \"sourceListId\",\n  \"benchmark\" : \"benchmark\",\n  \"creationName\" : \"creationName\",\n  \"dealName\" : \"dealName\",\n  \"security\" : \"security\",\n  \"revisionName\" : \"revisionName\",\n  \"ask\" : 5.637376656633329,\n  \"bid\" : 5.962133916683182,\n  \"account\" : \"account\",\n  \"commentary\" : \"commentary\",\n  \"status\" : \"status\"\n}", BidList.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                Logger.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BidList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BidList>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<BidList> updateBidList(@ApiParam(value = "DTO used as parameter to update corresponding BidList to database") @Valid @RequestBody BidListUpdateDTO body
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BidList>(objectMapper.readValue("{\n  \"bidQuantity\" : 0.6027456183070403,\n  \"side\" : \"side\",\n  \"askQuantity\" : 1.4658129805029452,\n  \"bidListDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"revisionDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"book\" : \"book\",\n  \"trader\" : \"trader\",\n  \"BidListId\" : 0,\n  \"type\" : \"type\",\n  \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"dealType\" : \"dealType\",\n  \"sourceListId\" : \"sourceListId\",\n  \"benchmark\" : \"benchmark\",\n  \"creationName\" : \"creationName\",\n  \"dealName\" : \"dealName\",\n  \"security\" : \"security\",\n  \"revisionName\" : \"revisionName\",\n  \"ask\" : 5.637376656633329,\n  \"bid\" : 5.962133916683182,\n  \"account\" : \"account\",\n  \"commentary\" : \"commentary\",\n  \"status\" : \"status\"\n}", BidList.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                Logger.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BidList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BidList>(HttpStatus.NOT_IMPLEMENTED);
    }

}
