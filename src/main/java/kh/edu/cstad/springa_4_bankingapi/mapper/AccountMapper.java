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
    void toAccountPartially(UpdateAccountRequest updateAccountRequest, @MappingTarget Account account);

    @Mapping(target = "fullName", source = "customer.fullName")
    AccountResponse fromAccount(Account account);
    Account toAccount(CreateAccountRequest createAccountRequest);

    default AccountType map(Integer id) {
        if (id == null) {
            return null;
        }
        AccountType at = new AccountType();
        at.setId(id);
        return at;
    }


}
