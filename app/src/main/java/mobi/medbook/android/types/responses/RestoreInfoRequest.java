package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;


public class RestoreInfoRequest {
    public boolean success;
    public Errors errors;
    public Item[] items;

    public class Item {
        public String message;
    }
}
