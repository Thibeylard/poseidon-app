package com.nnk.springboot.domain;

import com.nnk.springboot.annotations.RangeDouble;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
public @Data
class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @RangeDouble(min = 0.0)
    private Double buyQuantity;
    @RangeDouble(min = 0.0)
    private Double sellQuantity;
    @RangeDouble(min = 0.0)
    private Double buyPrice;
    @RangeDouble(min = 0.0)
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
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

    protected Trade() {
    }

    public Trade(String account,
                 String type) {
        this.account = account;
        this.type = type;
    }
}
