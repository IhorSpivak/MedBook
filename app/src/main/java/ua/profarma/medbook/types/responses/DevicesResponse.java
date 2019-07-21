package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;
import ua.profarma.medbook.types.requests.DeviceRequest;

public class DevicesResponse {

    public boolean success;
    public Errors errors;
    public DeviceRequest[] items;
}
