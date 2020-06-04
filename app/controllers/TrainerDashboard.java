package controllers;

import models.Assessments;
import play.Logger;
import play.mvc.Controller;
import models.Member;
import models.Trainer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * This class is used to include the methods for the trainer views and any trainer actions that take place on
 * a member / member assessments.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class TrainerDashboard extends Controller {

    /**
     * The trainerDashboardMenu() method is used to render the view to the trainer with all members displayed with
     * the number of assessments completed for each member. It is from this view that the trainer can delete
     * individual members.
     */

    public static void trainerDashboardMenu() {
        Logger.info("Rendering Trainer Dashboard");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> members = Member.findAll();
        for (int i = 0; i < members.size(); i++) {
            members.get(i).name = members.get(i).name.toLowerCase();
        }


        for (int i = 0; i < members.size(); i++) {
            int totalNumberOfAssessments = members.get(i).assessments.size();
            members.get(i).setNumberOfAssessments(totalNumberOfAssessments);


        }


        render("trainerDashboard.html", trainer, members);
    }

    /**
     * If the trainer clicks on a member's name, this method is triggered which obtains member assessments and member details and
     * renders these in a view to the Trainer (where the trainer can add comments to any assessment).
     * <p>
     * The member id is used to obtain the details for the correct member.
     * <p>
     * The member's BMI (Body Mass Index), BMI category and whether the member's weight is ideal (according to the
     * Devine formula) is determined via calls to the relevant static methods in the GymUtility class.
     * <p>
     * The member's assessments, user details, BMI, BMI category and whether the member is at the ideal weight (boolean value)
     * is passed to the 'trainerViewMember.html' file which is rendered.
     *
     * @param id The member id passed in the method call
     */

    public static void index(Long id) {
        Member member = Member.findById(id);
        double bmi = 0;
        Date currentTime = new Date();
        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss");
        String timestamp = timestampFormat.format(currentTime);
        String bmiCategory = "";
        boolean isIdealWeight = false;
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> members = trainer.members;
        List<Assessments> assessments = member.assessments;
        if (assessments.size() > 0) {
            Assessments assessment = assessments.get((assessments.size() - 1));
            bmi = GymUtility.calculateBMI(member, assessment);
            bmiCategory = GymUtility.determineBMICategory(bmi);
            isIdealWeight = GymUtility.isIdealBodyWeight(member, assessment);
        } else if (assessments.size() == 0) {
            double height = member.getHeight();
            double heightSquared = height * height;
            double weight = member.getStartingWeight();
            bmi = weight / heightSquared;
            bmi = Math.floor(bmi * 100) / 100;
            bmiCategory = GymUtility.determineBMICategory(bmi);
            Assessments assessment = new Assessments(member.getStartingWeight(), 0.0, 0.0, 0.0, 0.0, 0.0, "", timestamp);
            isIdealWeight = GymUtility.isIdealBodyWeight(member, assessment);
        }


        Logger.info("Member id = " + id);
        render("trainerViewMember.html", member, assessments, members, bmi, bmiCategory, isIdealWeight);
    }

    /**
     * This method allows trainers to add comments to individual member's assessments.
     * The assessment id is used to obtain the correct assessment.
     * The .setComment() method is used on the assessment to add the trainer's comment.
     * assessments._save() is called so that the added comment will be persisted.
     * <p>
     * A redirect of the 'trainerViewMember.html' file (via the trainerDashboard.index method) is undertaken
     * so that the trainer can see that the comment has been added.
     *
     * @param memberid     The member id passed (the member whose assessment is being commented upon)
     * @param assessmentid The assessment id (the assessment that is being commented upon by the trainer)
     * @param comment      The comment that the trainer is adding to the assessment
     */

    public static void addComment(Long memberid, Long assessmentid, String comment) {

        Member member = Member.findById(memberid);
        Assessments assessments = Assessments.findById(assessmentid);

        assessments.setComment(comment);
        Logger.info("Test" + assessments + comment);
        assessments._save();
        Logger.info("Comment added to assessment: " + assessmentid);
        redirect("trainerDashboard.index", memberid);

    }

    /**
     * Allows trainers to delete individual members.
     * Correct member is obtained by doing a search using the member's id.
     * The member is then removed from the members ArrayList in the Trainer class by using .remove().
     * To ensure that the member is also removed from the database, the .delete() call is made on the member.
     * <p>
     * The 'trainerDashboard.html' file is rendered to show the trainer that the member has been deleted.
     *
     * @param id The member id of the member to be deleted
     */

    public static void deleteMember(Long id) {
        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(id);
        List<Member> members = trainer.members;
        trainer.members.remove(member);
        trainer.save();
        member.delete();
        for (int i = 0; i < members.size(); i++) {
            int numberOfAssessments = members.get(i).assessments.size();
            members.get(i).setNumberOfAssessments(numberOfAssessments);

        }
        for (int i = 0; i < members.size(); i++) {
            members.get(i).name = members.get(i).name.toLowerCase();
        }
        render("trainerDashboard.html", members);


    }
}
