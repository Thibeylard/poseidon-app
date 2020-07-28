package com.nnk.springboot.domain;

import com.nnk.springboot.annotations.RangeDouble;
import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public @Data
class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BidListId;
    @NotNull(message = "Account is mandatory")
    private String account;
    @NotNull(message = "Type is mandatory")
    private String type;
    @RangeDouble(min = 0.0, message = "BidQuantity must be positive")
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    protected BidList() {
    }

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public BidList(BidListAddDTO bidListAddDTO) {
        this.account = bidListAddDTO.getAccount();
        this.type = bidListAddDTO.getType();
        this.bidQuantity = bidListAddDTO.getBidQuantity();
    }

    public BidList(BidListUpdateDTO bidListUpdateDTO) {
        this.BidListId = bidListUpdateDTO.getBidListId();
        this.account = bidListUpdateDTO.getAccount();
        this.type = bidListUpdateDTO.getType();
        this.bidQuantity = bidListUpdateDTO.getBidQuantity();
    }
}
