package ua.profarma.medbook.models;

public class DrugSelectedReference {
    public int id;
    public int qty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public DrugSelectedReference(int id, int qty) {
        this.id = id;
        this.qty = qty;
    }
}
