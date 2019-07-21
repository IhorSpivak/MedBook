package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.visits.UserRelationItem;

public class UserRelationVisitsResponse extends MResponse {
    public UserRelationItem[] items;
}
