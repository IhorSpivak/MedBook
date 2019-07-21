package ua.profarma.medbook.types.requests;

public class ExecuteTransactionRequest {
    public String transaction;
    public int key;
    public String verification_type = "fishka";
    public int user_id;
    public int value;
    public String payment_details;

    public ExecuteTransactionRequest(String transaction, int key, int user_id, int value, String payment_details) {
        this.transaction = transaction;
        this.key = key;
        this.user_id = user_id;
        this.value = value;
        this.payment_details = payment_details;
    }
}
