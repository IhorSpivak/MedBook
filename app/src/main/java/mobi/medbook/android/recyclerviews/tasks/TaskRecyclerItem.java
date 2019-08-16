package mobi.medbook.android.recyclerviews.tasks;

import android.content.Intent;
import android.view.View;

import java.io.IOException;

import mobi.medbook.android.Core;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionStart;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;

import mobi.medbook.android.types.TaskMaterial;

import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.materials.Video;
import mobi.medbook.android.ui.materials.PresentationActivity;
import mobi.medbook.android.ui.materials.VideoActivity;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;

import static mobi.medbook.android.ui.materials.MaterialsEnum.TEST;


public class TaskRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private TaskMaterial taskMaterial;
    Intent intentPresentation;

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

//                    Intent intentTests = new Intent(view.getContext(), TestsActivity.class);
//                    intentTests.putExtra(TestsActivity.KEY_ID_TEST, taskMaterial.id);
//                    intentTests.putExtra(TestsActivity.KEY_ID_MATERIAL_TEST, taskMaterial.idMaterial);
//                    view.getContext().startActivity(intentTests);
                    Core.get().UserContentControl().setSelectedMaterial(taskMaterial.item);
                    EventRouter.send(new EventMaterialDescriptionStart(TEST, taskMaterial.id));

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
                 intentPresentation = new Intent(view.getContext(), PresentationActivity.class);
                intentPresentation.putExtra(PresentationActivity.KEY_ID_PRESENTATION, taskMaterial.id);
                intentPresentation.putExtra(PresentationActivity.KEY_ID_MATERIAL_PRESENTATION, taskMaterial.idMaterial);
                view.getContext().startActivity(intentPresentation);
                break;
        }
    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}
