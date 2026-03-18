package com.telusko.repo;

import com.telusko.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUserName(String userName);
}
