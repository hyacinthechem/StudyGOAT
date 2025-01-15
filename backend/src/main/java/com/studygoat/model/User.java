package main.java.com.studygoat.model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.regex.*;


public class User{

    private String username;
    private Long id;
    private String email;
    private String password;
    private int goatCoins;
    private int xp;
    private Mountain mountain;
    private LocalDateTime registeredDate;
    private int totalStudyTime;

    public boolean active;

    public User(String username, Long id, String email, String password){
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.username = username;
        this.id = id;
        this.email = email;
        this.password = hashPassword(password); //ensures security of password
        this.registeredDate = LocalDateTime.now();
        this.xp = 0; //default starting point for user
        this.goatCoins = 0; //default starting point for user
        this.active = true;
        this.mountain = Mountain.MOUNTRUAPEHU; //default starting point ( level 0 )
        this.totalStudyTime = 0;
    }

    private boolean isValidEmail(String email){
        if(email == null){
            return false;
        }
        String emailRegex =  "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; //valid sequence that the email has, if it breaks then it isn't an email
        Pattern p = Pattern.compile(emailRegex); //compiles the emailregex into a pattern that the compiler understand
        Matcher m = p.matcher(email); //Create matcer object for the pattern and email
        return m.matches(); // if the email is consistent with the pattern then return true, if not then it isn't valid
    }

    private String hashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //take plaintext and create passwordEncoder object
        return encoder.encode(password); //encode the password into a ciphertext
    }

    public void purchaseGoatCoins(int goatCoins){
        this.goatCoins+=goatCoins;
    }

    public void purchaseXp(int xp){
        this.xp+=xp;
    }

    public void addStudyTime(int minutes, boolean stayedInApp){
        totalStudyTime+=minutes;
        goatCoins +=minutes * 1.2;
        xp +=minutes*5;
        if(stayedInApp){
            goatCoins+=minutes * 1.6;
            xp+=minutes*6;
        }
        updateMountainLevel(totalStudyTime,xp);
    }

    public void updateMountainLevel(int minutes, int xp){
        if(minutes < 1800){
            mountain = Mountain.MOUNTRUAPEHU;
        }else if(minutes>=1800 && minutes<=6000){
            mountain = Mountain.GLACIERPEAK;
        }else if(minutes>=6000 && minutes<=36000){
            mountain = Mountain.MOUNTBLANC;
        }else{
            mountain = Mountain.MOUNTEVEREST;
        }
    }


    public Mountain getLevel(){
        return mountain;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }

    public int getGoatCoins(){
        return goatCoins;
    }

    public int getXp(){
        return xp;
    }

    public int getTotalStudyTime(){
        return totalStudyTime;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public Long getId(){
        return id;
    }

    public LocalDateTime getRegisteredDate(){
        return registeredDate;
    }

    public boolean isActive(){
        return active;
    }


    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }

        if(this==o){
            return true;
        }

        if(getClass()!=o.getClass()){
            return false; //checks for strict equality incase we wanted to extend object to a subclass.
        }

        User user = (User) o;
        return this.id!=null && id == user.id; //if passed all arguments false
    }

    @Override
    public int hashCode(){
        int hash = 17; //start with a prime number to avoid hash collisions.
         hash = 31 * hash + ( id==null? 0 : id.hashCode()); //utilise another prime number to avoid hash collision and set to zero if id==null to avoid raising exception
         hash = 31 * hash + (username == null? 0 : username.hashCode());
         hash = 31 * hash + (email==null? 0 : email.hashCode());
         return hash;
    }

    @Override
    public String toString(){
        return email + " " + username + " " + id;
    }

}
