package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeConverter;
import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Repository.Customer.JPA.*;
import com.Nkosopa.NMarket.Services.Customer.iCustomerService;
import com.Nkosopa.NMarket.Services.Other.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements iCustomerService {

    @Autowired
    private CustomerAttributeJpaRepository customerAttributeJpaRepository;

    @Autowired
    private CustomerTextValueJpaRepository customerTextValueRepository;


    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private CustomerAttributeConverter customerAttributeConverter;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomerValueServiceImpl customerValueService;

    @Override
    public void newCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setUserName(customerDTO.getUserName());
        newCustomer.setPassword(customerDTO.getPassword());
        newCustomer.setEmail(customerDTO.getEmail());
        newCustomer.setAttributes(new ArrayList<>());

        customerJPARepository.save(newCustomer);
    }//add new customer manually


    @Override
    public Optional<CustomerDTO> findCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerJPARepository.findById(customerId);

        return customerOptional.map(customer -> {
            List<CustomerAttributes> customerAttributesList = customerAttributeJpaRepository.getCustomerAttributes(customerId);
            List<CustomerAttributeDTO> attributeDTOs = customerAttributeConverter.mapAttributesToDTOs(customerAttributesList);

            return CustomerDTO.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .userName(customer.getUserName())
                    .password(customer.getPassword())
                    .email(customer.getEmail())
                    .attributesDTO(attributeDTOs)
                    .build();
        });
    }

    @Override
    public void deleteUser(Long customerId) {
        customerTextValueRepository.deleteValueByCustomerId(customerId);
        customerJPARepository.deleteById(customerId);
    }

    @Override
    @Transactional
    public void updateCustomerProfile(CustomerDTO customerDTO, List<CustomerValueDTO> valueDTOList) {
        Long customerId = customerDTO.getId();
        Optional<Customer> optionalCustomer = customerJPARepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (customerDTO.getAvatar() != null) {
                String avatarUrl = authenticationService.uploadImageToCloudinary(customerDTO.getAvatar());
                customer.setAvatarUrl(avatarUrl);
            }

            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPassword(customerDTO.getPassword());
            customer.setDOB(customer.getDOB());
            customerJPARepository.save(customer);

            customerValueService.addValuesToCustomerAttributes(valueDTOList);

        } else {
            throw new EntityNotFoundException("User not found with ID: " + customerId);
        }
    }

    //view
    @Override
    public List<CustomerDTO> getAllCustomer() {
        return customerJPARepository.findAll().stream()
                .map(customerConverter::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getOneCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerJPARepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return customerConverter.mapEntityToDTO(customer);
        } else {
            return null;
        }
    }
}
