package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;
import ua.profarma.medbook.types.materials.Material;

public class MaterialsResponse {

    public boolean success;
    public Errors errors;
    public Material[] items;
}
