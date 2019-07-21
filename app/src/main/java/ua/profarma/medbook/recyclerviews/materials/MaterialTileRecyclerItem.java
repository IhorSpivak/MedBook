package ua.profarma.medbook.recyclerviews.materials;

import ua.profarma.medbook.types.materials.Material;

public class MaterialTileRecyclerItem extends MaterialListRecyclerItem  {


    public MaterialTileRecyclerItem(Material material) {
        super(material);
    }

    @Override
    public int getViewType() {
        return 2;
    }
}
