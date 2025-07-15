package kh.edu.cstad.springa_4_bankingapi.service;

import kh.edu.cstad.springa_4_bankingapi.dto.customer.CreateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CustomerResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> findAll();
    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
    CustomerResponse findByPhoneNumber(String phoneNumber);
    CustomerResponse updateCustomerByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);
    void deleteCustomerByPhoneNumber(String phoneNumber);
    void disableByPhoneNumber(String phoneNumber);
    void verifyKyc(Integer customerId);

}
