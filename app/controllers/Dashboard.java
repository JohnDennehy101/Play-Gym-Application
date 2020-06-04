package controllers;

import models.Member;
import models.Trainer;
import models.Assessments;
import play.Logger;
import play.mvc.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used to render the dashboard to the logged in member.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class Dashboard extends Controller {

    /**
     * The index() method is called to display the logged in member's dashboard.
     * The member's BMI (Body Mass Index), BMI category and whether the member's weight is ideal (according to the
     * Devine formula) is determined via calls to the relevant static methods in the GymUtility class.
     * <p>
     * The member's assessments, user details, BMI, BMI category and whether the member is at the ideal weight (boolean value)
     * is passed to the 'dashboard.html' file which is rendered.
     */

    public static void index() {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        double bmi = 0;
        String bmiCategory = null;
        boolean isIdealWeight = false;
        Date currentTime = new Date();
        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss");
        String timestamp = timestampFormat.format(currentTime);


        List<Assessments> assessments = member.assessments;
        List<Double> assessmentWeights = new ArrayList<Double>();

        for (int i = 0; i < assessments.size(); i++) {
            assessmentWeights.add(assessments.get(i).getWeight());
        }


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


        render("dashboard.html", member, assessments, bmi, bmiCategory, isIdealWeight, assessmentWeights);
    }

    /**
     * The addAssessment() method is used by member's to add assessments on a wide range of body measurements (weight,
     * chest, thigh, upper arm, waist and hips).
     * <p>
     * A timestamp (current date and time) is taken at the assessment submission.
     * <p>
     * The BMI and BMI category are updated based on the latest assessment submitted.
     * <p>
     * A check is also made to see if the latest assessment weight is ideal (according to the Devine formula).
     * <p>
     * Following submission, a redirect takes place on the dashboard so that the latest assessment can be rendered.
     *
     * @param weight   The assessment weight (double)
     * @param chest    The assessment chest measurement (double)
     * @param thigh    The assessment thigh measurement (double)
     * @param upperArm The assessment upperArm measurement (double)
     * @param waist    The assessment waist measurement (double)
     * @param hips     The assessment hips measurement (double)
     * @param comment  The assessment comment (String) - Trainers can add comments
     */


    public static void addAssessment(double weight, double chest, double thigh, double upperArm, double waist, double hips, String comment) {
        Member member = Accounts.getLoggedInMember();

        Date currentTime = new Date();

        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss");
        String timestamp = timestampFormat.format(currentTime);
        Assessments assessment = new Assessments(weight, chest, thigh, upperArm, waist, hips, comment, timestamp);

        member.assessments.add(0, assessment);
        GymUtility.calculateBMI(member, assessment);
        GymUtility.isIdealBodyWeight(member, assessment);
        member.save();
        Logger.info("Adding Assessment for: " + member.name);
        redirect("/dashboard");
    }

    /**
     * Used to allow member's to delete assessments.
     * <p>
     * The relevant assessment is obtained by searching through the assessments for the passed assessment id.
     * <p>
     * The assessment is then removed from the member's assessments ArrayList (by using .remove()).
     * <p>
     * To ensure that the assessment is also removed from the database, the .delete() call is made on the assessment.
     * <p>
     * A redirect takes place on the dashboard so that the deleted assessment no longer appears.
     *
     * @param id The assessment id that is to be deleted
     */

    public static void deleteAssessment(Long id) {

        Member member = Accounts.getLoggedInMember();
        Assessments assessment = Assessments.findById(id);
        member.assessments.remove(assessment);
        member.save();
        assessment.delete();
        Logger.info("Deleting assessment (id): " + id);
        redirect("/dashboard");
    }
}
