package ua.profarma.medbook.recyclerviews.tasks;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.TaskMaterial;
import ua.profarma.medbook.ui.materials.MaterialsEnum;

public class TaskViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private ImageView imvIcon;

    public TaskViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_task_title);
        rootView = itemView.findViewById(R.id.item_task_root);
        imvIcon = itemView.findViewById(R.id.item_task_icon);
    }

    public void init(TaskRecyclerItem owner, TaskMaterial taskMaterial) {
        if (tvTitle != null) {
            tvTitle.setText(taskMaterial.title);
        }
        if (imvIcon != null) {
            if (taskMaterial.type == MaterialsEnum.TEST)
                imvIcon.setImageResource(R.drawable.ic_task_test);
            else if (taskMaterial.type == MaterialsEnum.PRESENTATION)
                imvIcon.setImageResource(R.drawable.ic_task_presentation);
            else if (taskMaterial.type == MaterialsEnum.VIDEO)
                imvIcon.setImageResource(R.drawable.ic_task_video);
        }
        rootView.setOnClickListener(owner);
    }
}
