package com.nnk.springboot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO composed of BidList id, account, type, and bidQuantity properties
 */
@ApiModel(description = "DTO composed of BidList id, account, type, and bidQuantity properties")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-07-21T09:29:06.402Z[GMT]")
public class BidListUpdateDTO {
    @JsonProperty("BidListId")
    private Integer BidListId = null;

    @JsonProperty("account")
    private String account = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("bidQuantity")
    private Double bidQuantity = null;

    public BidListUpdateDTO bidListId(Integer BidListId) {
        this.BidListId = BidListId;
        return this;
    }

    /**
     * Get bidListId
     *
     * @return bidListId
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public Integer getBidListId() {
        return BidListId;
    }

    public void setBidListId(Integer bidListId) {
        this.BidListId = bidListId;
    }

    public BidListUpdateDTO account(String account) {
        this.account = account;
        return this;
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

    public void setAccount(String account) {
        this.account = account;
    }

    public BidListUpdateDTO type(String type) {
        this.type = type;
        return this;
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

    public void setType(String type) {
        this.type = type;
    }

    public BidListUpdateDTO bidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
        return this;
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

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BidListUpdateDTO bidListUpdateDTO = (BidListUpdateDTO) o;
        return Objects.equals(this.BidListId, bidListUpdateDTO.BidListId) &&
                Objects.equals(this.account, bidListUpdateDTO.account) &&
                Objects.equals(this.type, bidListUpdateDTO.type) &&
                Objects.equals(this.bidQuantity, bidListUpdateDTO.bidQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BidListId, account, type, bidQuantity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BidListUpdateDTO {\n");

        sb.append("    bidListId: ").append(toIndentedString(BidListId)).append("\n");
        sb.append("    account: ").append(toIndentedString(account)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    bidQuantity: ").append(toIndentedString(bidQuantity)).append("\n");
        sb.append("}");
        return sb.toString();
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
