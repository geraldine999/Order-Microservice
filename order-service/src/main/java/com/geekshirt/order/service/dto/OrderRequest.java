package com.geekshirt.order.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

@Data
@ApiModel(description="This class represents an order to be processed")
public class OrderRequest {

    @ApiModelProperty(notes="Account Id", example="123456", required = true)
    private String accountId;
    @ApiModelProperty(notes="Items included in the order", required = true)
    private List<LineItem> items;
}
