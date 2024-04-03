package ru.java.springmvcclasswork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.springmvcclasswork.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
