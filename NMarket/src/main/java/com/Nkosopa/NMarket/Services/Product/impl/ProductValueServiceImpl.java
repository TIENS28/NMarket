package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.*;
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
import java.util.Optional;

@Service
public class ProductValueServiceImpl implements iProductValueService {

    @Autowired
    private AttributeJPARepository attributeJPARepository;

    @Autowired
    private ProductTextValueJpaRepository productTextValueJpaRepository;

    @Autowired
    private ProductLongValueJpaRepository productLongValueJpaRepository;

    @Autowired
    private ProductDateValueJpaRepository productDateValueJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductServiceImpl productService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public void addValueToProductAttribute(ProductValueDTO valueDTO) {
        Optional<Product> productOptional = productJpaRepository.findById(valueDTO.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Optional<AttributeEAV> attributesOptinal = attributeJPARepository.findById(valueDTO.getAttributeId());
            if (attributesOptinal.isPresent()) {
                AttributeEAV attributeEAV = attributesOptinal.get();
                DataType dataType = attributeEAV.getDataType();
                switch (dataType) {
                    case STRING:
                        ProductTextValue textValue = new ProductTextValue();
                        textValue.setValue(valueDTO.getValue());
                        textValue.setAttribute(attributeEAV);
                        textValue.setProduct(product);
                        productTextValueJpaRepository.save(textValue);
                        break;

                    case LONG:
                        ProductLongValue longValue = new ProductLongValue();
                        longValue.setValue(Long.parseLong(valueDTO.getValue()));
                        longValue.setAttribute(attributeEAV);
                        longValue.setProduct(product);
                        productLongValueJpaRepository.save(longValue);
                        break;

                    case DATE:
                        ProductDateValue dateValue = new ProductDateValue();
                        dateValue.setValue(parseStringToDate(valueDTO.getValue()));
                        dateValue.setAttribute(attributeEAV);
                        dateValue.setProduct(product);
                        productDateValueJpaRepository.save(dateValue);
                        break;

                    default:

                        throw new IllegalArgumentException("Unsupported data type");

                }
            }
        }
    }//add value to one attribute

    @Override
    public void addValuesToProductAttributes(List<ProductValueDTO> valueDTOs) {
        for (ProductValueDTO valueDTO : valueDTOs) {
            addValueToProductAttribute(valueDTO);
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

    @Override
    public ProductDTO updateValueOfProductAttribute(Long productAttributeId, ProductValueDTO valueDTO) {
        updateProductValue(valueDTO, productAttributeId);
        return productService.findProductById(valueDTO.getProductId()).orElse(null);
    }

    @Override
    public ProductDTO updateProductAttributeValues(List<ProductValueDTO> valueDTOs) {
        for (ProductValueDTO valueDTO : valueDTOs) {
            Long productAttributeId = valueDTO.getAttributeId();
            updateProductValue(valueDTO, productAttributeId);
        }
        return productService.findProductById(valueDTOs.get(0).getProductId()).orElse(null);
    }

    private void updateProductValue(ProductValueDTO valueDTO, Long productAttributeId) {
        AttributeEAV attributeEAV = attributeJPARepository.findById(productAttributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found"));

        DataType dataType = attributeEAV.getDataType();

        switch (dataType) {
            case STRING:
                ProductTextValue textValue = productTextValueJpaRepository.findByProductAttributesId(productAttributeId)
                        .orElseThrow(() -> new EntityNotFoundException("Text value not found"));
                textValue.setValue(valueDTO.getValue());
                productTextValueJpaRepository.save(textValue);
                break;

            case LONG:
                ProductLongValue longValue = productLongValueJpaRepository.findByProductAttributesId(productAttributeId)
                        .orElseThrow(() -> new EntityNotFoundException("Long value not found"));
                longValue.setValue(Long.parseLong(valueDTO.getValue()));
                productLongValueJpaRepository.save(longValue);
                break;

            case DATE:
                ProductDateValue dateValue = productDateValueJpaRepository.findByProductAttributesId(productAttributeId)
                        .orElseThrow(() -> new EntityNotFoundException("Date value not found"));
                dateValue.setValue(parseStringToDate(valueDTO.getValue()));
                productDateValueJpaRepository.save(dateValue);
                break;

            default:
                throw new IllegalArgumentException("Unsupported data type");
        }
    }


}
