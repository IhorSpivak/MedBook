package mobi.medbook.android.recyclerviews.videos;

import android.view.View;

import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionStart;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.materials.Video;
import mobi.medbook.android.ui.materials.MaterialsEnum;


public class VideoRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Video video;

    public VideoRecyclerItem(Video video) {
        this.video = video;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).init(this, video);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        EventRouter.send(new EventMaterialDescriptionStart(MaterialsEnum.VIDEO, video.id));
//        if (video.video.translations[0].file != null && !video.video.translations[0].file.isEmpty()) {
//            if (video.video.translations[0].file.contains(".mp4")) {
//                Intent intent = new Intent(view.getContext(), VideoActivity.class);
//                intent.putExtra(VideoActivity.ARGS_VIDEO, video.video.translations[0].file);
//                Log.d("VideoActivity", video.video.translations[0].file);
//                view.getContext().startActivity(intent);
//            } else
//                AppUtils.toastError("Сервер передав невірне посилання. Відео не може бути формату " + video.video.translations[0].file.substring(video.video.translations[0].file.lastIndexOf(".")), true);
//        } else AppUtils.toastError("Вибачте але відео не перегляду немає", true);
    }
}
