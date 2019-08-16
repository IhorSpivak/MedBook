package mobi.medbook.android.ui.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.authorization.EventRestore;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.TextUtils;


public class RestoreFragment extends Fragment implements EventListener {
    public static RestoreFragment newInstance() {
        final RestoreFragment fragment = new RestoreFragment();
        return fragment;
    }

    private Button btnEnter;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_restore, container, false);

        final AppCompatEditText emailEt = rootView.findViewById(R.id.fragment_restore_tiet_email);
        btnEnter = rootView.findViewById(R.id.fragment_restore_btn_send);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = null;
                email = emailEt.getText().toString();
                if (TextUtils.isValidEmail(email)) {
                    btnEnter.setEnabled(false);
                    Core.get().AuthorizationControl().restore(email);
                } else {
                    AppUtils.toastError(getString(R.string.error_email), true);
                }
            }
        });
        TextView btnBack = rootView.findViewById(R.id.fragment_restore_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        EventRouter.register(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_RESTORE:
                EventRestore eventRestore = (EventRestore) event;
                if (eventRestore.isSuccess()) {
                } else {
                    AppUtils.toastError(eventRestore.getMesssage() != null && !eventRestore.getMesssage().isEmpty() ? eventRestore.getMesssage() : getString(R.string.unknown_restore_request), false);

                }
                if(getActivity() != null)
                    getActivity().onBackPressed();
                btnEnter.setEnabled(true);
                break;
        }
    }
}
