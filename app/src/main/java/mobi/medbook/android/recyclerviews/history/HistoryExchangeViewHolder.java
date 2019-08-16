package mobi.medbook.android.recyclerviews.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.points.HistoryExchange;

public class HistoryExchangeViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvSum;
    private ImageView imvIcon;

    public HistoryExchangeViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_history_exchange_title);
        tvDate = itemView.findViewById(R.id.item_history_exchange_date);
        tvSum = itemView.findViewById(R.id.item_history_exchange_sum);
        rootView = itemView.findViewById(R.id.item_history_exchange_root);
        imvIcon = itemView.findViewById(R.id.item_history_exchange_icon);
    }

    public void init(HistoryExchange historyExchange) {
        if (tvTitle != null) {
            int selectLang = -1;
            for (int i = 0; i < historyExchange.translations.length; i++) {
                if (historyExchange.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                    selectLang = i;
                }
            }
            if (selectLang == -1) {
                for (int i = 0; i < historyExchange.translations.length; i++) {
                    if (historyExchange.translations[i].language.substring(0, 2).equals("uk")) {
                        selectLang = i;
                    }
                }
            }
            if (selectLang != -1)
                tvTitle.setText(historyExchange.translations[selectLang].comment);
        }
        if (imvIcon != null) {
            if (historyExchange.points > 0) {
                imvIcon.setImageResource(R.drawable.ic_points_receive);
            } else {
                imvIcon.setImageResource(R.drawable.ic_points_send);
            }
        }
        if (tvDate != null) {
            Date date = new Date(historyExchange.created_at * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            DateFormat format = new SimpleDateFormat("HH:mm dd MMM yyyy");
            String formatted = format.format(date);
            tvDate.setText(formatted);
        }
        if (tvSum != null) {
            if (historyExchange.points > 0) {
                tvSum.setText(String.valueOf(historyExchange.points.intValue()));
                tvSum.setTextColor(tvSum.getContext().getResources().getColor(R.color.history_points_blue));
            } else if (historyExchange.points < 0) {
                tvSum.setText(String.valueOf(historyExchange.points.intValue()));
                tvSum.setTextColor(tvSum.getContext().getResources().getColor(R.color.history_points_red));
            }
        }
    }
}
