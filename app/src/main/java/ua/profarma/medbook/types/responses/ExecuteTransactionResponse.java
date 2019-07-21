package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.points.ExecuteTransactionData;

public class ExecuteTransactionResponse {

    public boolean status;
    public String message;
    public String user_message;
    public ExecuteTransactionData data;
}
