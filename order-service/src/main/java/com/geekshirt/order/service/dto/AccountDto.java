package com.geekshirt.order.service.dto;

import com.geekshirt.order.service.util.AccountStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {
    private Long id;
    private AddressDto address;
    private CustomerDto customer;
    private CreditCardDto creditCard;
    private AccountStatus status;
}
