package com.nnk.springboot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * DTO composed of BidList account, type, and bidQuantity properties
 */
@ApiModel(description = "DTO composed of BidList account, type, and bidQuantity properties")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
public @Data
class BidListAddDTO {
    @JsonProperty("account")
    private String account = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("bidQuantity")
    private Double bidQuantity = null;

    public BidListAddDTO(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    /**
     * Get account
     *
     * @return account
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getAccount() {
        return account;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getType() {
        return type;
    }

    /**
     * Get bidQuantity
     *
     * @return bidQuantity
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public Double getBidQuantity() {
        return bidQuantity;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
