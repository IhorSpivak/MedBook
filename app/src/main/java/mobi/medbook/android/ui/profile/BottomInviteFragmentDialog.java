package mobi.medbook.android.ui.profile;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LinkGenerator;

public class BottomInviteFragmentDialog extends DialogFragment {

    public static BottomInviteFragmentDialog newInstance() {
        BottomInviteFragmentDialog fragment = new BottomInviteFragmentDialog();

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
        View rootView = inflater.inflate(R.layout.dialog_bottom_invite, container, false);

        LinearLayout infoLay = rootView.findViewById(R.id.info);
        infoLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.openLink(LinkGenerator.newUrlBringFriend());
            }
        });

        ImageView imvClose = rootView.findViewById(R.id.close);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        TextView tvInviteLink = rootView.findViewById(R.id.invite_link);
        tvInviteLink.setText(App.getUser().invite_link);

        LinearLayout llShare = rootView.findViewById(R.id.share);
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = App.getUser().invite_link;
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
            }
        });

        LinearLayout llCopy = rootView.findViewById(R.id.copy);
        llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(App.getUser().invite_link, App.getUser().invite_link);
                clipboard.setPrimaryClip(clip);
            }
        });


        return rootView;
    }
}
