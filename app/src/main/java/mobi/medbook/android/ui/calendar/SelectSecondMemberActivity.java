package mobi.medbook.android.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Collections;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.visits_second_member.SecondMemberVisitRecyclerItem;
import mobi.medbook.android.recyclerviews.visits_second_member.SecondMemberVisitsRecyclerView;
import mobi.medbook.android.types.visits.UserRelation;
import mobi.medbook.android.ui.custom_views.MedBookActivity;



public class SelectSecondMemberActivity extends MedBookActivity implements IOnSelectMemberVisit {

    private SecondMemberVisitsRecyclerView list;
    private TextView tvTitle;
    private RecyclerItems items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Core.get().VisitsControl().getMemberVisitItems() == null || Core.get().VisitsControl().getMemberVisitItems().length == 0)
            finish();
        setContentView(R.layout.activity_select_second_member);
        ImageView imvClose = findViewById(R.id.activity_select_second_member_toolbar_close);
        tvTitle = findViewById(R.id.activity_select_second_member_toolbar_title);
        imvClose.setOnClickListener(view -> finish());
        list = findViewById(R.id.activity_select_second_member_list);
        list.init();
        items = new RecyclerItems();
        for (int i = 0; i < Core.get().VisitsControl().getMemberVisitItems().length; i++) {
            if (Core.get().VisitsControl().getMemberVisitItems()[i].userOne.id != App.getUser().id)
                items.add(new SecondMemberVisitRecyclerItem(Core.get().VisitsControl().getMemberVisitItems()[i].userOne));
            else if (Core.get().VisitsControl().getMemberVisitItems()[i].userTwo.id != App.getUser().id)
                items.add(new SecondMemberVisitRecyclerItem(Core.get().VisitsControl().getMemberVisitItems()[i].userTwo));
        }
        Collections.sort(items, (o1, o2) -> ((SecondMemberVisitRecyclerItem) o1).getUserRelation().middle_name.compareTo((((SecondMemberVisitRecyclerItem) o2).getUserRelation().middle_name)));
        Collections.sort(items, (o1, o2) -> ((SecondMemberVisitRecyclerItem) o1).getUserRelation().first_name.compareTo((((SecondMemberVisitRecyclerItem) o2).getUserRelation().first_name)));
        Collections.sort(items, (o1, o2) -> ((SecondMemberVisitRecyclerItem) o1).getUserRelation().last_name.compareTo((((SecondMemberVisitRecyclerItem) o2).getUserRelation().last_name)));
        list.itemsAdd(items);
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_select_second_member_toolbar_title));
    }

    @Override
    public void onSelectMemberVisit(UserRelation member) {
        Intent intent = new Intent();
        intent.putExtra(AddVisitActivity.ADD_VISIT_ID_MEMBER, member.id);
        intent.putExtra(AddVisitActivity.ADD_VISIT_LAST_NAME, member.last_name);
        intent.putExtra(AddVisitActivity.ADD_VISIT_FIRST_NAME, member.first_name);
        intent.putExtra(AddVisitActivity.ADD_VISIT_MIDDLE_NAME, member.middle_name);
        setResult(RESULT_OK, intent);
        finish();
    }

}



