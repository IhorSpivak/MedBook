package ua.profarma.medbook.recyclerviews.videos;

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
import ua.profarma.medbook.recyclerviews.presentations.PresentationRecyclerItem;
import ua.profarma.medbook.types.materials.Presentation;
import ua.profarma.medbook.types.materials.Video;

public class VideoViewHolder extends BaseViewHolder {

    private View rootView;


    private TextView tvtitle;
    private TextView tvBalls;
    private TextView tvDate;
    private TextView tvId;

    private ImageView imvLogo;

    private ImageView dot;


    public VideoViewHolder(View itemView) {
        super(itemView);

        dot = itemView.findViewById(R.id.item_videos_dot);
        tvtitle = itemView.findViewById(R.id.item_videos_title);
        tvBalls = itemView.findViewById(R.id.item_videos_balls);
        tvDate = itemView.findViewById(R.id.item_videos_date);
        tvId    = itemView.findViewById(R.id.item_videos_id);
        imvLogo = itemView.findViewById(R.id.item_videos_logo);


        rootView = itemView.findViewById(R.id.item_videos_list_root);
    }

    public void init(VideoRecyclerItem owner, Video video) {
        if(dot != null){
            if(video.result_time == null || video.result_time.isEmpty())
                dot.setVisibility(View.VISIBLE);
            else
                dot.setVisibility(View.INVISIBLE);
        }
        if (tvtitle != null) {
            tvtitle.setText(video.video.translations[0].title);
        }
        if (tvBalls != null) {
            if(video.video_points > 0) {
                tvBalls.setVisibility(View.VISIBLE);
                tvBalls.setText(String.valueOf(video.video_points));
            }
            else
                tvBalls.setVisibility(View.GONE);
        }
        if (tvDate != null) {
            Date date = new Date(video.time_from * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            DateFormat format = new SimpleDateFormat("dd MMM yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);

            tvDate.setText(formatted);
        }
        if (tvId != null) {
            tvId.setText(String.valueOf(video.video_id));
        }

        if (video.video.translations[0].logo != null && !video.video.translations[0].logo.isEmpty())
            Picasso.get().load(video.video.translations[0].logo).into(imvLogo);

        rootView.setOnClickListener(owner);
    }
}
