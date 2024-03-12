package com.Nkosopa.NMarket.Services.Customer;

import java.util.List;

public interface iCustomerAttributeService {

    void deleteSingleCustomerAttribute(Long customerId, List<String> attributeCodes);

    void deleteAttributesOfAllCustomer(List<String> attributeCodes);
}
