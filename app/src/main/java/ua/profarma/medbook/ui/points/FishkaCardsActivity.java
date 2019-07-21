package ua.profarma.medbook.ui.points;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.points.EventLoadFishkaCards;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardRecyclerItem;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardRecyclerItems;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardsRecyclerView;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class FishkaCardsActivity extends MedBookActivity implements IOnSelectFishkaCard, IOnDeleteFishkaCard {

    private TextView mTvTitle;
    private FishkaCardsRecyclerView list;
    private FishkaCardRecyclerItems items;
    private TextView mTvGotoFishKA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishka_cards);
        mTvGotoFishKA = findViewById(R.id.fragment_exchange_likiwiki_goto);
        mTvGotoFishKA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FishkaCardsActivity.this, FishkaRulesActivity.class);
                startActivity(intent);
            }
        });

        list = findViewById(R.id.activity_fishka_cards_list);
        list.init();

        ImageView imAdd = findViewById(R.id.activity_fishka_cards_toolbar_add);
        //imAdd.setEnabled(false);
        imAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FishkaCardsActivity.this, AddFishkaCardActivity.class);
                startActivity(intent);
            }
        });
        ImageView imBack = findViewById(R.id.activity_fishka_cards_toolbar_close);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvTitle = findViewById(R.id.activity_fishka_cards_toolbar_title);

        Core.get().PointControl().getUserFishkaCrads();
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        mTvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_fishka_cards_toolbar_title));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_LOGOUT)
            finish();
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_FISHKA_CARDS:
                if (items == null) {
                    items = new FishkaCardRecyclerItems();
                    for (int i = 0; i < ((EventLoadFishkaCards) event).getItems().length; i++)
                        items.add(new FishkaCardRecyclerItem(((EventLoadFishkaCards) event).getItems()[i].id, ((EventLoadFishkaCards) event).getItems()[i].loyalty_id));


                    //items.add(new FishkaCardRecyclerItem(1789, "777999111222555"));
                    //items.add(new FishkaCardRecyclerItem(1999, "000777111555333"));


                    list.itemsAdd(items);
                } else {
                    for (int i = 0; i < ((EventLoadFishkaCards) event).getItems().length; i++)
                        if (items.find(((EventLoadFishkaCards) event).getItems()[i].loyalty_id) == null) {
                            items.add(new FishkaCardRecyclerItem(((EventLoadFishkaCards) event).getItems()[i].id, ((EventLoadFishkaCards) event).getItems()[i].loyalty_id));
                            list.itemAdd(new FishkaCardRecyclerItem(((EventLoadFishkaCards) event).getItems()[i].id, ((EventLoadFishkaCards) event).getItems()[i].loyalty_id));
                        }
                }
                break;
        }
    }

    @Override
    public void onSelectFishkaCard(String cardNumber) {
        Double points = Double.parseDouble(App.getUser().points);
        if (points.intValue() < 100) {
            DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.not_enough_points_to_exchange));
        } else {
            Intent intent = new Intent(this, ExchangePointsForFishkaActivity.class);
            intent.putExtra(ExchangePointsForFishkaActivity.CARD_NUMBER, cardNumber);
            startActivity(intent);
        }
    }

    @Override
    public void onDeleteFishkaCardRecyclerView(int pos) {
        Core.get().PointControl().deleteUserFishkaCard(((FishkaCardRecyclerItem) items.get(pos)).getId());
        items.remove(pos);
        list.itemRemove(pos);
    }
}
