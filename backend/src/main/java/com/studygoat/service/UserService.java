package main.java.com.studygoat.service;
import main.java.com.studygoat.repository.UserRepository;
import main.java.com.studygoat.model.User;
import java.util.Optional;
import java.util.*;

/* Essentially the database that keeps track of all known users in the studygoat system. utilise a UserRepository that gives us data that can be
* manipulated by methods defined in userService class*/

/*Primary Objective of this class is to complete all the neccessary functions of an app, being the ability
* to register a user, to purchase goatCoins, to addStudyTime, to deactivate a user*/

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*RegisterUser method that returns a newUser and uses the UserRepository interface to check for valid email and update database*/

    public User RegisterUser(String email, String username, Long id, String password){
        if(userRepository.findByEmail(email).isPresent()){ //if email is present in database then you can't use email
            throw new IllegalArgumentException("Email Already in Use");
        }

        User newRegisteredUser = new User(email,id,username,password);
        userRepository.save(newRegisteredUser);
        return newRegisteredUser;
    }

    //fetch known user in the database by email
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    //fetch all the users in the database

    public List<User> getAllUsers(){
        return userRepository.findAll(); //find all method inherited from JPArepository returns us a list of all the users in our database
    }

    public void purchaseGoatCoins(String email, int coins){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        user.purchaseGoatCoins(coins);
        userRepository.save(user); //important to note that after each change to the user we have to update database and save once again ( Just like with anything in tech )
    }

    public void addStudyTime(String email, int minutes,boolean stayedInApp){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("User Not Found"));
        user.addStudyTime(minutes,stayedInApp);
        userRepository.save(user);
    }

    public void deActivateUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("User Not Found"));
        user.active = false;
        userRepository.save(user);
    }

    //purchaseGoatCoins
    //addStudyTime
    //deactivateUser




}
