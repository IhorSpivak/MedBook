package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.points.FishkaCard;

public class UserFishkaCardsResponse extends MResponse {
    public FishkaCard[] items;
}
