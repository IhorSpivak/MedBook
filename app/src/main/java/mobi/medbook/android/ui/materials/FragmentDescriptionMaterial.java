package mobi.medbook.android.ui.materials;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.api.MCall;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionClose;
import mobi.medbook.android.events.materials.EventUserNotificationReaction;
import mobi.medbook.android.types.materials.Presentation;
import mobi.medbook.android.types.materials.Test;
import mobi.medbook.android.types.materials.Video;
import mobi.medbook.android.types.responses.UserNotificationReactionResponse;
import mobi.medbook.android.ui.custom_views.MedBookFragment;

import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.DialogBuilder;
import mobi.medbook.android.utils.LogUtils;
import retrofit2.Response;


import static android.text.Html.*;

public class FragmentDescriptionMaterial extends MedBookFragment {

    private final static String KEY_ARGUMENT_TYPE_MATERIAL = "KEY_ARGUMENT_TYPE_MATERIAL";
    private final static String KEY_ARGUMENT_ID_TYPE_MATERIAL = "KEY_ARGUMENT_ID_TYPE_MATERIAL";

    private String TAG = "AppMedbook/FragmentDescriptionMaterial";

    private Video mSelectVideo;
    private Presentation mSelectPresentation;
    private Test mSelectTest;

    private TextView tvTitle;
    private ImageView image;
    private TextView tvTitleTest;
    private TextView tvDescription;
    private TextView tDate;
    private Button btnMainGo;
    private LinearLayout llMainPoints;
    private TextView tvMainPoints;
    private TextView tvMainPointsText;
    private TextView tvMSecondaryPoints;
    private TextView tvMSecondaryPointsText;
    private LinearLayout llSecondaryPoints;
    private int idType;
    private int minRecationNotification;
    boolean connected = false;


    public static FragmentDescriptionMaterial newInstance(MaterialsEnum type, int idType) {
        FragmentDescriptionMaterial fragment = new FragmentDescriptionMaterial();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ARGUMENT_TYPE_MATERIAL, type.toString());
        bundle.putInt(KEY_ARGUMENT_ID_TYPE_MATERIAL, idType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_description_material, container, false);

//        minRecationNotification = -1;
//        Core.get().UserContentControl().userNotificationReaction();
        userNotificationReaction();
        llMainPoints = rootView.findViewById(R.id.fragment_material_description_ll_points_main);

