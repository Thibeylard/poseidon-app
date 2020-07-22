package com.nnk.springboot.dtos;

import com.nnk.springboot.annotations.RangeDouble;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO composed of BidList id, account, type, and bidQuantity properties
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
public @Data
class BidListUpdateDTO {
    @NotNull
    private Integer BidListId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @RangeDouble(min = 0.0)
    private Double bidQuantity;

    public BidListUpdateDTO(Integer bidListId, String account, String type, Double bidQuantity) {
        BidListId = bidListId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
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
