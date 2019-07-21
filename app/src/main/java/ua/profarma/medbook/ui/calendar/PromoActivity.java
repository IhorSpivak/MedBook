package ua.profarma.medbook.ui.calendar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.promo.PromoRecyclerItem;
import ua.profarma.medbook.recyclerviews.promo.PromoRecyclerView;
import ua.profarma.medbook.types.visits.Promo;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;

public class PromoActivity extends MedBookActivity implements IOnUpdateIssuedQty{

    private PromoRecyclerView list;
    private RecyclerItems items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        ImageView imClose = findViewById(R.id.activity_promo_toolbar_close);
        imClose.setOnClickListener(view -> finish());

        list = findViewById(R.id.activity_promo_list);
        list.init();
        items = new RecyclerItems();

        for(int i = 0; i < Core.get().VisitsControl().getUserVisitQuestionnaireMP().data.promoArr.length; i ++){
            items.add(new PromoRecyclerItem(Core.get().VisitsControl().getUserVisitQuestionnaireMP().data.promoArr[i]));
        }
        list.itemsAdd(items);

        Button btnSave = findViewById(R.id.activity_promo_save);
        btnSave.setOnClickListener(view -> {
            for(int i = 0; i  < items.size(); i++) {
                Core.get().VisitsControl().setPromo(((PromoRecyclerItem)items.get(i)).getPromo());
            }
            finish();
        });

        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onUpdateIssuedQty(Promo promo) {
        for(int i = 0; i  < items.size(); i++){
            if (((PromoRecyclerItem)items.get(i)).getPromo().seria.equals(promo.seria)){
                ((PromoRecyclerItem)items.get(i)).setPromo(promo);
                list.itemUpdate(i);
            }
        }
    }
}
