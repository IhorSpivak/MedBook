package mobi.medbook.android.recyclerviews.materials;

import android.view.View;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialFragmentStart;
import mobi.medbook.android.events.materials.EventMaterialInfoFragmentStart;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;

import mobi.medbook.android.types.materials.Material;


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
