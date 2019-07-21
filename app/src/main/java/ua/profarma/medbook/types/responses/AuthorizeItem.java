package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.User;

public class AuthorizeItem {
    public User user;
    public String authorization_code;
    public long expires_at;
}
