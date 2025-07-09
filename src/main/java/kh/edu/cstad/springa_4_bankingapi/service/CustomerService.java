package kh.edu.cstad.springa_4_bankingapi.service;

import kh.edu.cstad.springa_4_bankingapi.dto.CreateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);

    List<CustomerResponse> findAll();
}
