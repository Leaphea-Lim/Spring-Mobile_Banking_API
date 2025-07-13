package kh.edu.cstad.springa_4_bankingapi.controller;

import jakarta.validation.Valid;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CreateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CustomerResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.UpdateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> findAll(){
        return customerService.findAll();
    }

    @GetMapping("/{phone}")
    public CustomerResponse fineByPhoneNumber(@PathVariable String phone){
        return customerService.findByPhoneNumber(phone);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createNew(@Valid @RequestBody CreateCustomerRequest createCustomerRequest){
        return customerService.createNew(createCustomerRequest);
    }

    //update
    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateCustomerByPhoneNumber(@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest,
                                                        @PathVariable String phoneNumber
    ){
        return customerService.updateCustomerByPhoneNumber(phoneNumber, updateCustomerRequest);
    }

    //delete
    @DeleteMapping("/{phoneNumber}")

    public void  deleteCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        customerService.deleteCustomerByPhoneNumber(phoneNumber);

    }

}
