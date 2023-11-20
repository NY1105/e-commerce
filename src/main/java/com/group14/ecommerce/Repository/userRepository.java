package com.group14.ecommerce.Repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.group14.ecommerce.Vo.*;

@Repository
public interface userRepository extends JpaRepository<User, Long> {

}