package kh.edu.cstad.springa_4_bankingapi.service.Impl;

import jakarta.transaction.Transactional;
import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.domain.CustomerSegment;
import kh.edu.cstad.springa_4_bankingapi.domain.KYC;
//import kh.edu.cstad.springa_4_bankingapi.domain.SegmentType;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CreateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CustomerResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.UpdateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.mapper.CustomerMapper;
import kh.edu.cstad.springa_4_bankingapi.repository.CustomerRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.CustomerSegmentRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.KYCRepository;
//import kh.edu.cstad.springa_4_bankingapi.repository.SegmentTypeRepository;
import kh.edu.cstad.springa_4_bankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j //this annotation for log factoring
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KYCRepository kycRepository;
//    private final SegmentTypeRepository segmentTypeRepository;
    private final CustomerSegmentRepository customerSegmentRepository;


    //delete
    @Override
    public void deleteCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customer);
    }

//disable
    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if(!customerRepository.isExistsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number not found");
        }
        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public void verifyKyc(Integer customerId) {
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
//
//        KYC kyc = kycRepository.findByCustomer(customer)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "KYC not found"));
//
//        kyc.setIsVerified(true);
//        kycRepository.save(kyc);
    }

    //update
    @Override
    public CustomerResponse updateCustomerByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        customerRepository.save(customer);

        return customerMapper.fromCustomer(customer);
    }

//find by phone number
    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//create
    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        //validate email
        if (customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }

        //validate phone number
        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone Number already exists!");
        }


        // Validate customer segment
        CustomerSegment customerSegment = customerSegmentRepository
                .findBySegment(createCustomerRequest.customerSegment())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,
                        "Customer segment not found!"));

        Customer customer = customerMapper.toCustomer(createCustomerRequest);

//        String segmentName = createCustomerRequest.segmentType();

//        SegmentType segmentType = segmentTypeRepository.findBySegmentTypeIgnoreCase(segmentName)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SegmentType not found"));

        KYC kyc = new KYC();
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setCustomer(customer);
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setCustomer(customer);
//        kycRepository.save(kyc);

//        customer.setSegmentType(segmentType);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());
        customer.setCustomerSegment(customerSegment);
        customer.setKyc(kyc);
//        customer.setSegment(createCustomerRequest.segment());

        log.info("Customer before save: {}", customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer after save: {}", customer.getId());

        return customerMapper.fromCustomer(customer);
    }

//find all
    @Override
    public List<CustomerResponse> findAll() {

        List<Customer> customers = customerRepository.findAllByIsDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();
    }
}
