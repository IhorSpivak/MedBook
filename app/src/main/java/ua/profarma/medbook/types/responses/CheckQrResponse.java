package ua.profarma.medbook.types.responses;

public class CheckQrResponse {

    private String message;
    private String user_message;
    private boolean status;

    public String getUserMessage() {
        return user_message == null ? "" : user_message;
    }

    public boolean isStatus() {
        return status;
    }
}
