package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.news.UserNews;

public class UserNewsResponse extends MResponse {
    public UserNews[] items;
}
