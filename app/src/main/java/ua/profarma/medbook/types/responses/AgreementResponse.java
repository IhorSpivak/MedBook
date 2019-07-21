package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.user.Agreement;

public class AgreementResponse extends MResponse {
    public Agreement[] items;
}
