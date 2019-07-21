package ua.profarma.medbook.ui.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ua.profarma.medbook.R;



public class CheckEmailForInstructionFragment extends Fragment  {

    public static CheckEmailForInstructionFragment newInstance() {
        final CheckEmailForInstructionFragment fragment = new CheckEmailForInstructionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_check_email_for_instrcutions, container, false);

        Button btnEnter = rootView.findViewById(R.id.fragment_check_email_for_instrcutions_ok);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }

}
