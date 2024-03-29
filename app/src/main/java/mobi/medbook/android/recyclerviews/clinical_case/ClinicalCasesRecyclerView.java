package mobi.medbook.android.recyclerviews.clinical_case;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseRecyclerView;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;




public class ClinicalCasesRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/NewsRecyclerView";

    public ClinicalCasesRecyclerView(Context context) {
        this(context, null);
    }

    public ClinicalCasesRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClinicalCasesRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        return new ClinicalCaseViewHolder(itemView);
    }
}
