package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;

public class AuthorizeInfo {
    public boolean success;
    public Errors errors;
    public AuthorizeItem[] items;

}
