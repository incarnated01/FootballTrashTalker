package com.theironyard.charlotte.services;

import com.theironyard.charlotte.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mfahrner on 10/4/16.
 */
public interface UserRepository extends CrudRepository<User, Integer>{
    User findFirstByUserName(String userName);
}
