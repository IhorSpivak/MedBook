package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.points.ExecuteTransactionData;

public class ExecuteTransactionResponse {

    public boolean status;
    public String message;
    public String user_message;
    public ExecuteTransactionData data;
}
