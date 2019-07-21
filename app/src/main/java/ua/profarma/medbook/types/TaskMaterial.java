package ua.profarma.medbook.types;

import ua.profarma.medbook.ui.materials.MaterialsEnum;

public class TaskMaterial implements Comparable<TaskMaterial> {
    public int idMaterial;
    public int id;
    public String title;
    public MaterialsEnum type;
    public Long timeCreate;

    public TaskMaterial(int idMaterial, int id, String title, MaterialsEnum type, long timeCreate) {
        this.idMaterial = idMaterial;
        this.id = id;
        this.title = title;
        this.type = type;
        this.timeCreate = timeCreate;
    }

    @Override
    public int compareTo(TaskMaterial o) {
        return o.timeCreate.compareTo(this.timeCreate);
    }
}
