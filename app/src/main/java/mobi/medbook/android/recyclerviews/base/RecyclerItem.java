package mobi.medbook.android.recyclerviews.base;

public abstract class RecyclerItem {
    public abstract void fill(BaseViewHolder holder);

    public abstract int getViewType();

    public String Text() {
        return null;
    }

    public String TextToCompare() {
        return null;
    }
}
