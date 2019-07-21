package ua.profarma.medbook.ui.points;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.points.EventUpdateKeyLikiWiki;
import ua.profarma.medbook.types.requests.CheckUserLikiWikiRequest;
import ua.profarma.medbook.ui.TermAndPointAgreementsActivity;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.ui.points.Likiwiki.ExchangeLikiWikiActivity;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LinkGenerator;

public class PageExchangeFragment extends MedBookFragment {
    public static Fragment newInstance() {
        PageExchangeFragment fragment = new PageExchangeFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_exchange, container, false);

        LinearLayout llFishka = rootView.findViewById(R.id.fragment_page_exchange_fishka);
        llFishka.setOnClickListener(view -> {
            if (App.getUser().allowed_exchange_points == 0) {
                AppUtils.openLink(LinkGenerator.newUrlPayout());
            } else {
                Intent intent = new Intent(getActivity(), FishkaCardsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        LinearLayout llLikiWiki = rootView.findViewById(R.id.fragment_page_exchange_likiwiki);
        llLikiWiki.setOnClickListener(view -> {
            if (App.getUser().allowed_exchange_points == 0) {
                AppUtils.openLink(LinkGenerator.newUrlPayout());
            } else {
                if (App.getUser().likiwiki_auth_token != null && !App.getUser().likiwiki_auth_token.isEmpty()) {
                    getActivity().startActivity(new Intent(getActivity(), ExchangeLikiWikiActivity.class));
                } else {
                    CheckUserLikiWikiRequest checkUserLikiWikiRequest = new CheckUserLikiWikiRequest();
                    checkUserLikiWikiRequest.email = App.getUser().email;
                    checkUserLikiWikiRequest.user_id = App.getUser().id;
                    checkUserLikiWikiRequest.name = App.getUser().last_name + " " + App.getUser().last_name + " " + App.getUser().last_name;
                    Core.get().Api2Control().checkUserLikiWiki(checkUserLikiWikiRequest);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_UPDATE_KEY_LIKI_WIKI) {
            if (((EventUpdateKeyLikiWiki) event).isStatus()) {
                getActivity().startActivity(new Intent(getActivity(), ExchangeLikiWikiActivity.class));
            } else {
                DialogBuilder.showInfoDialog(getActivity(), Core.get().LocalizationControl().getText(R.id.general_message),
                        ((EventUpdateKeyLikiWiki) event).getUser_message() == null || ((EventUpdateKeyLikiWiki) event).getUser_message().isEmpty() ?
                                "" : ((EventUpdateKeyLikiWiki) event).getUser_message());
            }
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
