package org.swiggy.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swiggy.orderservice.model.MenuOrder;

@Repository
public interface OrderRepository extends JpaRepository<MenuOrder, Long> {
}
