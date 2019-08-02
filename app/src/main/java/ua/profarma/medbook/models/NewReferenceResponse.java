package ua.profarma.medbook.models;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.news.MedicalCaseItem;

public class NewReferenceResponse extends MResponse {
    public ReferenceCaseItem[] items;
}
