package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeEAVConverter;
import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeEAVDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributeEAV;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeEAVJPARepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerJPARepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerTextValueJpaRepository;
import com.Nkosopa.NMarket.Services.Customer.iCustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements iCustomerService {

    @Autowired
    private CustomerTextValueJpaRepository customerTextValueRepository;


    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private CustomerValueServiceImpl customerValueService;
    @Autowired
    private CustomerAttributeEAVJPARepository customerAttributeEAVJPARepository;
    @Autowired
    private CustomerAttributeEAVConverter customerAttributeEAVConverter;

    @Override
    public void newCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setUserName(customerDTO.getUserName());
        newCustomer.setPassword(customerDTO.getPassword());
        newCustomer.setEmail(customerDTO.getEmail());
        newCustomer.setAttributeEAVList(new ArrayList<>());

        customerJPARepository.save(newCustomer);
    }//add new customer manually


    @Override
    public Optional<CustomerDTO> findCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerJPARepository.findById(customerId);
        return customerOptional.map(product -> {
            return customerConverter.mapEntityToDTO(product);
        });
    }


    @Override
    public CustomerDTO updateCustomerProfile(CustomerDTO customerDTO) {
        Optional<Customer> customerOptional = customerJPARepository.findById(customerDTO.getId());

        return customerOptional.map(customer -> {
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setUserName(customerDTO.getUserName());

//            CustomerTypeDTO customerTypeDTO = customerDTO.getCustomerType();
//            if (CustomerTypeDTO != null) {
//                CustomerType customerType = customerJpaRepository.findCustomerTypeByType(customerTypeDTO.getType());
//                product.setCustomerType(customerTypeDTO);
//            }

            if (customerDTO.getAttributeEAVDTOList() != null && !customerDTO.getAttributeEAVDTOList().isEmpty()) {
                List<CustomerAttributeEAVDTO> attributeDTOList = customerDTO.getAttributeEAVDTOList();
                for (CustomerAttributeEAVDTO attributeDTO : attributeDTOList) {
                    Optional<CustomerAttributeEAV> existingAttributeOpt = customerAttributeEAVJPARepository.findByAttributeName(attributeDTO.getAttributeName());

                    CustomerAttributeEAV attributeEAV;
                    if (existingAttributeOpt.isPresent()) {
                        attributeEAV = existingAttributeOpt.get();
                    } else {
                        attributeEAV = new CustomerAttributeEAV();
                        attributeEAV.setAttributeName(attributeDTO.getAttributeName());
                        attributeEAV.setSearchable(true);
                        attributeEAV.setDataType(attributeDTO.getDataType());
                    }

                    attributeEAV.setDataType(attributeDTO.getDataType());
                    DataType dataType = attributeDTO.getDataType();
                    Long customerDTOid = customerDTO.getId();
                    Long attributeDTOid = attributeDTO.getId();
                    switch (dataType) {
                        case STRING:
                            if(attributeDTO.getTextValues().isEmpty()){
                                customerValueService.deleteCustomerAttributeValue(customerDTOid, attributeDTOid);
                            }else {
                                customerValueService.updateTextValues(customerDTOid, attributeDTOid, attributeDTO.getTextValues());
                            }
                            break;
                        case LONG:
                            if(attributeDTO.getLongValues().isEmpty()){
                                customerValueService.deleteCustomerAttributeValue(customerDTOid, attributeDTOid);
                            }else {
                                customerValueService.updateLongValues(customerDTOid, attributeDTOid, attributeDTO.getLongValues());
                            }
                            break;
                        case DATE:
                            if(attributeDTO.getDateValues().isEmpty()) {
                                customerValueService.deleteCustomerAttributeValue(customerDTOid, attributeDTOid);
                            }else{
                                customerValueService.updateDateValues(customerDTOid, attributeDTOid, attributeDTO.getDateValues());
                            }
                            break;
                        default:
                            break;
                    }
                    customerAttributeEAVJPARepository.save(attributeEAV);
                }
                customer.setAttributeEAVList(customerAttributeEAVConverter.mapToEntities(attributeDTOList));
            }

            Customer updatedCustomer = customerJPARepository.save(customer);
            return customerConverter.mapEntityToDTO(updatedCustomer);
        }).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    //view
    @Override
    public List<CustomerDTO> getAllCustomer() {
        return customerJPARepository.findAll().stream()
                .map(customerConverter::mapEntityToDTO)
                .collect(Collectors.toList());
    }

//    public void deleteCustomer(Long customerId){
//        Optional<Customer> customerOptional = customerJPARepository.findById(customerId);
//        customerValueService.deleteCustomerValue(customerId);
//        customerJPARepository.deleteCustomerAttribute(customerId);
//        if (customerOptional.isPresent()) {
//            customerJPARepository.deleteById(customerId);
//        }
//    }

    public CustomerDTO disableCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerJPARepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setStatus(false);

            customerJPARepository.save(customer);

            return customerConverter.mapEntityToDTO(customer);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + customerId);
        }
    }

}
