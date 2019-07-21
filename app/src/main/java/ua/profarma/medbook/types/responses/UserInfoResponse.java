package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.User;

public class UserInfoResponse extends MResponse {
    public User[] items;
}
