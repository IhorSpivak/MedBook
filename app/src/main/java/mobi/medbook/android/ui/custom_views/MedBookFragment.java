package mobi.medbook.android.ui.custom_views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;


public abstract class MedBookFragment extends Fragment implements EventListener {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EventRouter.register(this);
        super.onViewCreated(view, savedInstanceState);
        onLocalizationUpdate();
    }

    @Override
    public void onDestroyView() {
        EventRouter.unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        App.updateLanguage();
    }

    @Override
    public void onEvent(Event event) {
        if (event.getEventId() == Event.EVENT_LOCALIZATION_UPDATE) {
            onLocalizationUpdate();
        }
    }

    protected void setHint(EditText editText, int key) {
        if (editText != null) {
            editText.setHint(Core.get().LocalizationControl().getText(key));
        }
    }

    protected void setText(TextView textView, int key) {
        if (textView != null) {
            textView.setText(Core.get().LocalizationControl().getText(key));
        }
    }

    protected abstract void onLocalizationUpdate();
}
