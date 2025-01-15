package main.java.com.studygoat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import main.java.com.studygoat.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
   Optional<User> findByEmail(String email); //accesses persons from the database ( repository ) by use of email
}
