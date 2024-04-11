package com.Nkosopa.NMarket.Repository.Product.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CommonValueRepository<T, ID> extends JpaRepository<T, Long> {
    Optional<T> findByProductAttributesId(Long productAttributeId);
    List<T> findByProductId(Long productId);

    List<T> findByProductIdAndAttributeId(Long productId, Long attributeId);

}
