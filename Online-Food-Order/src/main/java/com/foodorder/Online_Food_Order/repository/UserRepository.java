package com.foodorder.Online_Food_Order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodorder.Online_Food_Order.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{
Optional<User> findByEmailIdAndPassword(String emailId,String password);
Optional<User> findByEmailId(String emailId);

}
