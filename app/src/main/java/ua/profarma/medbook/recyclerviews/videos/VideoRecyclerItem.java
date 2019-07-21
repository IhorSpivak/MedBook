package ua.profarma.medbook.recyclerviews.videos;

import android.view.View;

import ua.profarma.medbook.events.materials.EventMaterialDescriptionStart;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.materials.Video;
import ua.profarma.medbook.ui.materials.MaterialsEnum;
import ua.profarma.medbook.utils.LogUtils;

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
