package ua.profarma.medbook.ui.materials;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.user_content.EventMaterialTranlateLoad;
import ua.profarma.medbook.ui.custom_views.ImageHelper;
import ua.profarma.medbook.ui.custom_views.RoundedCornersTransform;
import ua.profarma.medbook.utils.LogUtils;

public class MaterialInfoFragment extends Fragment implements EventListener{
    public static MaterialInfoFragment newInstance() {
        MaterialInfoFragment fragment = new MaterialInfoFragment();
        return fragment;
    }

    private ImageView imIcon;
    private TextView tvTitle;
    private TextView tvDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_material_info, container, false);

        ImageView imClose = rootView.findViewById(R.id.fragment_material_info_close);
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        TextView tvtoolbarTitle = rootView.findViewById(R.id.fragment_material_info_title);
         imIcon = rootView.findViewById(R.id.fragment_material_info_image);
         tvTitle = rootView.findViewById(R.id.fragment_material_info_title_item);
         tvDescription = rootView.findViewById(R.id.fragment_material_info_info_decription);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventRouter.register(this);
    }

    @Override
    public void onDestroyView() {
        EventRouter.unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()){
            case Event.EVENT_MATERIAL_TRANSLATE_LOAD:
                LogUtils.logD("thfgj67rutfg", "onEvent EVENT_MATERIAL_TRANSLATE_LOAD");
                EventMaterialTranlateLoad eventMaterialTranlateLoad = (EventMaterialTranlateLoad)event;
                if(eventMaterialTranlateLoad.getMaterialTranslation() != null){
                    if(eventMaterialTranlateLoad.getMaterialTranslation().description != null && !eventMaterialTranlateLoad.getMaterialTranslation().description.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvDescription.setText(Html.fromHtml(eventMaterialTranlateLoad.getMaterialTranslation().description, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvDescription.setText(Html.fromHtml(eventMaterialTranlateLoad.getMaterialTranslation().description));
                    }
                    tvTitle.setText(eventMaterialTranlateLoad.getMaterialTranslation().name);
                    Picasso.get().load(eventMaterialTranlateLoad.getMaterialTranslation().logo).into(imIcon);
//                            new Target() {
//                        @Override
//                        public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
//                            /* Save the bitmap or do something with it here */
//                            Bitmap roundTopBitmap = ImageHelper.getRoundedCornerBitmap(bitmap, 10);
//                            imIcon.setImageBitmap(roundTopBitmap);
//                            //Set it in the ImageView
//                           // theView.setImageBitmap(bitmap);
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });
                }
                break;
        }
    }
}
