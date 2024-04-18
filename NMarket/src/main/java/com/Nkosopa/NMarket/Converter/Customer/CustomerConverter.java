package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.Converter.Other.CartConverter;
import com.Nkosopa.NMarket.Converter.Other.OrderConverter;
import com.Nkosopa.NMarket.DTO.Customer.*;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerCommonValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private CartConverter cartConverter;

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
//            if (customer.getOrders()!=null) {
//                customerDTO.setOrderDTOList(orderConverter.mapToDTOs(customer.getOrders()));
//            }
            if (customer.getShoppingCart()!=null) {
                customerDTO.setShopingCartDTO(cartConverter.mapEntityToDTO(customer.getShoppingCart()));
            }
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
