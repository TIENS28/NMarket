package com.Nkosopa.NMarket.Repository.Other.JPA;

import com.Nkosopa.NMarket.Entity.Other.OrderList;
import com.Nkosopa.NMarket.Repository.BaseRepository;
import com.Nkosopa.NMarket.Repository.Other.OrderListRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderList, Long>, OrderListRepository {

}
