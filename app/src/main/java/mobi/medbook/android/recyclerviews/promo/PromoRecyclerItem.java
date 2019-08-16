package mobi.medbook.android.recyclerviews.promo;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.visits.Promo;
import mobi.medbook.android.ui.calendar.IOnUpdateIssuedQty;


public class PromoRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Promo promo;

    public PromoRecyclerItem(Promo promo) {
        this.promo = promo;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof PromoViewHolder) {
            ((PromoViewHolder) holder).init(this, promo);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_promo_plus) {
            if (promo.issuedQty < promo.count) {
                promo.issuedQty = promo.issuedQty + 1;
                if (getActivity(view.getContext()) != null)
                    getActivity(view.getContext()).onUpdateIssuedQty(promo);
            }
        } else if (view.getId() == R.id.item_promo_minus) {
            if (promo.issuedQty > 0) {
                promo.issuedQty = promo.issuedQty - 1;
                if (getActivity(view.getContext()) != null)
                    getActivity(view.getContext()).onUpdateIssuedQty(promo);
            }
        }
    }

    private IOnUpdateIssuedQty getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnUpdateIssuedQty) {
                return (IOnUpdateIssuedQty) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }
}
