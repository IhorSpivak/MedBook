package mobi.medbook.android.ui.points.Likiwiki;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.ui.points.add_phone.IOnAddPhoneNumber;


public class ExchangeLikiWikiFragment extends MedBookFragment {

    private TextView tvChanged;
    private TextView tvPoints;
    private TextView tvGoToLikiWiki;
    private Button btnGetCode;
    private Button btnAddNumber;
    private TextView tvAddNumber;
    private ProgressBar pb;

    private int valuePoints = 0;
    private Double points;
    private ImageView imvAdd;
    private ImageView imvMinus;

    public static ExchangeLikiWikiFragment newInstance() {
        ExchangeLikiWikiFragment fragment = new ExchangeLikiWikiFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchange_liki_wiki, container, false);

        points = Double.parseDouble(App.getUser().points);

        tvChanged = rootView.findViewById(R.id.fragment_exchange_likiwiki_changed);
        pb = rootView.findViewById(R.id.pb);
        tvPoints = rootView.findViewById(R.id.fragment_exchange_likiwiki_points);
        tvGoToLikiWiki = rootView.findViewById(R.id.fragment_exchange_likiwiki_goto);
        btnGetCode = rootView.findViewById(R.id.fragment_exchange_likiwiki_btn_get_code);
        tvAddNumber = rootView.findViewById(R.id.fragment_exchange_likiwiki_btn_add_number_caption);
        btnAddNumber = rootView.findViewById(R.id.fragment_exchange_likiwiki_btn_add_number);

        imvAdd = rootView.findViewById(R.id.fragment_exchange_likiwiki_add);
        imvMinus = rootView.findViewById(R.id.fragment_exchange_likiwiki_minus);

        btnAddNumber.setVisibility(View.GONE);
        tvAddNumber.setVisibility(View.GONE);

        btnAddNumber.setOnClickListener(view -> {
            if(getActivity() instanceof IOnAddPhoneNumber){
                ((ExchangeLikiWikiActivity)getActivity()).onAddPhoneNumber();
            }
        });
        btnGetCode.setOnClickListener(view ->{
            pb.setVisibility(View.VISIBLE);
            //App.updateUserPhone("");
            if(App.getUser().phone == null || App.getUser().phone.isEmpty()){
                btnGetCode.setVisibility(View.GONE);
                btnAddNumber.setVisibility(View.VISIBLE);
                tvAddNumber.setVisibility(View.VISIBLE);
            } else {
                //startChange;
                Core.get().Api2Control().getSMSForExchangePoints(valuePoints, "likiwiki");
            }
        });
        tvPoints.setText(String.valueOf(valuePoints));

        imvAdd.setOnClickListener(view -> {
            if (valuePoints <= (points - 100)) {
                valuePoints = valuePoints + 100;
                tvPoints.setText(String.valueOf(valuePoints));
            }
        });
        imvMinus.setOnClickListener(view -> {

                valuePoints = valuePoints - 100;
                tvPoints.setText(String.valueOf(valuePoints));
        });

        tvGoToLikiWiki.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://liki.wiki/doc/auth/" + App.getUser().likiwiki_auth_token));
            startActivity(browserIntent);
        });

        return rootView;
    }


    @Override
    protected void onLocalizationUpdate() {
        tvChanged.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_changed));
        tvGoToLikiWiki.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_goto));
        btnGetCode.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_btn_get_code));
        tvAddNumber.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_btn_add_number_caption));
        btnAddNumber.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_likiwiki_btn_add_number));
    }
}
