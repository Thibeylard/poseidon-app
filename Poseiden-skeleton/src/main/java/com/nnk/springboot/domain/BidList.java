package com.nnk.springboot.domain;

import com.nnk.springboot.annotations.RangeDouble;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public @Data
class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer BidListId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @RangeDouble(min = 0.0)
    private Double bidQuantity;
    @RangeDouble(min = 0.0)
    private Double askQuantity;
    @RangeDouble(min = 0.0)
    private Double bid;
    @RangeDouble(min = 0.0)
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

}
