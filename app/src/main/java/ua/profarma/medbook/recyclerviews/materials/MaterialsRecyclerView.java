package ua.profarma.medbook.recyclerviews.materials;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.utils.LogUtils;

public class MaterialsRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/MaterialsRecyclerView";

    public MaterialsRecyclerView(Context context) {
        this(context, null);
    }

    public MaterialsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MaterialsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = null;
        LogUtils.logD(TAG, "viewType = " + viewType);
        if (viewType == 1)
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_material_type_list, parent, false);
        else if (viewType == 2)
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_material_type_tile, parent, false);
        return new MaterialViewHolder(itemView);
    }
}
