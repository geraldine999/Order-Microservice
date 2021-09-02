package com.geekshirt.order.service.client;

import com.geekshirt.order.service.config.OrderServiceConfig;
import com.geekshirt.order.service.dto.AccountDto;
import com.geekshirt.order.service.dto.AddressDto;
import com.geekshirt.order.service.dto.CreditCardDto;
import com.geekshirt.order.service.dto.CustomerDto;
import com.geekshirt.order.service.util.AccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
public class CustomerServiceClient {
    private RestTemplate restTemplate;

    @Autowired
    private OrderServiceConfig orderServiceConfig;

    public CustomerServiceClient(RestTemplateBuilder restTemplateBuilder){
        restTemplate = restTemplateBuilder.build();
    }

    public Optional <AccountDto> findAccountById(String accountId){
        Optional<AccountDto> result = Optional.empty();
        try {
            result = Optional.ofNullable(restTemplate.getForObject(orderServiceConfig.getCustomerServiceURL() + "/{id}", AccountDto.class, accountId));
        }catch(HttpClientErrorException exception){
            if(exception.getStatusCode() != HttpStatus.NOT_FOUND){
                throw exception;
            }

        }
        return result;
    }

    public AccountDto createAccount(AccountDto account){
        return restTemplate.postForObject(orderServiceConfig.getCustomerServiceURL(), account, AccountDto.class);
    }

    public AccountDto createDummyAccount(){
        AddressDto addressDto = AddressDto.builder().state("Av.Siempre Viva")
                .city("Springfield")
                .state("Florida")
                .country("United States")
                .zipCode("12345").build();
        CustomerDto customerDto = CustomerDto.builder()
                .lastName("Simpson")
                .firstName("Homer")
                .email("homersimpson@gmail.com")
                .build();
        CreditCardDto creditCardDto = CreditCardDto.builder()
                .nameOnCard("Homer Simpson")
                .number("432012314552264")
                .expirationMonth("03")
                .expirationYear("2023")
                .type("Visa")
                .ccv("145").build();
        AccountDto accountDto = AccountDto.builder().address(addressDto).customer(customerDto)
                .creditCard(creditCardDto)
                .status(AccountStatus.ACTIVE).build();
        return accountDto;
    }

    public AccountDto createAccountBody(AccountDto accountDto){
        ResponseEntity<AccountDto> responseAccount = restTemplate.postForEntity(orderServiceConfig.getCustomerServiceURL(), accountDto, AccountDto.class);
        log.info("Response"+responseAccount.getHeaders());
        return responseAccount.getBody();
    }

    public void updateAccount(AccountDto accountDto){
        restTemplate.put(orderServiceConfig.getCustomerServiceURL()+"/{id}", accountDto, accountDto.getId());

    }

    public void deleteAccount(AccountDto accountDto){
        restTemplate.delete(orderServiceConfig.getCustomerServiceURL()+"{id}", accountDto.getId());
    }
}
