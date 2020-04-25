package ru.gsmirnov.sweater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gsmirnov.sweater.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
