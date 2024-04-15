package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.*;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerCommonValueRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.CommonValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {

    @Autowired
    private CustomerCommonValueRepository<CustomerLongValue, Long> longValueRepository;

    @Autowired
    private CustomerCommonValueRepository<CustomerTextValue, Long> textValueRepository;

    @Autowired
    private CustomerCommonValueRepository<CustomerDateValue, Long> dateValueRepository;
    @Autowired
    private CustomerAttributeEAVConverter customerAttributeEAVConverter;

    public CustomerDTO mapEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        if (customer != null && customer.getId() != null){
            customerDTO.setId(customer.getId());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setUserName(customer.getUsername());
            customerDTO.setGender(customer.getGender()) ;
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setRole(customer.getRole().name());
            customerDTO.setAddress(customer.getAddress());
            if(customer.getAttributeEAVList()!=null) {
                List<CustomerAttributeEAV> attributeEAVList = customer.getAttributeEAVList();
                for (CustomerAttributeEAV attributeEAV : attributeEAVList) {
                    attributeEAV.setIntValues(longValueRepository.findByCustomerIdAndAttributeId(customer.getId(), attributeEAV.getId()));
                    attributeEAV.setDateValues(dateValueRepository.findByCustomerIdAndAttributeId(customer.getId(), attributeEAV.getId()));
                    attributeEAV.setTextValues(textValueRepository.findByCustomerIdAndAttributeId(customer.getId(), attributeEAV.getId()));
                }
                customerDTO.setAttributeEAVDTOList(customerAttributeEAVConverter.mapToDTOs(attributeEAVList));
            }
        }
        return customerDTO;
    }
}
