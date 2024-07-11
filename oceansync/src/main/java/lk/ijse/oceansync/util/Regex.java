package lk.ijse.oceansync.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean isTextFieldValid(TextField textField, String val) {
        String field = "";

        switch (textField) {
            case USERID:
                field = "^[a-z0-9_-]{3,16}$";
                break;
            case USERNAME:
                field = "^[a-z0-9_-]{3,16}$";
                break;
            case PASSWORD:
                field = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
                break;
            case QTY:
                field = "^[0-9]{1,3}$";
                break;
            case DISCOUNT:
                field = "^[0-9]{1,3}$";
                break;
            case UNIT_PRICE:
                field = "^[0-9]{1,9}$";
                break;
            case AMOUNT:
                field = "^[0-9]{1,3}$";
                break;
            case MOBILENUMBER:
                field ="^\\+(?:[0-9] ?){6,14}[0-9]$";
                break;
            default:
                break;

        }
        Pattern pattern = Pattern.compile(field);

        if (val != null){
            if (val.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(val);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, JFXTextField field){
        if (Regex.isTextFieldValid(location,field.getText())){
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }
}