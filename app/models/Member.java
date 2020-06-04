package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * The Member model is used to define the constructor for members and includes mutators for each
 * member detail. It also contains methods to check the member's password and email (for authentication purposes).
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

@Entity
public class Member extends Model {

    public boolean idealWeight;


    public String name;
    public String gender;

    public String email;
    public String password;
    public String address;
    public double height;
    public double startingWeight;
    public int numberOfAssessments;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessments> assessments = new ArrayList<Assessments>();

    /**
     * Overloaded constructor for objects of type Member
     *
     * @param name           the member's name (String)
     * @param gender         the member's gender (String)
     * @param email          the member's email (String)
     * @param password       the member's password (String)
     * @param address        the member's address (String)
     * @param height         the member's height (double)
     * @param startingWeight the member's starting weight (double)
     */

    public Member(String name, String gender, String email, String password, String address, double height, double startingWeight) {
        this.name = capitalisedName(name);
        setGender(gender);
        setEmail(email);
        setPassword(password);
        setAddress(address);
        setHeight(height);
        setStartingWeight(startingWeight);


    }

    /**
     * Getter to obtain the member's name
     *
     * @return the member's name
     */

    public String getName() {
        return this.name;
    }

    /**
     * Setter to set the member's name
     *
     * @param name the member's name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter to obtain the member's gender
     *
     * @return the member's gender
     */

    public String getGender() {
        return this.gender;
    }

    /**
     * Setter to set the member's gender
     *
     * @param gender the member's gender
     */

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter to get the member's email
     *
     * @return the member's email
     */

    public String getEmail() {
        return this.email;
    }

    /**
     * Setter to set the member's email
     *
     * @param email the member's email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter to get the member's password
     *
     * @return the member's password
     */

    public String getPassword() {
        return this.password;
    }

    /**
     * Setter to set the member's password
     *
     * @param password the member's password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter to get the member's address
     *
     * @return the member's address
     */

    public String getAddress() {
        return this.address;
    }

    /**
     * Setter to set the member's address
     *
     * @param address the member's address
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter to obtain the member's height
     *
     * @return the member's height
     */

    public double getHeight() {
        return this.height;
    }

    /**
     * Setter to set the member's height
     *
     * @param height the member's height
     */

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Getter to obtain the member's starting weight
     *
     * @return the member's starting weight
     */

    public double getStartingWeight() {
        return startingWeight;
    }

    /**
     * Setter to set the member's starting weight
     *
     * @param startingWeight the member's starting weight
     */

    public void setStartingWeight(double startingWeight) {
        this.startingWeight = startingWeight;
    }


    /**
     * Method to capitalise the member's name
     *
     * @param name the member's name
     * @return the capitalised member's name
     */

    public String capitalisedName(String name) {
        name = name.toUpperCase();
        return name;
    }

    /**
     * Find a member by checking for the first member email match with the email passed as a parameter.
     *
     * @param email the email entered which will be checked against existing members' emails
     * @return The first email that matches the email entered
     */

    public static Member findByEmail(String email) {
        return find("email", email).first();
    }

    /**
     * Check if the password entered matches the password stored for the member
     *
     * @param password the password entered by the user
     * @return boolean value. True if the password entered matches the member password, false if not.
     */

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Sets the boolean value for idealWeight: true if member weight is at ideal weight, false if not (
     * based on Devine formula calculation: see GymUtility for more information)
     *
     * @param idealWeight boolean value: true if the member is at the ideal weight, false if not
     */

    public void setIdealWeight(boolean idealWeight) {
        this.idealWeight = idealWeight;
    }

    /**
     * Set the number of assessments for the member
     *
     * @param numberOfAssessments the number of assessment that have been completed by the member.
     */

    public void setNumberOfAssessments(int numberOfAssessments) {
        this.numberOfAssessments = numberOfAssessments;
    }


}

