package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

/**
 * Created by Kyle Tommet on 9/7/2017.
 */

public class HelperFunctions {
    public static String toBioString(String input){
        String[] stringComponents = input.trim().split(" ");
        String output = "";

        for(int i = 0; i < stringComponents.length; i++){
            if(i != stringComponents.length - 1){
                output += stringComponents[i].toLowerCase();
                output += "-";
            }
            else output += stringComponents[i].toLowerCase();
        }
        return output;
    }
    public static String toPicString(String input){
        String output = "";
        String[] stringComponents = input.trim().split(" ");

        for(int i = 0; i < stringComponents.length; i++){
            if(i != stringComponents.length - 1){
                output += stringComponents[i].toLowerCase();
                output += "_";
            }
            else output += stringComponents[i].toLowerCase();
        }

        return output;
    }
}
