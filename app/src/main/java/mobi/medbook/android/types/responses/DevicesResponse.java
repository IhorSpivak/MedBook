package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.requests.DeviceRequest;

public class DevicesResponse {

    public boolean success;
    public Errors errors;
    public DeviceRequest[] items;
}
