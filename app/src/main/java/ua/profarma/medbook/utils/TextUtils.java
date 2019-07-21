package ua.profarma.medbook.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import ua.profarma.medbook.App;

public class TextUtils {
    public static boolean isValidEmail(CharSequence email) {
        if (email == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getString(String text){
        if(text == null || text.isEmpty())
            return "";
        else return text;
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) App.getAppContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
