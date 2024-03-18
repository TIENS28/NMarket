package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductAttributeJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductDateValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductLongValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductTextValueJpaRepository;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerServiceImpl;
import com.Nkosopa.NMarket.Services.Product.iProductValueService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProductValueServiceImpl implements iProductValueService {

    @Autowired
    private ProductAttributeJpaRepository productAttributesJpaRepository;

    @Autowired
    private ProductTextValueJpaRepository productTextValueJpaRepository;

    @Autowired
    private ProductLongValueJpaRepository productLongValueJpaRepository;

    @Autowired
    private ProductDateValueJpaRepository productDateValueJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public void addValueToProductAttribute(Long productAttributeId, ProductValueDTO valueDTO) {
        ProductAttributes productAttribute = productAttributesJpaRepository.findById(productAttributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found"));

        DataType dataType = productAttribute.getDataType();

        switch (dataType) {
            case STRING:
                ProductTextValue textValue = new ProductTextValue();
                textValue.setValue(valueDTO.getValue());
                textValue.setProductAttributes(productAttribute);
                textValue.setProduct(productAttribute.getProduct());
                productTextValueJpaRepository.save(textValue);
                break;

            case LONG:
                ProductLongValue longValue = new ProductLongValue();
                longValue.setValue(Long.parseLong(valueDTO.getValue()));
                longValue.setProductAttributes(productAttribute);
                longValue.setProduct(productAttribute.getProduct());
                productLongValueJpaRepository.save(longValue);
                break;

            case DATE:
                ProductDateValue dateValue = new ProductDateValue();
                dateValue.setValue(parseStringToDate(valueDTO.getValue()));
                dateValue.setProductAttributes(productAttribute);
                dateValue.setProduct(productAttribute.getProduct());
                productDateValueJpaRepository.save(dateValue);
                break;

            default:

                throw new IllegalArgumentException("Unsupported data type");
        }
    }//add value to one attribute

    @Override
    public void addValuesToProductAttributes(List<ProductValueDTO> valueDTOs) {
        for (ProductValueDTO valueDTO : valueDTOs) {
            addValueToProductAttribute(valueDTO.getAttributeId(), valueDTO);
        }
    }//add multiple value to multiple attributes

    private Date parseStringToDate(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            logger.error("invalid value");
            return null;
        }
    }
}
