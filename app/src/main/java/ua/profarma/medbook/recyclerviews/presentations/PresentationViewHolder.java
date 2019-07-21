package ua.profarma.medbook.recyclerviews.presentations;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.materials.Presentation;

public class PresentationViewHolder extends BaseViewHolder {

    private View rootView;


    private TextView tvtitle;
    private TextView tvBalls;
    private TextView tvDate;
    private TextView tvId;

    private ImageView imvLogo;
    private ImageView dot;


    public PresentationViewHolder(View itemView) {
        super(itemView);

        dot     = itemView.findViewById(R.id.item_presentations_dot);
        tvtitle = itemView.findViewById(R.id.item_presentations_title);
        tvBalls = itemView.findViewById(R.id.item_presentations_balls);
        tvDate  = itemView.findViewById(R.id.item_presentations_date);
        tvId    = itemView.findViewById(R.id.item_presentations_id);
        imvLogo = itemView.findViewById(R.id.item_presentation_logo);


        rootView = itemView.findViewById(R.id.item_presentation_list_root);
    }

    public void init(PresentationRecyclerItem owner, Presentation presentation) {

        if(dot != null){
            if(presentation.result_time == null || presentation.result_time.isEmpty())
                dot.setVisibility(View.VISIBLE);
            else
                dot.setVisibility(View.INVISIBLE);
        }

        if (tvtitle != null) {
            tvtitle.setText(presentation.presentation.translations[0].title);
        }
        if (tvBalls != null) {
            if(presentation.presentation_points > 0) {
                tvBalls.setVisibility(View.VISIBLE);
                tvBalls.setText(String.valueOf(presentation.presentation_points));
            }
            else
                tvBalls.setVisibility(View.GONE);
        }
        if (tvDate != null) {
            Date date = new Date(presentation.time_from * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            DateFormat format = new SimpleDateFormat("dd MMM yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);

            tvDate.setText(formatted);
        }
        if (tvId != null) {
            tvId.setText(String.valueOf(presentation.presentation_id));
        }

        if (presentation.presentation.translations[0].logo != null && !presentation.presentation.translations[0].logo.isEmpty())
            Picasso.get().load(presentation.presentation.translations[0].logo).into(imvLogo);

        rootView.setOnClickListener(owner);
    }
}
