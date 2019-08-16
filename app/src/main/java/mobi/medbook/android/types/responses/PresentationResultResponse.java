package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.materials.ResultsCheckPresentation;

public class PresentationResultResponse {
    public boolean success;
    public Errors errors;
    public ResultsCheckPresentation resultsCheck;
}
