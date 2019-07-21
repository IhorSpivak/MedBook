package ua.profarma.medbook.types.news;

public class DrugSelected {

    public int id;
    public String title;
    public boolean viewing;
    public DrugSelected(int id, String title, boolean viewing) {
        this.id = id;
        this.title = title;
        this.viewing = viewing;
    }
}
