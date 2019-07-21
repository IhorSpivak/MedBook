package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;
import ua.profarma.medbook.types.materials.ResultsCheckPresentation;

public class PresentationResultResponse {
    public boolean success;
    public Errors errors;
    public ResultsCheckPresentation resultsCheck;
}
