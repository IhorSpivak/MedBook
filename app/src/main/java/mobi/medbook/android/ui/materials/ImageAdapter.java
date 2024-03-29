package mobi.medbook.android.ui.materials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import mobi.medbook.android.R;
import mobi.medbook.android.types.materials.ImageP;
import mobi.medbook.android.utils.DoubleClick;
import mobi.medbook.android.utils.DoubleClickListener;
import mobi.medbook.android.utils.LogUtils;


public class ImageAdapter extends PagerAdapter {
    Context context;
    ImageP[] images;

    LayoutInflater mLayoutInflater;

    public ImageAdapter(Context context, ImageP[] images) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        Picasso.get().load(images[position].file_path).into(imageView);

        container.addView(itemView);

        itemView.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {

                 LogUtils.logD("jyt78tjgh", "This page was clicked single: " + position);
            }

            @Override
            public void onDoubleClick(View view) {

                 LogUtils.logD("jyt78tjgh", "This page was clicked double: " + position);
            }
        }));

        //LogUtils.logD("jyt78tjgh", "This page was clicked: " + position);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
