package ua.profarma.medbook.ui.points;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.points.EventLoadFishkaCards;
import ua.profarma.medbook.models.CardFishka;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardRecyclerItem;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardRecyclerItems;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardsRecyclerView;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.reference.ReferenceActivity;
import ua.profarma.medbook.ui.reference.ReferenceListAdapter;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class FishkaCardsActivity extends MedBookActivity {

    private TextView mTvTitle;
    private RecyclerView rv;
    private FishkaCardRecyclerItems items;
    private TextView mTvGotoFishKA;

    private List<CardFishka> list = new ArrayList<>();

    private FishkaCardsAdapter fishkaCardsAdapter;
    private GridLayoutManager mGridLayoutManager;

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

        rv = findViewById(R.id.activity_fishka_cards_list);

        rv.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mGridLayoutManager);

        fishkaCardsAdapter = new FishkaCardsAdapter(this, new FishkaCardsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardFishka item) {
                onSelectFishkaCard(item.getNumber());
            }

            @Override
            public void onIsRefused(CardFishka item) {
                onDeleteFishkaCardRecyclerView(item.getId());
                fishkaCardsAdapter.removeItem(item);
            }
        });

        rv.setAdapter(fishkaCardsAdapter);


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
                for (int i = 0; i < ((EventLoadFishkaCards) event).getItems().length; i++){
                list.add( new CardFishka(((EventLoadFishkaCards) event).getItems()[i].id, ((EventLoadFishkaCards) event).getItems()[i].loyalty_id));
                }
                fishkaCardsAdapter.addAll(list);
                break;
        }
    }


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


    public void onDeleteFishkaCardRecyclerView(int pos) {
        Core.get().PointControl().deleteUserFishkaCard(((FishkaCardRecyclerItem) items.get(pos)).getId());
//        items.remove(pos);
//        list.itemRemove(pos);
    }
}
