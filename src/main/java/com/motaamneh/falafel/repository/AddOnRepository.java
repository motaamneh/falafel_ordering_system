package com.motaamneh.falafel.repository;


import com.motaamneh.falafel.entity.AddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn,Integer> {
}
