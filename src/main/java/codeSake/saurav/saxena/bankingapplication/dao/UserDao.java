package codeSake.saurav.saxena.bankingapplication.dao;

import codeSake.saurav.saxena.bankingapplication.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;



//JpaRepository<User,Long> HERE User is database and Long Is Primary Key

public interface UserDao extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);

    Boolean existsByAccountNumber(String accountNumber);

    User findByAccountNumber(String accountNumber);


}
