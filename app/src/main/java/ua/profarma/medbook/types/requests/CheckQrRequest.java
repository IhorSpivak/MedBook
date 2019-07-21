package ua.profarma.medbook.types.requests;

import ua.profarma.medbook.App;

public class CheckQrRequest {
    private int doctor_id;
    private String code;

    public CheckQrRequest(String code) {
        doctor_id = App.getUser().id;
        this.code = code;
    }
}
