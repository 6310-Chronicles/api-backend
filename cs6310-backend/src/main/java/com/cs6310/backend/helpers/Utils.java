package com.cs6310.backend.helpers;

/**
 * Created by nelson on 11/6/15.
 */

public class Utils {


    public static Integer convertIntegerToString(String value) {

        if (value == null) {
            return 0;
        } else return Integer.parseInt(value);
    }

    public static boolean convertStringToBool(String value) {

        if (value == null) {
            return false;
        } else if (value.equalsIgnoreCase("true"))
            return true;
        else if (value.equalsIgnoreCase("false"))
            return false;
        else
            return false;

    }

}
