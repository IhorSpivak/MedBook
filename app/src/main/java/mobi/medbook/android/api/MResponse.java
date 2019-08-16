package mobi.medbook.android.api;

import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.responses.Versions;

public abstract class MResponse {
    public boolean success;
    public Errors errors;
    public Versions versions;
}
