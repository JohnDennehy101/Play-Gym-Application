package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * The Assessments model is used to define the constructor for assessments and includes mutators for each
 * aspect of an assessment.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

@Entity
public class Assessments extends Model {
    public double weight;
    public double chest;
    public double thigh;
    public double upperArm;
    public double waist;
    public double hips;
    public String timestamp;
    public String comment;


    /**
     * Overloaded constructor for objects of type Assessments
     *
     * @param weight    The assessment weight (double)
     * @param chest     The assessment chest measurement (double)
     * @param thigh     The assessment thigh measurement (double)
     * @param upperArm  The assessment upper arm measurement (double)
     * @param waist     The assessment waist measurement (double)
     * @param hips      The assessment hips measurement (double)
     * @param comment   The assessment comment (String)
     * @param timestamp The assessment timestamp - date and time (String)
     */
    public Assessments(double weight, double chest, double thigh, double upperArm, double waist, double hips, String comment, String timestamp) {
        setWeight(weight);
        setChest(chest);
        setThigh(thigh);
        setUpperArm(upperArm);
        setWaist(waist);
        setHips(hips);
        setComment(comment);
        setTimestamp(timestamp);
    }


    /**
     * Getter to obtain the assessment weight
     *
     * @return the assessment weight
     */

    public double getWeight() {
        return this.weight;
    }

    /**
     * Setter to set the assessment weight
     *
     * @param weight the assessment weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Getter to obtain the chest measurement from the assessment
     *
     * @return the assessment chest measurement
     */

    public double getChest() {
        return this.chest;
    }

    /**
     * Setter to set the assessment chest measurement
     *
     * @param chest the assessment chest measurement
     */

    public void setChest(double chest) {
        this.chest = chest;
    }

    /**
     * Setter to set the assessment thigh measurement
     *
     * @param thigh the assessment thigh measurement
     */

    public void setThigh(double thigh) {
        this.thigh = thigh;
    }

    /**
     * Getter to get the assessment thigh measurement
     *
     * @return the assessment thigh measurement
     */

    public double getThigh() {
        return this.thigh;
    }

    /**
     * Getter to get the assessment upper arm measurement
     *
     * @return the assessment upper arm measurement
     */

    public double getUpperArm() {
        return this.upperArm;
    }

    /**
     * Setter to set the assessment upper arm measurement
     *
     * @param upperArm the upper arm measurement from the assessment
     */

    public void setUpperArm(double upperArm) {
        this.upperArm = upperArm;
    }

    /**
     * Getter to obtain the assessment waist measurement
     *
     * @return the assessment waist measurement
     */

    public double getWaist() {
        return this.waist;
    }

    /**
     * Setter to set the assessment waist measurement
     *
     * @param waist the assessment waist measurement
     */

    public void setWaist(double waist) {
        this.waist = waist;
    }

    /**
     * Getter to obtain the hip measurement from an assessment
     *
     * @return the assessment hip measurement
     */

    public double getHips() {
        return this.hips;
    }

    /**
     * Setter to set the hip measurement for an assessment
     *
     * @param hips the assessment hip measurement
     */

    public void setHips(double hips) {
        this.hips = hips;
    }

    /**
     * Getter to obtain the assessment comment
     *
     * @return the assessment comment
     */

    public String getComment() {
        return comment;
    }

    /**
     * Setter to set the assessment comment
     *
     * @param comment the assessment comment
     */

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter to obtain the assessment timestamp (when it was completed - date and time)
     *
     * @return the assessment timestamp
     */

    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     * Setter to set the assessment timestamp (time of submission - date and time)
     *
     * @param timestamp the assessment timestamp
     */

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}




