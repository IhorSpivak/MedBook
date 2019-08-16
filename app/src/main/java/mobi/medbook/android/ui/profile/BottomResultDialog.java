package mobi.medbook.android.ui.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

import mobi.medbook.android.R;


public class BottomResultDialog extends BottomSheetDialog {
    private Callback callback;

    public BottomResultDialog(@NonNull Context context) {
        super(context);
    }

    public BottomResultDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomResultDialog(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static BottomResultDialog create(Context context, String message, final Callback callback) {
        final BottomResultDialog bottomResultDialog = new BottomResultDialog(context);
        bottomResultDialog.setContentView(R.layout.dialog_bottom_result);
        ((TextView) bottomResultDialog.findViewById(R.id.title)).setText(message);
        bottomResultDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomResultDialog.dismiss();
                if (callback != null) {
                    callback.onClose();
                }
            }
        });
        bottomResultDialog.callback = callback;
        return bottomResultDialog;
    }

    public interface Callback {
        void onClose();
    }
}
