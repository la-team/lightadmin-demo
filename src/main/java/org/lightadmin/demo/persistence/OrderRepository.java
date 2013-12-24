package org.lightadmin.demo.persistence;

import org.lightadmin.demo.domain.Customer;
import org.lightadmin.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);
}