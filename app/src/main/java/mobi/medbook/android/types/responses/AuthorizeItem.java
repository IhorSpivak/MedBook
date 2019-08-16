package mobi.medbook.android.types.responses;


import mobi.medbook.android.types.User;

public class AuthorizeItem {
    public User user;
    public String authorization_code;
    public long expires_at;
}
