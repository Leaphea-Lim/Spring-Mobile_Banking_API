package kh.edu.cstad.springa_4_bankingapi.mapper;

import kh.edu.cstad.springa_4_bankingapi.domain.Account;
import kh.edu.cstad.springa_4_bankingapi.domain.AccountType;
import kh.edu.cstad.springa_4_bankingapi.dto.account.AccountResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.account.CreateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.account.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    void toAccountPartially(UpdateAccountRequest dto, @MappingTarget Account entity);

    @Mapping(target = "fullName", source = "customer.fullName")
    @Mapping(target = "accountTypeId", source = "accountType.accountTypeId")
    @Mapping(target = "accountType", source = "accountType.accountType")
    AccountResponse fromAccount(Account account);

    Account toAccount(CreateAccountRequest dto);


    default AccountType map(Integer id) {
        if (id == null) {
            return null;
        }
        AccountType at = new AccountType();
        at.setAccountTypeId(id);
        return at;
    }


}
