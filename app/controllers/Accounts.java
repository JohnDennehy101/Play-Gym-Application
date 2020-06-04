package controllers;

import models.Assessments;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

/**
 * This class is used to manage all methods on user registration, login, authentication of user details
 * and editing existing user details.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class Accounts extends Controller {
    public static void signup() {
        render("signup.html");
    }

    /**
     * This method is used to render the 'login' page.
     */

    public static void login() {
        render("login.html");
    }


    /**
     * Used to render the 'edit account details' page for the member.
     */

    public static void accountDetails() {
        Member member = getLoggedInMember();
        List<Assessments> assessments = member.assessments;
        int numberOfAssessments = assessments.size();

        render("accountDetails.html", member, numberOfAssessments);
    }

    /**
     * Used to register a new member.
     * Redirected to the 'login' page after successful registration.
     *
     * @param name           The user's name (String)
     * @param gender         The user's gender (String)
     * @param email          The user's email (String)
     * @param password       The user's password (String)
     * @param address        The user's address (String)
     * @param height         The user's height (double)
     * @param startingWeight The user's weight at registration (double)
     */

    public static void register(String name, String gender, String email, String password, String address, double height, double startingWeight) {
        Logger.info("Registering new user " + email);

        Member member = new Member(name, gender, email, password, address, height, startingWeight);

        member.save();
        redirect("/login");
    }

    /**
     * This method checks if the user details correspond to an existing member / trainer.
     * If a member match is found, the member dashboard is rendered to the user.
     * If a trainer match is found, the trainer dashboard is rendered to the user.
     * The member/trainer id is added to the cookie (for tracking purposes).
     * <p>
     * If the user does not provide valid details, the login page is displayed once again so that they can
     * try to login again.
     *
     * @param email    The email entered by the user
     * @param password The password entered by the user
     */

    public static void authenticate(String email, String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        Trainer trainer = Trainer.findByEmail(email);

        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect("/dashboard");
        } else if ((trainer != null) && (trainer.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect("/trainerDashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }

    }

    /**
     * The logout() method is used to end the user session and the user is redirected to the login page.
     */

    public static void logout() {
        session.clear();
        redirect("/login");
    }

    /**
     * This method is used to get the logged in member's details.
     * This is completed by checking if the session contains "logged_inMemberid".
     * If it does, the details are obtained by using the memberId from the session
     * to search for the correct Member object.
     * <p>
     * If it does not, the login page is displayed to the user once again.
     *
     * @return the logged in member Object
     */

    public static Member getLoggedInMember() {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));

        } else {
            login();
        }
        return member;
    }

    /**
     * This method is used to get the logged in trainer's details.
     * This is completed by checking if the session contains "logged_inTrainerid".
     * If it does, the details are obtained by using the trainerId from the session
     * to search for the correct Trainer object.
     * <p>
     * If it does not, the login page is displayed to the user once again.
     *
     * @return the logged in trainer Object
     */

    public static Trainer getLoggedInTrainer() {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));

        } else {
            login();
        }
        return trainer;
    }

    /**
     * Used to update the logged in member's name.
     * After being called, the accountDetails.html page is refreshed for the user.
     *
     * @param name The updated name passed by the member
     */

    public static void editAccountName(String name) {
        Member member = Accounts.getLoggedInMember();

        member.setName(name.toUpperCase());
        member.save();
        redirect("/viewAccountDetails ");
    }

    /**
     * Used to update the logged in member's gender.
     * After being called, the accountDetails.html page is refreshed for the user.
     *
     * @param gender The updated gender passed by the member
     */

    public static void editAccountGender(String gender) {
        Member member = Accounts.getLoggedInMember();

        member.setGender(gender);
        member.save();
        redirect("/viewAccountDetails ");
    }

    /**
     * Used to update the logged in member's email.
     * After being called, the accountDetails.html page is refreshed for the user.
     *
     * @param email The updated email passed by the member.
     */

    public static void editAccountEmail(String email) {
        Member member = Accounts.getLoggedInMember();

        member.setEmail(email);
        member.save();
        redirect("/viewAccountDetails ");
    }

    /**
     * Used to update the logged in member's password.
     * After being called, the accountDetails.html page is refreshed for the user.
     *
     * @param password The updated password passed by the user.
     */

    public static void editAccountPassword(String password) {
        Member member = Accounts.getLoggedInMember();

        member.setPassword(password);
        member.save();
        redirect("/viewAccountDetails ");
    }

    /**
     * Used to update the logged in member's height.
     * After being called, the accountDetails.html page is refreshed for the user.
     *
     * @param height The updated height passed by the user.
     */

    public static void editAccountHeight(double height) {
        Member member = Accounts.getLoggedInMember();

        member.setHeight(height);
        member.save();
        redirect("/viewAccountDetails ");
    }

    /**
     * Used to update the logged in member's weight.
     * After being called, the accountDetails.html page is refreshed for the user.
     *
     * @param weight The updated weight passed by the user.
     */

    public static void editAccountWeight(double weight) {
        Member member = Accounts.getLoggedInMember();

        member.setStartingWeight(weight);
        member.save();
        redirect("/viewAccountDetails ");
    }

}

