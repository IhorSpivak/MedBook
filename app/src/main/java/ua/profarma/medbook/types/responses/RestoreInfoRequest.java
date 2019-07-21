package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;

public class RestoreInfoRequest {
    public boolean success;
    public Errors errors;
    public Item[] items;

    public class Item {
        public String message;
    }
}
