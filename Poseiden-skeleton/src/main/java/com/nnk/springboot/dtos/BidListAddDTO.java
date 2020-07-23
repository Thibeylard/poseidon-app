package com.nnk.springboot.dtos;

import com.nnk.springboot.annotations.RangeDouble;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * DTO composed of BidList account, type, and bidQuantity properties
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
public @Data
class BidListAddDTO {
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @RangeDouble(min = 0.0)
    private Double bidQuantity;

    public BidListAddDTO() {
    }

    public BidListAddDTO(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public BidListAddDTO(BidListUpdateDTO bidList) {
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();
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
