package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.visits.UserVisit;

public class UserVisitsResponse extends MResponse {

    public UserVisit[] items;
}
