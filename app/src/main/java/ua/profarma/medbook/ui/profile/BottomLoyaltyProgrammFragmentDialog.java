package ua.profarma.medbook.ui.profile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ua.profarma.medbook.R;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LinkGenerator;

public class BottomLoyaltyProgrammFragmentDialog extends DialogFragment {

    public static BottomLoyaltyProgrammFragmentDialog newInstance() {
        BottomLoyaltyProgrammFragmentDialog fragment = new BottomLoyaltyProgrammFragmentDialog();

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getContext(), R.style.DialogBottomFullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_bottom_loyalty, container, false);

        LinearLayout rulesLay = rootView.findViewById(R.id.rules);
        LinearLayout instructionsLay = rootView.findViewById(R.id.instructions);
        rulesLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.openLink(LinkGenerator.newUrlRules());
            }
        });

        instructionsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.openLink(LinkGenerator.newUrlInstructions());
            }
        });

        ImageView imvClose = rootView.findViewById(R.id.close);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}
