package ua.profarma.medbook.types.requests;

public class DeviceRequest {

    public int id;
    public int user_id;
    public String imei;
    public String push_token;
    public String device_type = "phone";
    public String device_name;
    public String application_version;
}
