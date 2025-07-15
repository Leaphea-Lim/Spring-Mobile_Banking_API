package kh.edu.cstad.springa_4_bankingapi.mapper;

import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.domain.SegmentType;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CreateCustomerRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.CustomerResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.SegmentTypeResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.customer.UpdateCustomerRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void toCustomerPartially(@MappingTarget Customer customer, UpdateCustomerRequest updateCustomerRequest);

    // DTO -> Model
    // Model -> DTO
    // return type is converted | target data
    // parameter is source data

    CustomerResponse fromCustomer(Customer customer);
    @Mapping(target = "segmentType", ignore = true)
    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    SegmentTypeResponse toSegmentTypeResponse(SegmentType segmentType);

    List<SegmentTypeResponse> toSegmentTypeResponseList(List<SegmentType> segmentTypes);
}
