/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.20).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
@Api(value = "bidList", description = "the bidList API")
public interface BidListAPI {

    @ApiOperation(value = "Add a BidList to the database", nickname = "addBidList", notes = "", response = BidList.class, tags = {"users",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "BidList successfully created", response = BidList.class),
            @ApiResponse(code = 400, message = "Invalid input, object invalid"),
            @ApiResponse(code = 405, message = "An error occurred")})
    @RequestMapping(value = "/bidList/add",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<BidList> addBidList(@ApiParam(value = "DTO used as parameter to add corresponding BidList to database")
                                       @Valid @RequestBody BidListAddDTO body
    ) throws HttpMediaTypeNotAcceptableException;


    @ApiOperation(value = "Remove specified BidList from the database", nickname = "deleteBidList", notes = "", response = Boolean.class, tags = {"users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BidList successfully removed", response = Boolean.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "BidList with specified ID not found"),
            @ApiResponse(code = 405, message = "An error occurred")})
    @RequestMapping(value = "/bidList/delete",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Boolean> deleteBidList(@NotNull @ApiParam(value = "ID of BidList to delete", required = true)
                                          @Min(0) @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) throws ResourceIdNotFoundException, HttpMediaTypeNotAcceptableException;


    @ApiOperation(value = "Get all BidLists from database.", nickname = "getAllBidLists", notes = "", response = BidList.class, responseContainer = "List", tags = {"users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All bidLists retrieved", response = BidList.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 405, message = "An error occurred")})
    @RequestMapping(value = "/bidList/list",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<BidList>> getAllBidLists() throws HttpMediaTypeNotAcceptableException;


    @ApiOperation(value = "Get specified BidList from database.", nickname = "getBidList", notes = "", response = BidList.class, tags = {"users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BidList retrieved", response = BidList.class),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 404, message = "BidList not found"),
            @ApiResponse(code = 405, message = "An error occurred")})
    @RequestMapping(value = "/bidList",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<BidList> getBidList(@NotNull @ApiParam(value = "ID of BidList to find", required = true)
                                       @Min(0) @RequestParam(value = "bidListId", required = true) Integer bidListId
    ) throws ResourceIdNotFoundException, HttpMediaTypeNotAcceptableException;


    @ApiOperation(value = "Update a BidList in the database", nickname = "updateBidList", notes = "", response = BidList.class, tags = {"users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BidList successfully updated", response = BidList.class),
            @ApiResponse(code = 400, message = "Invalid input, object invalid"),
            @ApiResponse(code = 404, message = "BidList with specified ID not found"),
            @ApiResponse(code = 405, message = "An error occurred")})
    @RequestMapping(value = "/bidList/update",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<BidList> updateBidList(@ApiParam(value = "DTO used as parameter to update corresponding BidList to database")
                                          @Valid @RequestBody BidListUpdateDTO body
    ) throws ResourceIdNotFoundException, HttpMediaTypeNotAcceptableException;

}
