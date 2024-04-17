package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Product.*;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.*;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerServiceImpl;
import com.Nkosopa.NMarket.Services.Product.iProductValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ProductConverter productConverter;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public ProductDTO addValueToProductAttribute(ProductValueDTO valueDTO) {
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
                return productConverter.mapEntityToDTO(product);
            }
        }
        return null;
    }//add value to one attribute

    @Override
    public List<ProductDTO> addValuesToProductAttributes(List<ProductValueDTO> valueDTOs) {
        List<ProductDTO> updatedProductDTOs = new ArrayList<>();
        for (ProductValueDTO valueDTO : valueDTOs) {
            ProductDTO updatedProductDTO = addValueToProductAttribute(valueDTO);
            if (updatedProductDTO != null) {
                updatedProductDTOs.add(updatedProductDTO);
            }
        }
        return updatedProductDTOs;
    }

    public void updateTextValues(List<ProductTextValueDTO> textValues) {
        for (ProductTextValueDTO textValueDTO : textValues) {
            String textValueContent = textValueDTO.getValue();
            if (textValueDTO.getAttributeId()!=null) {

                Optional<ProductTextValue> existingValueOptional = productTextValueJpaRepository.findByProductAttributesId(textValueDTO.getAttributeId());

                if (existingValueOptional.isPresent()) {
                    ProductTextValue existingValue = existingValueOptional.get();

                    if (existingValue.getProduct().getId().equals(textValueDTO.getProductId()) &&
                            existingValue.getAttribute().getId().equals(textValueDTO.getAttributeId())) {

                        existingValue.setValue(textValueContent);
                        productTextValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                } else {
                    ProductTextValue newValue = new ProductTextValue();
                    newValue.setProduct(productJpaRepository.getReferenceById(textValueDTO.getProductId()));
                    newValue.setAttribute(attributeJPARepository.getReferenceById(textValueDTO.getAttributeId()));
                    newValue.setValue(textValueContent);
                    productTextValueJpaRepository.save(newValue);
                }
            }
        }
    }


    public void updateLongValues(List<ProductLongValueDTO> longValueDTOS) {
        for (ProductLongValueDTO longValueDTO : longValueDTOS) {
            Long longValueContent = longValueDTO.getValue();
            if (longValueDTO.getAttributeId()!=null) {

                Optional<ProductLongValue> existingValueOptional = productLongValueJpaRepository.findByProductAttributesId(longValueDTO.getAttributeId());

                if (existingValueOptional.isPresent()) {
                    ProductLongValue existingValue = existingValueOptional.get();

                    if (existingValue.getProduct().getId().equals(longValueDTO.getProductId()) &&
                            existingValue.getAttribute().getId().equals(longValueDTO.getAttributeId())) {

                        existingValue.setValue(longValueContent);
                        productLongValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                } else {
                    ProductLongValue newValue = new ProductLongValue();
                    newValue.setProduct(productJpaRepository.getReferenceById(longValueDTO.getProductId()));
                    newValue.setAttribute(attributeJPARepository.getReferenceById(longValueDTO.getAttributeId()));
                    newValue.setValue(longValueContent);
                    productLongValueJpaRepository.save(newValue);
                }
            }
        }
    }



    public void updateDateValues(List<ProductDateValueDTO> dateValueDTOS) {
        for (ProductDateValueDTO dateValueDTO : dateValueDTOS) {
            Date dateValueContent = dateValueDTO.getValue();
            if (dateValueDTO.getAttributeId()!=null) {

                Optional<ProductDateValue> existingValueOptional = productDateValueJpaRepository.findByProductAttributesId(dateValueDTO.getAttributeId());

                if (existingValueOptional.isPresent()) {
                    ProductDateValue existingValue = existingValueOptional.get();

                    if (existingValue.getProduct().getId().equals(dateValueDTO.getProductId()) &&
                            existingValue.getAttribute().getId().equals(dateValueDTO.getAttributeId())) {

                        existingValue.setValue(dateValueContent);
                        productDateValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                } else {
                    ProductDateValue newValue = new ProductDateValue();
                    newValue.setProduct(productJpaRepository.getReferenceById(dateValueDTO.getProductId()));
                    newValue.setAttribute(attributeJPARepository.getReferenceById(dateValueDTO.getAttributeId()));
                    newValue.setValue(dateValueContent);
                    productDateValueJpaRepository.save(newValue);
                }
            }
        }
    }


    public Date parseStringToDate(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            logger.error("invalid value");
            return null;
        }
    }

    public void deleteProductValue(Long productId){
        productTextValueJpaRepository.findByProductId(productId).clear();
        productLongValueJpaRepository.findByProductId(productId).clear();
        productDateValueJpaRepository.findByProductId(productId).clear();
    }

    public void deleteProductAttributeValue(Long productId, Long attributeId){
        productTextValueJpaRepository.deleteByProductIdAndAttributeId(productId, attributeId);
        productLongValueJpaRepository.deleteByProductIdAndAttributeId(productId, attributeId);
        productDateValueJpaRepository.deleteByProductIdAndAttributeId(productId, attributeId);
    }
}
