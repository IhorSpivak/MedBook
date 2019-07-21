package ua.profarma.medbook.recyclerviews.materials;

import android.view.View;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.materials.EventMaterialFragmentStart;
import ua.profarma.medbook.events.materials.EventMaterialInfoFragmentStart;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.materials.Material;

public class MaterialListRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Material material;

    public MaterialListRecyclerItem(Material material) {
        this.material = material;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof MaterialViewHolder) {
            ((MaterialViewHolder) holder).init(this, material);
        }
    }

    @Override
    public int getViewType() {
        return 1;
    }

    @Override
    public void onClick(View view) {
        //view.getContext().startActivity(new Intent(view.getContext(), MaterialActivity.class));
        if (view.getId() == R.id.item_materials_type_list_imv_info) {
            Core.get().UserContentControl().getMaterialTranslate(material.translations[0].id);
            EventRouter.send(new EventMaterialInfoFragmentStart());
        } else {
            Core.get().UserContentControl().setSelectedMaterial(material);
            EventRouter.send(new EventMaterialFragmentStart());
        }
//        Context context = view.getContext();
//        if (context instanceof OnDetailsClickListener) {
//            ((OnDetailsClickListener) context).onDetailsClick(r030, txt, rate, cc, exchangedate);
//        }
    }
}
