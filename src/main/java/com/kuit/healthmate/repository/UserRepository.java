package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
