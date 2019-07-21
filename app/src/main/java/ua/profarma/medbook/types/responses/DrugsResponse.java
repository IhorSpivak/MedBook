package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.news.Drug;

public class DrugsResponse extends MResponse {
    public Drug[] items;
}
