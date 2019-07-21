package ua.profarma.medbook.types.requests;

public class RefreshAccessTokenRequest {
    public String refresh_token;
    public RefreshAccessTokenRequest(String refresh_token){
        this.refresh_token = refresh_token;
    }
}