//package ua.profarma.medbook.ui.calendar;
//
//import android.app.SearchManager;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import butterknife.BindView;
//import ua.profarma.medbook.App;
//import ua.profarma.medbook.Core;
//import ua.profarma.medbook.R;
//import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
//import ua.profarma.medbook.recyclerviews.visits_second_member.SecondMemberVisitRecyclerItem;
//import ua.profarma.medbook.recyclerviews.visits_second_member.SecondMemberVisitsRecyclerView;
//import ua.profarma.medbook.types.visits.UserRelation;
//import ua.profarma.medbook.types.visits.UserRelationItem;
//import ua.profarma.medbook.ui.custom_views.MedBookActivity;
//
//
//public class SelectSecondMemberActivity extends MedBookActivity implements IOnSelectMemberVisit {
//
//
//    private TextView tvTitle;
//
//
//    RecyclerView rv;
//
//    SearchView search;
//
//    private SecondMemberListAdapter adapter;
//
//
//
//    private  ArrayList<UserRelation> memberVisitItems = new ArrayList();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (Core.get().VisitsControl().getMemberVisitItems() == null || Core.get().VisitsControl().getMemberVisitItems().length == 0)
//            finish();
//        setContentView(R.layout.activity_select_second_member);
//        ImageView imvClose = findViewById(R.id.activity_select_second_member_toolbar_close);
//        tvTitle = findViewById(R.id.activity_select_second_member_toolbar_title);
//        search = findViewById(R.id.search);
//        rv = findViewById(R.id.rv);
//
//        imvClose.setOnClickListener(view -> finish());
//
////        list = findViewById(R.id.activity_select_second_member_list);
////        list.init();
////        items = new RecyclerItems();
////
////
////        for (int i = 0; i < Core.get().VisitsControl().getMemberVisitItems().length; i++) {
////            if (Core.get().VisitsControl().getMemberVisitItems()[i].userOne.id != App.getUser().id)
////                items.add(new SecondMemberVisitRecyclerItem(Core.get().VisitsControl().getMemberVisitItems()[i].userOne));
////            else if (Core.get().VisitsControl().getMemberVisitItems()[i].userTwo.id != App.getUser().id)
////                items.add(new SecondMemberVisitRecyclerItem(Core.get().VisitsControl().getMemberVisitItems()[i].userTwo));
////        }
////
////
////        Collections.sort(items, (o1, o2) -> ((SecondMemberVisitRecyclerItem) o1).getUserRelation().middle_name.compareTo((((SecondMemberVisitRecyclerItem) o2).getUserRelation().middle_name)));
////        Collections.sort(items, (o1, o2) -> ((SecondMemberVisitRecyclerItem) o1).getUserRelation().first_name.compareTo((((SecondMemberVisitRecyclerItem) o2).getUserRelation().first_name)));
////        Collections.sort(items, (o1, o2) -> ((SecondMemberVisitRecyclerItem) o1).getUserRelation().last_name.compareTo((((SecondMemberVisitRecyclerItem) o2).getUserRelation().last_name)));
////        list.itemsAdd(items);
//
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        search.setIconifiedByDefault(false);
//        search.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//        search.setMaxWidth(Integer.MAX_VALUE);
//        search.setQueryHint("Поиск");
////        androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete =
////                (androidx.appcompat.widget.SearchView.SearchAutoComplete)search.findViewById(androidx.appcompat.R.id.search_src_text);
////        searchAutoComplete.setHintTextColor(Color.GRAY);
////
////        searchAutoComplete.setTextColor(Color.BLACK);
//        LinearLayout searchEditFrame = (LinearLayout) search.findViewById(R.id.search_edit_frame); // Get the Linear Layout
//        ((LinearLayout.LayoutParams) searchEditFrame.getLayoutParams()).leftMargin = 0;
//
//        for (int i = 0; i < Core.get().VisitsControl().getMemberVisitItems().length; i++) {
//            if (Core.get().VisitsControl().getMemberVisitItems()[i].userOne.id != App.getUser().id)
//                memberVisitItems.add(Core.get().VisitsControl().getMemberVisitItems()[i].userOne);
//            else if (Core.get().VisitsControl().getMemberVisitItems()[i].userTwo.id != App.getUser().id)
//                memberVisitItems.add(Core.get().VisitsControl().getMemberVisitItems()[i].userTwo);
//        }
//
//        adapter = new SecondMemberListAdapter();
//
//        rv.setAdapter(adapter);
//        adapter.setItemClickListener(item -> {});
//        rv.setLayoutManager(new LinearLayoutManager(this));
//
//
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//        });
//
//
//
//        onLocalizationUpdate();
//    }
//
//    @Override
//    protected void onLocalizationUpdate() {
//        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_select_second_member_toolbar_title));
//    }
//
//    @Override
//    public void onSelectMemberVisit(UserRelation member) {
//        Intent intent = new Intent();
//        intent.putExtra(AddVisitActivity.ADD_VISIT_ID_MEMBER, member.id);
//        intent.putExtra(AddVisitActivity.ADD_VISIT_LAST_NAME, member.last_name);
//        intent.putExtra(AddVisitActivity.ADD_VISIT_FIRST_NAME, member.first_name);
//        intent.putExtra(AddVisitActivity.ADD_VISIT_MIDDLE_NAME, member.middle_name);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//}
