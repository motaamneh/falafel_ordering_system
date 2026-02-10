package com.motaamneh.falafel.repository;


import com.motaamneh.falafel.entity.OrderAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderAddOnRepository extends JpaRepository<OrderAddOn,Integer> {
}
