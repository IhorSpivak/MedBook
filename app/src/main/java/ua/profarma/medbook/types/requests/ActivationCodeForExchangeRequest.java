package ua.profarma.medbook.types.requests;

public class ActivationCodeForExchangeRequest {
    private int user_id;
    private String verification_type;// = "fishka" "likiwiki"
    private String phone_number;

    public ActivationCodeForExchangeRequest(int user_id, String phone_number, String verification_type) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.verification_type = verification_type;
    }
}
