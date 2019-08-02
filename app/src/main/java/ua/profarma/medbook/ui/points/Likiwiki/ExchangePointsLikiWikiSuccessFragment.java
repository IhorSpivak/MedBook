package ua.profarma.medbook.ui.points.Likiwiki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.types.points.ExecuteTransactionData;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.utils.TextUtils;

public class ExchangePointsLikiWikiSuccessFragment extends MedBookFragment {
    private static final String KEY_ARGUMENT_DATA = "KEY_ARGUMENT_DATA";
    private static final String KEY_ARGUMENT_MESSAGE = "KEY_ARGUMENT_MESSAGE";

    private ExecuteTransactionData data;
    private String mMessage;
    private TextView tvWrittenOffText;
    private TextView tvWrittenOffPoints;
    private TextView tvMessage;
    private Button btnClose;


    public static ExchangePointsLikiWikiSuccessFragment newInstance(ExecuteTransactionData data, String message) {
        ExchangePointsLikiWikiSuccessFragment fragment = new ExchangePointsLikiWikiSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ARGUMENT_DATA, data);
        bundle.putString(KEY_ARGUMENT_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchange_points_likiwiki_success, container, false);
        if (getArguments() == null && getActivity() != null) getActivity().finish();
        data = getArguments().getParcelable(KEY_ARGUMENT_DATA);
        mMessage = getArguments().getParcelable(KEY_ARGUMENT_MESSAGE);
        if (data == null && getActivity() != null) getActivity().finish();

        tvWrittenOffText = rootView.findViewById(R.id.fragment_exchange_points_success_written_off_text);
        tvWrittenOffPoints = rootView.findViewById(R.id.fragment_exchange_points_success_written_off_points);
        tvMessage = rootView.findViewById(R.id.fragment_exchange_points_success_message);
        btnClose  = rootView.findViewById(R.id.fragment_exchange_points_success_close);
        btnClose.setOnClickListener(view -> getActivity().finish());

        tvMessage.setText(TextUtils.getString(mMessage));
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
        tvWrittenOffText.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_success_written_off_text));
        btnClose.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_success_close));
    }
}
