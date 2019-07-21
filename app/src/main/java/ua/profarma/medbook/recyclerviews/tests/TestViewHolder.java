package ua.profarma.medbook.recyclerviews.tests;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.materials.Test;

public class TestViewHolder extends BaseViewHolder {

    private View rootView;

    private TextView tvtitle;
    private TextView tvBalls;
    private TextView tvDate;
    private TextView tvId;
    private ImageView dot;


    public TestViewHolder(View itemView) {
        super(itemView);

        dot     = itemView.findViewById(R.id.item_tests_dot);
        tvtitle = itemView.findViewById(R.id.item_tests_title);
        tvBalls = itemView.findViewById(R.id.item_tests_balls);
        tvDate  = itemView.findViewById(R.id.item_tests_date);
        tvId    = itemView.findViewById(R.id.item_tests_id);


        rootView = itemView.findViewById(R.id.item_test_single_choice_root);
    }

    public void init(TestRecyclerItem owner, Test test) {
        if(dot != null){
            if(test.test.results.length > 0)
                dot.setVisibility(View.INVISIBLE);
            else
                dot.setVisibility(View.VISIBLE);
        }
        if (tvtitle != null) {
            tvtitle.setText(test.test.translations[0].title);
        }
        if (tvBalls != null) {
            if(test.test_points > 0) {
                tvBalls.setVisibility(View.VISIBLE);
                tvBalls.setText(String.valueOf(test.test_points));
            }
            else
                tvBalls.setVisibility(View.GONE);
        }
        if (tvDate != null) {
            Date date = new Date(test.time_from * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            DateFormat format = new SimpleDateFormat("dd MMM yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);

            tvDate.setText(formatted);
        }
        if (tvId != null) {
            tvId.setText(String.valueOf(test.test_id));
        }

        rootView.setOnClickListener(owner);
    }
}
