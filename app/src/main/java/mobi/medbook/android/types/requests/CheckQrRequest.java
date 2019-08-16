package mobi.medbook.android.types.requests;


import mobi.medbook.android.App;

public class CheckQrRequest {
    private int doctor_id;
    private String code;

    public CheckQrRequest(String code) {
        doctor_id = App.getUser().id;
        this.code = code;
    }
}
