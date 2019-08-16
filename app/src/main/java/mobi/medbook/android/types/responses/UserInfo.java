package mobi.medbook.android.types.responses;


import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.User;


public class UserInfo {
    public boolean success;
    public Errors errors;
    public Versions versions;

    public User[] items;
}
