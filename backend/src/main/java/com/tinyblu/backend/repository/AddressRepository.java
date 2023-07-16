package com.tinyblu.backend.repository;

import com.tinyblu.backend.entity.Address;
import com.tinyblu.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findByUser(User user);

}
