package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.materials.MaterialTranslation;

public class MaterialTranslateResponse {

    public boolean success;
    public Errors errors;
    public MaterialTranslation[] items;
}
