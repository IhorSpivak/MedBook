package ua.profarma.medbook.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ua.profarma.medbook.IOnDialogInterface;
import ua.profarma.medbook.R;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnDialogCommentDelete;
import ua.profarma.medbook.ui.profile.BottomResultDialog;

public class DialogBuilder {

    public static abstract class ConfirmCallback {
        public abstract void confirm();

        public void cancel() {
        }
    }

    public static void showNoPermissionsDialog(Context context, final ConfirmCallback callback) {
        if (context == null) {

            return;
        }

        final Dialog dialog = buildNoPermissionsDialog(context);
        dialog.findViewById(R.id.dialog_accept_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.confirm();
                }
            }
        });


        showDialog(context, dialog);
    }

    public static Dialog buildNoPermissionsDialog(Context context) {
        if (context == null) {

            return null;
        }

        return buildInfoDialog(context, context.getResources().getString(R.string.message), context.getResources().getString(R.string.no_permission));
    }

    public static Dialog buildInfoDialog(final @NonNull Context context, String title, String description) {

        Dialog dialog = buildDialog(context, title, description);
        dialog.findViewById(R.id.dialog_cancel_button).setVisibility(View.GONE);
        return dialog;
    }

    private static void showDialog(Context context, Dialog dialog) {
        if (context == null || dialog == null) {
            return;
        }

        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Dialog buildDialog(final @NonNull Context context, String title, String description) {
        return buildDialog(context, title, description, R.layout.dialog);
    }

    private static Dialog buildDialog(final @NonNull Context context, String title, String description, int contentView) {
        final Dialog dialog = buildDefaultDialog(context, contentView);

        TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        TextView dialogText = dialog.findViewById(R.id.dialog_text);
        TextView dialogAcceptButton = dialog.findViewById(R.id.dialog_accept_button);
        TextView dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_button);

        dialogTitle.setText(title == null ? "" : title);
        dialogText.setText(description == null ? "" : description);
        if (description == null) {
            dialogText.setVisibility(View.GONE);
        }

        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialogAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog buildDefaultDialog(@NonNull Context context, int contentViewId) {

        return buildDefaultDialog(context, contentViewId, null);
    }

    public static Dialog buildDefaultDialog(@NonNull Context context, int contentViewId, Integer
            style) {

        Dialog dialog = style == null ? new Dialog(context) : new Dialog(context, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        View view = LayoutInflater.from(context).inflate(contentViewId, null, false);

        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }

//    public static Dialog buildDarkDialog(final @NonNull Context context, String title, String description) {
//        return buildDialog(context, title, description, R.layout.dark_dialog);
//    }

//    public static Dialog showConfirmDialogDark(Context context, String title, String description, final ConfirmCallback confirmCallback) {
//        if (context == null) {
//            return null;
//        }
//
//        Dialog dialog = buildDarkDialog(context, title, description);
//
//        TextView desc = dialog.findViewById(R.id.dialog_text);
//        if (GeneralUtils.isNullOrEmpty(description)) {
//            desc.setVisibility(View.GONE);
//        }
//
//
//        if (confirmCallback != null) {
//            dialog.findViewById(R.id.dialog_accept_button).setOnClickListener(v -> {
//                confirmCallback.confirm();
//                dialog.dismiss();
//            });
//            dialog.findViewById(R.id.dialog_cancel_button).setOnClickListener(v -> {
//                confirmCallback.cancel();
//                dialog.dismiss();
//            });
//        }
//
//        showDialog(context, dialog);
//
//        return dialog;
//    }

    public static Dialog showBottomResultDialog(Context context, String message, BottomResultDialog.Callback callback) {
        BottomResultDialog bottomResultDialog = BottomResultDialog.create(context, message, callback);
        bottomResultDialog.show();
        return bottomResultDialog;
    }

    public static Dialog showInfoDialog(Context context, int titleRes, int messageRes) {
        if (context == null) {
            return null;
        }

        return showInfoDialog(context, context.getResources().getString(titleRes), context.getResources().getString(messageRes));
    }

    public static Dialog showInfoDialog(Context context, String title, String message) {
        Dialog dialog = buildInfoDialog(context, title, message);
        if (dialog == null) {
            return null;
        }

        showDialog(context, dialog);
        return dialog;
    }

    public static Dialog buildDialogDeleteComment(final @NonNull Context context, int id, String title, String description) {
        final Dialog dialog = buildDialog(context, title, description);

        TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        TextView dialogText = dialog.findViewById(R.id.dialog_text);
        TextView dialogAcceptButton = dialog.findViewById(R.id.dialog_accept_button);
        TextView dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_button);

        dialogTitle.setText(title == null ? "" : title);
        dialogText.setText(description == null ? "" : description);
        if (description == null) {
            dialogText.setVisibility(View.GONE);
        }

        dialogCancelButton.setOnClickListener(view -> {
            dialogCancelButton.setEnabled(false);
            getActivity(context).onCancelCommentDialogDialog(id);
            dialog.dismiss();
        });
        dialogAcceptButton.setOnClickListener(view -> {
            dialogAcceptButton.setEnabled(false);
            getActivity(context).onOkCommentDialogDialog(id);
            dialog.dismiss();
        });

        return dialog;
    }

    public static Dialog buildDialogDeleteInterface(final @NonNull Context context, String title, String description) {
        final Dialog dialog = buildDialog(context, title, description);

        TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        TextView dialogText = dialog.findViewById(R.id.dialog_text);
        TextView dialogAcceptButton = dialog.findViewById(R.id.dialog_accept_button);
        TextView dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_button);
        dialogCancelButton.setVisibility(View.GONE);

        dialogTitle.setText(title == null ? "" : title);
        dialogText.setText(description == null ? "" : description);
        if (description == null) {
            dialogText.setVisibility(View.GONE);
        }

        dialogAcceptButton.setOnClickListener(view -> {
            dialogAcceptButton.setEnabled(false);
//            getActivityIterface(context).onOkDialog()



            dialog.dismiss();

        });

        return dialog;
    }

    public static Dialog showInfoDialogInterface(final @NonNull Context context, String title, String description) {
        Dialog dialog = buildDialogDeleteInterface(context, title, description);
        if (dialog == null) {
            return null;
        }

        showDialog(context, dialog);
        return dialog;
    }


    public static Dialog showCommentDeleteDialog(final @NonNull Context context, int id, String title, String description) {
        Dialog dialog = buildDialogDeleteComment(context, id, title, description);
        if (dialog == null) {
            return null;
        }

        showDialog(context, dialog);
        return dialog;
    }

    private static IOnDialogCommentDelete getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnDialogCommentDelete) {
                return (IOnDialogCommentDelete) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private static IOnDialogInterface getActivityIterface(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnDialogInterface) {
                return (IOnDialogInterface) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
