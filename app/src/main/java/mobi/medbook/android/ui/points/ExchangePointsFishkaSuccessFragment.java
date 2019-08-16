package mobi.medbook.android.ui.points;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.types.points.ExecuteTransactionData;
import mobi.medbook.android.ui.MainActivity;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.utils.TextUtils;


public class ExchangePointsFishkaSuccessFragment extends MedBookFragment {
    private static final String KEY_ARGUMENT_DATA = "KEY_ARGUMENT_DATA";
    private static final String KEY_ARGUMENT_MESSAGE = "KEY_ARGUMENT_MESSAGE";

    private ExecuteTransactionData data;
    private String mMessage;
    private TextView tvCountedText;
    private TextView tvCountedPoints;
    private TextView tvWrittenOffText;
    private TextView tvWrittenOffPoints;
    private TextView tvMessage;
    private Button btnClose;

    public static ExchangePointsFishkaSuccessFragment newInstance(ExecuteTransactionData data, String message) {
        ExchangePointsFishkaSuccessFragment fragment = new ExchangePointsFishkaSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ARGUMENT_DATA, data);
        bundle.putString(KEY_ARGUMENT_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchange_points_fishka_success, container, false);
        if (getArguments() == null && getActivity() != null) getActivity().finish();
        data = getArguments().getParcelable(KEY_ARGUMENT_DATA);
        mMessage = getArguments().getParcelable(KEY_ARGUMENT_MESSAGE);
        if (data == null && getActivity() != null) getActivity().finish();

        tvCountedText = rootView.findViewById(R.id.fragment_exchange_points_success_counted_text);
        tvCountedPoints = rootView.findViewById(R.id.fragment_exchange_points_success_counted_points);
        tvWrittenOffText = rootView.findViewById(R.id.fragment_exchange_points_success_written_off_text);
        tvWrittenOffPoints = rootView.findViewById(R.id.fragment_exchange_points_success_written_off_points);
        tvMessage = rootView.findViewById(R.id.fragment_exchange_points_success_message);
        btnClose  = rootView.findViewById(R.id.fragment_exchange_points_success_close);
        btnClose.setOnClickListener(view -> {
            {
                Intent intentDoc = new Intent(getActivity(), MainActivity.class);
                intentDoc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentDoc);
            }
        });

        tvMessage.setText(TextUtils.getString(mMessage));
        tvCountedPoints.setText(String.valueOf(data.AmountToEnrollment));
        tvWrittenOffPoints.setText(String.valueOf(data.value));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvCountedText.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_success_counted_text));
        tvWrittenOffText.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_success_written_off_text));
        btnClose.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_success_close));
    }
}
