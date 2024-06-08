package com.group.security.repository;

import com.group.security.entity.OrderSumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSumRepository extends JpaRepository<OrderSumEntity,Long> {
}
