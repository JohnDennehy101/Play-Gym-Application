package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * The Trainer model is used to define the constructor for trainers.
 * It also contains methods to check the trainer's password and email (for authentication purposes).
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

@Entity
public class Trainer extends Model {
    public String email;
    public String password;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Member> members = new ArrayList<Member>();

    /**
     * Overloaded constructor for objects of type Trainer.
     *
     * @param email    the trainer's email (String)
     * @param password the trainer's password (String)
     */

    public Trainer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Find a trainer by checking for the first trainer email match with the email passed as a parameter.
     *
     * @param email the email entered which will be checked against existing trainers' emails
     * @return The first email that matches the email entered.
     */

    public static Trainer findByEmail(String email) {
        return find("email", email).first();
    }

    /**
     * Check if the password entered matches the password stored for the trainer
     *
     * @param password the password entered by the user
     * @return boolean value. True if the password entered matches the trainer password, false if not.
     */

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Getter to obtain the trainer's email
     *
     * @return the trainer's email
     */

    public String getEmail() {
        return this.email;
    }

    /**
     * Setter to set the trainer's email
     *
     * @param email the trainer's email
     */

    public void setEmail(String email) {
        this.email = email;
    }

}
