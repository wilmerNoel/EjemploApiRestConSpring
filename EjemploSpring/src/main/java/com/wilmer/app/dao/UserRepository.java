package com.wilmer.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wilmer.app.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
