package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.CommonValueRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    @Autowired
    private ProductAttributeConverter productAttributeConverter;

    @Autowired
    private ProductTypeConverter productTypeConverter;

    @Autowired
    private AttributeConverter attributeConverter;

    @Autowired
    private CommonValueRepository<ProductLongValue, Long> longValueRepository;

    @Autowired
    private CommonValueRepository<ProductTextValue, Long> textValueRepository;

    @Autowired
    private CommonValueRepository<ProductDateValue, Long> dateValueRepository;

    public ProductDTO mapEntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        if (product.getId() != null) {
            productDTO.setId(product.getId());
        }

        productDTO.setName(product.getName());
        productDTO.setSku(product.getSku());
        productDTO.setStock(product.getStock());
        productDTO.setProductTypeDTO(productTypeConverter.mapEntityToDTO(product.getProductType()));
        if(product.getAttributeEAVS()!=null) {
            List<AttributeEAV> attributeEAVList = product.getAttributeEAVS();
            for (AttributeEAV attributeEAV : attributeEAVList) {
                attributeEAV.setIntValues(longValueRepository.findByProductId(product.getId()));
                attributeEAV.setDateValues(dateValueRepository.findByProductId(product.getId()));
                attributeEAV.setTextValues(textValueRepository.findByProductId(product.getId()));
            }
            productDTO.setAttributeDTOList(attributeConverter.mapToDTOs(attributeEAVList));
        }
        productDTO.setPrice(product.getPrice());
        productDTO.setCurrency(product.getCurrency());
        return productDTO;
    }

    public List<ProductDTO> mapEntitiesToDTOs(List<Product> products) {
        return products.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
