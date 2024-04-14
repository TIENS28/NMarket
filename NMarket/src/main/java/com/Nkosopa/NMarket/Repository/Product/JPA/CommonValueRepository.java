package com.Nkosopa.NMarket.Repository.Product.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonValueRepository<T, ID> extends JpaRepository<T, Long> {

    List<T> findByProductId(Long productId);

    List<T> findByProductIdAndAttributeId(Long productId, Long attributeId);

}
