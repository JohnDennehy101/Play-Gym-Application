package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

/**
 * This class is used to declare a method that, when called, renders the 'about' page.
 *
 * @author John Dennehy (Student Number: 20091408)
 * @version 1.0
 */

public class About extends Controller {
    public static void index() {
        Logger.info("Rendering about");
        render("about.html");
    }
}
