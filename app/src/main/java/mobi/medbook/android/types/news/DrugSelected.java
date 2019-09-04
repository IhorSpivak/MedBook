package mobi.medbook.android.types.news;

public class DrugSelected {

    public int id;
    public int qty = 1;
    public String title;
    public boolean viewing;


    public DrugSelected(int id, String title, boolean viewing, int qty) {
        this.id = id;
        this.title = title;
        this.viewing = viewing;
        this.qty = qty;
    }


    public DrugSelected(int id, String title, boolean viewing) {
        this.id = id;
        this.title = title;
        this.viewing = viewing;
    }

    public DrugSelected(int id, int qty) {
        this.id = id;
        this.qty = qty;
    }

    public DrugSelected(int id, int qty, String title, boolean viewing) {
        this.id = id;
        this.qty = qty;
        this.title = title;
        this.viewing = viewing;
    }
}
