package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;
import ua.profarma.medbook.types.materials.MaterialTranslation;

public class MaterialTranslateResponse {

    public boolean success;
    public Errors errors;
    public MaterialTranslation[] items;
}
