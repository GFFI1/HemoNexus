package com.hemonexus.repository;

import com.hemonexus.model.Request;
import com.hemonexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    long countByUser(User user);
}
