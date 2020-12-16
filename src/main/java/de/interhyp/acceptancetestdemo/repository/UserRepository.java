package de.interhyp.acceptancetestdemo.repository;

import de.interhyp.acceptancetestdemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
