package ua.profarma.medbook.types.news;

public class IcodSelected {
    public int id;
    public String code;
    public String title;
    public boolean viewing;
    public IcodSelected(int id, String code, String title, boolean viewing){
        this.id = id;
        this.code = code;
        this.title = title;
        this.viewing = viewing;
    }
}
