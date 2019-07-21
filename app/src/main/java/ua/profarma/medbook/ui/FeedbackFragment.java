package ua.profarma.medbook.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ua.profarma.medbook.App;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.EventCloseFeedbackFragment;
import ua.profarma.medbook.events.core.EventRouter;

public class FeedbackFragment extends Fragment {
    public static final String MEDBOOK_HOTLINE_PHONE = "+380675085101";

    public static FeedbackFragment newInstance() {
        FeedbackFragment fragment = new FeedbackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        ImageView imgClose = rootView.findViewById(R.id.close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventCloseFeedbackFragment());
            }
        });
        LinearLayout llPhone = rootView.findViewById(R.id.phone);
        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", MEDBOOK_HOTLINE_PHONE, null));
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }
}
