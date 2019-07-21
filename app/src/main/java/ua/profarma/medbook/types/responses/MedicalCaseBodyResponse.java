package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.news.MedicalCaseItem;

public class MedicalCaseBodyResponse extends MResponse {
    public MedicalCaseItem[] items;
}
