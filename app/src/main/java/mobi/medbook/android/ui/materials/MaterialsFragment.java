package mobi.medbook.android.ui.materials;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.user_content.EventMaterialsLoad;
import mobi.medbook.android.recyclerviews.materials.MaterialListRecyclerItem;
import mobi.medbook.android.recyclerviews.materials.MaterialTileRecyclerItem;
import mobi.medbook.android.recyclerviews.materials.MaterialsRecyclerView;

import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;


public class MaterialsFragment extends Fragment implements EventListener {

    private String TAG = "AppMedbook/MaterialsFragment";
    private MaterialsRecyclerView listMaterials;
    private ArrayList<Material> items;

    private final int STATE_LIST = 1;
    private final int STATE_TILE = 2;

    private int stateList = STATE_LIST;

    private ImageView imvList;
    private ImageView imvTile;

    private static final String APP_PREFERENCES = "ua.medbook";
    private static String MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST = "MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST";

    public static MaterialsFragment newInstance() {
        MaterialsFragment fragment = new MaterialsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_materials, container, false);
        listMaterials = rootView.findViewById(R.id.fragment_materials_list);
        LogUtils.logD(TAG, "onCreateView");



        SharedPreferences settings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (settings.contains(MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST)) {
            stateList = settings.getInt(MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST, STATE_LIST);
        } else {
            SharedPreferences.Editor prefsEditor = settings.edit();
            prefsEditor.putInt(MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST, stateList);
            prefsEditor.apply();
        }


        items = Core.get().UserContentControl().getListMaterial();
        listMaterials.init();


        imvList = rootView.findViewById(R.id.fragment_materials_btn_list);
        imvTile = rootView.findViewById(R.id.fragment_materials_btn_tile);

        if (items != null && !items.isEmpty()) {
            {
                for (Material item : items) {
                    listMaterials.itemAdd(new MaterialListRecyclerItem(item));
                }

                switch (stateList) {
                    case STATE_LIST:

                        listMaterials.clear();
                        listMaterials.setLayoutManager(new LinearLayoutManager(getContext()));
                        for (Material item : items) {
                            listMaterials.itemAdd(new MaterialListRecyclerItem(item));
                        }

                        imvList.setImageResource(R.drawable.ic_btn_material_list_select);
                        imvTile.setImageResource(R.drawable.ic_btn_material_tile_unselect);

                        break;

                    case STATE_TILE:
                        listMaterials.clear();

                        listMaterials.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        for (Material item : items) {
                            listMaterials.itemAdd(new MaterialTileRecyclerItem(item));
                        }

                        imvList.setImageResource(R.drawable.ic_btn_material_list_unselect);
                        imvTile.setImageResource(R.drawable.ic_btn_material_tile_select);


                        break;

                }
            }
        } else {
            imvList.setEnabled(false);
            imvTile.setEnabled(false);
        }

        imvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stateList) {
                    case STATE_TILE:

                        listMaterials.clear();
                        listMaterials.setLayoutManager(new LinearLayoutManager(getContext()));
                        for (Material item : items) {
                            listMaterials.itemAdd(new MaterialListRecyclerItem(item));
                        }

                        stateList = STATE_LIST;
                        imvList.setImageResource(R.drawable.ic_btn_material_list_select);
                        imvTile.setImageResource(R.drawable.ic_btn_material_tile_unselect);

                        SharedPreferences.Editor prefsEditor = settings.edit();
                        prefsEditor.putInt(MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST, stateList);
                        prefsEditor.apply();

                        break;
                }
            }
        });

        imvTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stateList) {
                    case STATE_LIST:
                        listMaterials.clear();

                        listMaterials.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        for (Material item : items) {
                            listMaterials.itemAdd(new MaterialTileRecyclerItem(item));
                        }

                        stateList = STATE_TILE;
                        imvList.setImageResource(R.drawable.ic_btn_material_list_unselect);
                        imvTile.setImageResource(R.drawable.ic_btn_material_tile_select);
                        SharedPreferences.Editor prefsEditor = settings.edit();
                        prefsEditor.putInt(MATERIALS_FRAGMENT_PREFERENCE_TYPE_LIST, stateList);
                        prefsEditor.apply();

                        break;
                }
            }
        });
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventRouter.register(this);
        LogUtils.logD(TAG, "onViewCreated");
    }

    @Override
    public void onDestroyView() {
        EventRouter.unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_MATERIALS_LOAD:
                LogUtils.logD(TAG, "onEvent EVENT_MATERIALS_LOAD");
                EventMaterialsLoad eventMaterialsLoad = (EventMaterialsLoad) event;
                if (eventMaterialsLoad.isSuccess()) {
                    LogUtils.logD(TAG, "onEvent EVENT_MATERIALS_LOAD isSuccess");
                    if (listMaterials != null)
                        listMaterials.clear();
                    items = Core.get().UserContentControl().getListMaterial();
                    for (Material item : items) {

                        listMaterials.itemAdd(new MaterialListRecyclerItem(item));
                        LogUtils.logD(TAG, "id = " + item.id);
                    }
                    imvList.setEnabled(true);
                    imvTile.setEnabled(true);
                } else {
                    AppUtils.toastError(eventMaterialsLoad.getMessage(), true);
                }
                break;
        }
    }
}