        ImageView imClose = rootView.findViewById(R.id.fragment_material_description_close);
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventMaterialDescriptionClose());
            }
        });

        tvTitle = rootView.findViewById(R.id.fragment_material_description_title);
        image = rootView.findViewById(R.id.fragment_material_description_image);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tvTitleTest = rootView.findViewById(R.id.fragment_material_description_title_test);
        tDate = rootView.findViewById(R.id.fragment_material_description_date_test);
        tvDescription = rootView.findViewById(R.id.fragment_material_description_description_test);
        btnMainGo = rootView.findViewById(R.id.fragment_material_description_start);


        tvMainPoints = rootView.findViewById(R.id.fragment_material_description_main_tv_points);
        tvMainPointsText = rootView.findViewById(R.id.fragment_material_description_main_tv_points_text);
        tvMSecondaryPoints = rootView.findViewById(R.id.fragment_material_description_secondary_tv_points);
        tvMSecondaryPointsText = rootView.findViewById(R.id.fragment_material_description_secondary_tv_points_text);
        llSecondaryPoints = rootView.findViewById(R.id.fragment_material_description_ll_points_secondary);
        llSecondaryPoints.setVisibility(View.GONE);

        Video[] videos = new Video[0];
        Presentation[] presentations = new Presentation[0];
        Test[] tests = new Test[0];

        if (Core.get().UserContentControl().getSelectedMaterial().videos != null)
            videos = Core.get().UserContentControl().getSelectedMaterial().videos;
        if (Core.get().UserContentControl().getSelectedMaterial().presentations != null)
            presentations = Core.get().UserContentControl().getSelectedMaterial().presentations;
        if (Core.get().UserContentControl().getSelectedMaterial().tests != null)
            tests = Core.get().UserContentControl().getSelectedMaterial().tests;

        MaterialsEnum materialsEnum = MaterialsEnum.valueOf(getArguments().getString(KEY_ARGUMENT_TYPE_MATERIAL));
        idType = getArguments().getInt(KEY_ARGUMENT_ID_TYPE_MATERIAL);
        switch (materialsEnum) {
            case PRESENTATION:
                mSelectPresentation = getTitlePresentation(idType, presentations);
                updateContent(Core.get().LocalizationControl().getText(R.id.material_presentation), mSelectPresentation.presentation.translations[1].title,
                        mSelectPresentation.time_from,
                        mSelectPresentation.presentation.translations[0].description,
                        mSelectPresentation.presentation.translations[0].logo,
                        mSelectPresentation.presentation_points, getString(R.string.material_description_points_view), R.string.material_description_btn_title_view_presentation);
                break;
            case VIDEO:
                mSelectVideo = getTitleVideo(idType, videos);
                updateContent(Core.get().LocalizationControl().getText(R.id.material_video), mSelectVideo.video.translations[0].title, mSelectVideo.time_from,
                        mSelectVideo.video.translations[0].description, mSelectVideo.video.translations[0].logo,
                        mSelectVideo.video_points, getString(R.string.material_description_points_view), R.string.material_description_btn_title_view_video);
                break;
            case TEST:
                mSelectTest = getTitleTest(idType, tests);
                if (mSelectTest.test.results.length > 0) {
                    llMainPoints.setVisibility(View.INVISIBLE);
                }
                updateContent(Core.get().LocalizationControl().getText(R.id.material_test), mSelectTest.test.translations[0].title, mSelectTest.time_from, mSelectTest.test.translations[0].description, mSelectTest.test.translations[0].logo,
                        mSelectTest.test_points, getString(R.string.material_description_points_testing), mSelectTest.test.results.length == 0 ? R.string.material_description_btn_title_testing : R.string.material_description_btn_title_testing_completed);
                break;
        }

        btnMainGo.setOnClickListener((View v) -> {
            switch (materialsEnum) {
                case PRESENTATION:
                    Intent intentPresentation = new Intent(getContext(), PresentationActivity.class);
                    intentPresentation.putExtra(PresentationActivity.KEY_ID_PRESENTATION, mSelectPresentation.id);
                    intentPresentation.putExtra(PresentationActivity.KEY_ID_MATERIAL_PRESENTATION, Core.get().UserContentControl().getSelectedMaterial().id);
                    getContext().startActivity(intentPresentation);
                    break;
                case VIDEO:
                    if (mSelectVideo.video.translations[0].file != null && !mSelectVideo.video.translations[0].file.isEmpty()) {
                        if (mSelectVideo.video.translations[0].file.contains(".mp4")) {
                            Intent intent = new Intent(getContext(), VideoActivity.class);
                            intent.putExtra(VideoActivity.ARGS_VIDEO, mSelectVideo.video.translations[0].file);
                            getContext().startActivity(intent);
                        } else
                            AppUtils.toastError("Сервер передав невірне посилання. Відео не може бути формату " + mSelectVideo.video.translations[0].file.substring(mSelectVideo.video.translations[0].file.lastIndexOf(".")), true);
                    } else AppUtils.toastError("Вибачте але відео не перегляду немає", true);
                    break;
                case TEST:
                    if(isOnline()) {
                        if (mSelectTest.minimum_notification_reactions <= minRecationNotification) {
                            if (mSelectTest.test.translations.length > 0
                                    && mSelectTest.test.questions.length > 0) {
                                if (mSelectTest.test.results.length == 0) {
                                    LogUtils.logD(TAG, "start TestsActivity");
                                    Intent intentTests = new Intent(getContext(), TestsActivity.class);
                                    intentTests.putExtra(TestsActivity.KEY_ID_TEST, mSelectTest.id);
                                    getContext().startActivity(intentTests);
                                } else {
                                    LogUtils.logD(TAG, "start ViewCompletedTestActivity");
                                    Intent intentTests = new Intent(getContext(), ViewCompletedTestActivity.class);
                                    LogUtils.logD(TAG, "testId = " + mSelectTest.test_id);
                                    intentTests.putExtra(ViewCompletedTestActivity.KEY_TEST_USER_CONTENT_ID, mSelectTest.id);
                                    getContext().startActivity(intentTests);
                                }
                            } else
                                AppUtils.toastError("Вибачте але тести не були завантажені", true);
                        } else {
                            DialogBuilder.showInfoDialogInterface(getActivity(),
                                    Core.get().LocalizationControl().getText(R.id.general_error),
                                    String.format(Core.get().LocalizationControl().getText(R.id.need_push_reaction),
                                            String.valueOf(mSelectTest.minimum_notification_reactions - minRecationNotification))); // todo
                        }
                    } else {
                        Toast.makeText(getActivity(), "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
                    }
                    break;

            }
        });

        return rootView;
    }

    public boolean isOnline() {
        boolean a = false ;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            a = true;
        }
        return a;
    }



    private Video getTitleVideo(int id, Video[] videos) {
        Video result = null;
        for (int i = 0; i < videos.length; i++) {
            if (videos[i].id == id)
                return videos[i];
        }
        return result;
    }

    private Test getTitleTest(int id, Test[] tests) {
        Test result = null;
        for (int i = 0; i < tests.length; i++) {
            if (tests[i].id == id)
                return tests[i];
        }
        return result;
    }

    private Presentation getTitlePresentation(int id, Presentation[] presentations) {
        Presentation result = null;
        for (int i = 0; i < presentations.length; i++) {
            if (presentations[i].id == id)
                return presentations[i];
        }
        return result;
    }

    private void updateContent(String textTitle, String textTitle2, long time, String textDescription, String logo, int points, String pointsText, int idBtGoMain) {
        tvTitle.setText(textTitle);
        tvTitleTest.setText(textTitle2);
        Date date2 = new Date(time * 1000L);
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        DateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
        format2.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted2 = format2.format(date2);
        tDate.setText(formatted2);
        if (textDescription != null && !textDescription.isEmpty())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDescription.setText(fromHtml(textDescription, FROM_HTML_MODE_COMPACT));
            } else {
                tvDescription.setText(fromHtml(textDescription));
            }
        if (logo != null && !logo.isEmpty())
            Picasso.get().load(logo).into(image);
        else
            Picasso.get().load(Core.get().UserContentControl().getSelectedMaterial().translations[0].logo).into(image);

        if (points == 0)
            llMainPoints.setVisibility(View.INVISIBLE);
        else {
            tvMainPoints.setText("+" + points);
            tvMainPointsText.setText(pointsText);
        }
        btnMainGo.setText(getString(idBtGoMain));
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        LogUtils.logD("jgjgyhkjjhbmkjh", "point 0 " + event.getEventId());
        switch (event.getEventId()) {
            case Event.EVENT_USER_NOTIFICATION_REACTION:
                minRecationNotification = ((EventUserNotificationReaction) event).getReactions();
                break;
            case Event.EVENT_MATERIAL_SELECTED_UPDATE:
                Test[] tests = Core.get().UserContentControl().getSelectedMaterial().tests;
                mSelectTest = getTitleTest(idType, tests);
                if (mSelectTest != null) {
                    if (mSelectTest.test.results.length > 0) {
                        llMainPoints.setVisibility(View.INVISIBLE);
                    }
                    updateContent(Core.get().LocalizationControl().getText(R.id.material_test), mSelectTest.test.translations[0].title, mSelectTest.time_from, mSelectTest.test.translations[0].description, mSelectTest.test.translations[0].logo,
                            mSelectTest.test_points, getString(R.string.material_description_points_testing), mSelectTest.test.results.length == 0 ? R.string.material_description_btn_title_testing : R.string.material_description_btn_title_testing_completed);
                }
                break;
        }
    }


    public void userNotificationReaction() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().userNotificationReaction().enqueue(new MCall<UserNotificationReactionResponse>() {
                    @Override
                    public void onSuccess(Response<UserNotificationReactionResponse> response) {
                        if (response != null && response.body() != null && response.body().items != null && response.body().items.length > 0)
                        minRecationNotification = response.body().items[0].reactions;
                        else {

                        }
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }
}
