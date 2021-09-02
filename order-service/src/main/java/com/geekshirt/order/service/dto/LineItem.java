package com.geekshirt.order.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description= "This class represents an item included in an order")
public class LineItem {
    @ApiModelProperty(notes="UPC(Universal Product Code), length- 12 digits", example="13423532433", required = true
            , position = 0)
    private String upc;
    @ApiModelProperty(notes="Quantity", example = "5", required = true, position = 2)
    private int quantity;
    @ApiModelProperty(notes="Price", example = "10.0", required = true, position = 1)
    private double price;
}