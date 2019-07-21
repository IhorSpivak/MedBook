package ua.profarma.medbook.api;

import ua.profarma.medbook.types.Errors;

public abstract class MResponse {
    public boolean success;
    public Errors errors;
}
