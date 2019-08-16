package mobi.medbook.android.recyclerviews.materials;

import mobi.medbook.android.types.materials.Material;

public class MaterialTileRecyclerItem extends MaterialListRecyclerItem {


    public MaterialTileRecyclerItem(Material material) {
        super(material);
    }

    @Override
    public int getViewType() {
        return 2;
    }
}
