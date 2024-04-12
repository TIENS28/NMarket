package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.CommonValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                attributeEAV.setIntValues(longValueRepository.findByProductIdAndAttributeId(product.getId(), attributeEAV.getId()));
                attributeEAV.setDateValues(dateValueRepository.findByProductIdAndAttributeId(product.getId(), attributeEAV.getId()));
                attributeEAV.setTextValues(textValueRepository.findByProductIdAndAttributeId(product.getId(), attributeEAV.getId()));
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

    public Product mapDTOToEntity(ProductDTO productDTO) {
        Product product = new Product();

        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }

        product.setName(productDTO.getName());
        product.setSku(productDTO.getSku());
        product.setStock(productDTO.getStock());
        product.setProductType(productTypeConverter.mapDTOsToEntity(productDTO.getProductTypeDTO()));

        if(productDTO.getAttributeDTOList() != null) {
            List<AttributeEAV> attributeEAVList = attributeConverter.mapToEntities(productDTO.getAttributeDTOList());
            product.setAttributeEAVS(attributeEAVList);
        }

        product.setPrice(productDTO.getPrice());
        product.setCurrency(productDTO.getCurrency());

        return product;
    }

}
