package de.interhyp.acceptancetest.example.repository;

import de.interhyp.acceptancetest.example.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
