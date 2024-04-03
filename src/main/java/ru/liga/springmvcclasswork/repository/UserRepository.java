package ru.liga.springmvcclasswork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.springmvcclasswork.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
