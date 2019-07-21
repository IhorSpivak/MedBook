package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;

public class AuthorizeInfo {
    public boolean success;
    public Errors errors;
    public AuthorizeItem[] items;

}
