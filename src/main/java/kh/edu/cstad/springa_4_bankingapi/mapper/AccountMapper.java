package kh.edu.cstad.springa_4_bankingapi.mapper;

import kh.edu.cstad.springa_4_bankingapi.domain.Account;
import kh.edu.cstad.springa_4_bankingapi.domain.AccountType;
import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.dto.account.AccountResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.account.CreateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.account.UpdateAccountRequest;
//import kh.edu.cstad.springa_4_bankingapi.dto.UpdateCustomerRequest;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountPartially(UpdateAccountRequest updateAccountRequest, @MappingTarget Account account);


    @Mapping(target = "accountCurrency", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    Account toAccount(CreateAccountRequest createAccountRequest);

//    @Mapping(target = "fullName", source = "customer.fullName")
    @Mapping(source = "accountType.type", target = "accountType")
    AccountResponse fromAccount(Account account);


}
