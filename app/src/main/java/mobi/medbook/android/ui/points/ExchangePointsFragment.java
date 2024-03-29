package mobi.medbook.android.ui.points;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.ui.points.add_phone.IOnAddPhoneNumber;


public class ExchangePointsFragment extends MedBookFragment {

    public static ExchangePointsFragment newInstance() {
        ExchangePointsFragment fragment = new ExchangePointsFragment();
        return fragment;
    }

    private TextView tvReceiveTitle;
    private TextView tvReceiveValue;
    private TextView tvSendTitle;
    private TextView tvSendValue;
    private ImageView imvAdd;
    private ImageView imvMinus;
    private Button btnGetCode;
    private ProgressBar pb;

    private Button btnAddNumber;
    private TextView tvAddNumber;


    private int valuePoints = 300;
    private Double points;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchange_points, container, false);

        points = Double.parseDouble(App.getUser().points);
        tvReceiveTitle = rootView.findViewById(R.id.fragment_exchange_points_text_1);
        tvReceiveValue = rootView.findViewById(R.id.fragment_exchange_points_text_2);
        tvSendTitle = rootView.findViewById(R.id.fragment_exchange_points_text_3);
        tvSendValue = rootView.findViewById(R.id.fragment_exchange_points_text_4);
        pb = rootView.findViewById(R.id.pb);

        tvAddNumber = rootView.findViewById(R.id.fragment_exchange_points_btn_add_number_caption);
        btnAddNumber = rootView.findViewById(R.id.fragment_exchange_points_btn_add_number);
        btnAddNumber.setVisibility(View.GONE);
        tvAddNumber.setVisibility(View.GONE);

        imvAdd = rootView.findViewById(R.id.fragment_exchange_points_add);
        imvMinus = rootView.findViewById(R.id.fragment_exchange_points_minus);

        tvReceiveValue.setText(String.valueOf(valuePoints * 7 / 10));
        tvSendValue.setText(String.valueOf(valuePoints));

        imvAdd.setOnClickListener(view -> {
            if (valuePoints <= (points - 300)) {
                valuePoints = valuePoints + 300;
                tvReceiveValue.setText(String.valueOf(valuePoints * 7 / 10));
                tvSendValue.setText(String.valueOf(valuePoints));
            }
        });
        imvMinus.setOnClickListener(view -> {
            if (valuePoints > 300) {
                valuePoints = valuePoints - 300;
                tvReceiveValue.setText(String.valueOf(valuePoints * 7 / 10));
                tvSendValue.setText(String.valueOf(valuePoints));
            }
        });

        btnAddNumber.setOnClickListener(view -> {
            if (getActivity() instanceof IOnAddPhoneNumber) {
                ((ExchangePointsForFishkaActivity) getActivity()).onAddPhoneNumber();
            }
        });

        btnGetCode = rootView.findViewById(R.id.fragment_exchange_points_btn_get_code);
        btnGetCode.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            view.setEnabled(false);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    view.setEnabled(true);
                }
            }, 2000);
            if (App.getUser().phone == null || App.getUser().phone.isEmpty()) {
                btnGetCode.setVisibility(View.GONE);
                btnAddNumber.setVisibility(View.VISIBLE);
                tvAddNumber.setVisibility(View.VISIBLE);
            } else {
                //startChange;
                Core.get().Api2Control().getSMSForExchangePoints(valuePoints, "fishka");
            }
        });
        return rootView;


    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_GET_SMS_EXCHANGE_POINTS:
                btnGetCode.setEnabled(true);
                pb.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onLocalizationUpdate() {
        tvReceiveTitle.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_text_1));
        tvSendTitle.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_text_3));
        btnGetCode.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_btn_get_code));
        tvAddNumber.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_btn_add_number_caption));
        btnAddNumber.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_btn_add_number));

    }
}
