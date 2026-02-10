package com.motaamneh.falafel.repository;


import com.motaamneh.falafel.entity.OrderAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderAddOnRepository extends JpaRepository<OrderAddOn,Integer> {

    // Find all add-ons for a specific order (to display order details)
    List<OrderAddOn> findByOrderId(Long orderId);

}
