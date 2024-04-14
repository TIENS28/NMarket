package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Product.*;
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
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void updateTextValues(Long productId, Long attributeId, List<ProductTextValueDTO> textValues) {
        for (ProductTextValueDTO textValueDTO : textValues) {
            String textValueContent = textValueDTO.getValue();
            if (textValueDTO.getId()!=null) {
                Long textValueId = textValueDTO.getId();

                Optional<ProductTextValue> existingValueOptional = productTextValueJpaRepository.findById(textValueId);
                if (existingValueOptional.isPresent()) {
                    ProductTextValue existingValue = existingValueOptional.get();

                    if (existingValue.getProduct().getId().equals(productId) &&
                            existingValue.getAttribute().getId().equals(attributeId)) {

                        existingValue.setValue(textValueContent);
                        productTextValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for productId and attributeId");
                    }
                }
            } else {
                ProductTextValue newValue = new ProductTextValue();
                newValue.setProduct(productJpaRepository.getReferenceById(productId));
                newValue.setAttribute(attributeJPARepository.getReferenceById(attributeId));
                newValue.setValue(textValueContent);
                productTextValueJpaRepository.save(newValue);
            }
        }
    }


    public void updateLongValues(Long productId, Long attributeId, List<ProductLongValueDTO> longValues) {
        for (ProductLongValueDTO longValueDTO : longValues) {
            Long longValueContent = longValueDTO.getValue();

            if(longValueDTO.getId()!=null) {
                Long longValueId = longValueDTO.getId();

                Optional<ProductLongValue> existingValueOptional = productLongValueJpaRepository.findById(longValueId);
                if (existingValueOptional.isPresent()) {
                    ProductLongValue existingValue = existingValueOptional.get();

                    if (existingValue.getProduct().getId().equals(productId) &&
                            existingValue.getAttribute().getId().equals(attributeId)) {

                        existingValue.setValue(longValueContent);
                        productLongValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing long value for productId and attributeId");
                    }
                }
            }else{
                ProductLongValue newValue = new ProductLongValue();
                newValue.setProduct(productJpaRepository.getReferenceById(productId));
                newValue.setAttribute(attributeJPARepository.getReferenceById(attributeId));
                newValue.setValue(longValueContent);
                productLongValueJpaRepository.save(newValue);
            }
        }
    }


    public void updateDateValues(Long productId, Long attributeId, List<ProductDateValueDTO> dateValues) {
        for (ProductDateValueDTO dateValueDTO : dateValues) {
            Date dateValueContent = dateValueDTO.getValue();
            if (dateValueDTO.getId()!=null) {
                Long dateValueId = dateValueDTO.getId();

                Optional<ProductDateValue> existingValueOptional = productDateValueJpaRepository.findById(dateValueId);
                if (existingValueOptional.isPresent()) {
                    ProductDateValue existingValue = existingValueOptional.get();

                    if (existingValue.getProduct().getId().equals(productId) &&
                            existingValue.getAttribute().getId().equals(attributeId)) {
                        existingValue.setValue(dateValueContent);
                        productDateValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing date value for productId and attributeId");
                    }
                }
            }else {
                ProductDateValue newValue = new ProductDateValue();
                newValue.setProduct(productJpaRepository.getReferenceById(productId));
                newValue.setAttribute(attributeJPARepository.getReferenceById(attributeId));
                newValue.setValue(dateValueContent);
                productDateValueJpaRepository.save(newValue);
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

}
