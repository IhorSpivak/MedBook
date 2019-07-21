package ua.profarma.medbook.recyclerviews.visits;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.visits.UserVisit;
import ua.profarma.medbook.ui.calendar.VisitUtils;
import ua.profarma.medbook.utils.TextUtils;
import ua.profarma.medbook.utils.TimeUtils;

public class VisitViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvName;
    private TextView tvStatus;
    private ImageView dotStatus;

    public VisitViewHolder(View itemView) {
        super(itemView);
        dotStatus = itemView.findViewById(R.id.item_visit_dot);
        tvTitle = itemView.findViewById(R.id.item_visit_title);
        tvTime = itemView.findViewById(R.id.item_visit_time);
        tvName = itemView.findViewById(R.id.item_visit_name);
        tvStatus = itemView.findViewById(R.id.item_visit_status);
        rootView = itemView.findViewById(R.id.item_visit_root);
    }

    public void init(VisitRecyclerItem owner, UserVisit userVisit, long today) {


        if (tvTitle != null) {
            tvTitle.setText(TextUtils.getString(userVisit.visit.title));
        }
        if (tvTime != null) {

            tvTime.setText(getDate(userVisit.time_from) + " - " + getDate(userVisit.time_to));
        }
        if (tvName != null) {
            tvName.setText(TextUtils.getString(userVisit.partner.partner.last_name) + " " +TextUtils.getString(userVisit.partner.partner.first_name)
                    + " " + TextUtils.getString(userVisit.partner.partner.middle_name));
        }

        if (tvStatus != null && dotStatus != null) {
            switch (VisitUtils.getStatus(userVisit, today)) {
                case PROCESSING:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_new));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_processing));
                    dotStatus.setImageResource(R.drawable.circle_visit_new);
                    break;
                case ACCEPTED:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_accepted));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_accepted));
                    dotStatus.setImageResource(R.drawable.circle_visit_accepted);
                    break;
                case CANCELED:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_canceled));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_canceled));
                    dotStatus.setImageResource(R.drawable.circle_visit_canceled);
                    break;
                case ENDED:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_ended));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_ended));
                    dotStatus.setImageResource(R.drawable.circle_visit_ended);
                    break;
                case FAILED:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_failed));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_failed));
                    dotStatus.setImageResource(R.drawable.circle_visit_failed);
                    break;
                case NEW:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_new));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_new));
                    dotStatus.setImageResource(R.drawable.circle_visit_new);
                    break;
                case STARTED:
                    tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_started));
                    tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_started));
                    dotStatus.setImageResource(R.drawable.circle_visit_started);
                    break;
                case EMPTY:
                    //tvStatus.setTextColor(rootView.getContext().getResources().getColor(R.color.color_circle_visit_started));
                    tvStatus.setText("EMPTY");
                    //dotStatus.setImageResource();
                    break;
            }
        }

        rootView.setOnClickListener(owner);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        int startYear = cal.get(Calendar.YEAR);
        int starthMonth = cal.get(Calendar.MONTH) + 1;
        int startDay = cal.get(Calendar.DAY_OF_MONTH);
        int startHours = cal.get(Calendar.HOUR_OF_DAY);
        int startMinutes = cal.get(Calendar.MINUTE);
        return /*startDay + "." + TimeUtils.getFormatMinutes(starthMonth) + "." + startYear + " " +*/ TimeUtils.getFormatMinutes(startHours) + ":" + TimeUtils.getFormatMinutes(startMinutes);
    }
}
