package ua.profarma.medbook.recyclerviews.tasks;

import android.content.Intent;
import android.view.View;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.TaskMaterial;
import ua.profarma.medbook.types.materials.Material;
import ua.profarma.medbook.types.materials.Video;
import ua.profarma.medbook.ui.materials.PresentationActivity;
import ua.profarma.medbook.ui.materials.TestsActivity;
import ua.profarma.medbook.ui.materials.VideoActivity;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

public class TaskRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private TaskMaterial taskMaterial;

    public TaskRecyclerItem(TaskMaterial taskMaterial) {
        this.taskMaterial = taskMaterial;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof TaskViewHolder) {
            ((TaskViewHolder) holder).init(this, taskMaterial);
        }
    }

    @Override
    public int getViewType() {
        return 1;
    }

    @Override
    public void onClick(View view) {

        LogUtils.logD("njhgmjhmhjbm", "idMaterial = " + taskMaterial.idMaterial + ", time_from = " + taskMaterial.timeCreate + ", type = " + taskMaterial.type + ", id = " + taskMaterial.id + ", title = " + taskMaterial.title);


        switch (taskMaterial.type) {
            case TEST:
                Intent intentTests = new Intent(view.getContext(), TestsActivity.class);
                intentTests.putExtra(TestsActivity.KEY_ID_TEST, taskMaterial.id);
                intentTests.putExtra(TestsActivity.KEY_ID_MATERIAL_TEST, taskMaterial.idMaterial);
                view.getContext().startActivity(intentTests);
                break;
            case VIDEO:
                for (Material materialItem : Core.get().UserContentControl().getListMaterial()) {
                    if (materialItem.id == taskMaterial.idMaterial)
                        for (Video videoItem : materialItem.videos) {
                            if (videoItem.video_id == taskMaterial.id)
                                if (videoItem.video.translations[0].file != null && !videoItem.video.translations[0].file.isEmpty()) {
                                    if (videoItem.video.translations[0].file.contains(".mp4")) {
                                        Intent intentVideo = new Intent(view.getContext(), VideoActivity.class);
                                        intentVideo.putExtra(VideoActivity.ARGS_VIDEO, videoItem.video.translations[0].file);
                                        view.getContext().startActivity(intentVideo);
                                    } else
                                        AppUtils.toastError("Сервер передав невірне посилання. Відео не може бути формату " + videoItem.video.translations[0].file.substring(videoItem.video.translations[0].file.lastIndexOf(".")), true);
                                } else
                                    AppUtils.toastError("Вибачте але відео не перегляду немає", true);
                        }
                }
                break;
            case PRESENTATION:
                Intent intentPresentation = new Intent(view.getContext(), PresentationActivity.class);
                intentPresentation.putExtra(PresentationActivity.KEY_ID_PRESENTATION, taskMaterial.id);
                intentPresentation.putExtra(PresentationActivity.KEY_ID_MATERIAL_PRESENTATION, taskMaterial.idMaterial);
                view.getContext().startActivity(intentPresentation);
                break;
        }
    }
}
