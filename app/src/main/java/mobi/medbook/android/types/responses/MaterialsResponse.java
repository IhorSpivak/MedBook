package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.materials.Material;

public class MaterialsResponse {

    public boolean success;
    public Errors errors;
    public Material[] items;
}
