package mobi.medbook.android.recyclerviews.notifications;

import java.util.ListIterator;

import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;


public class NotificationRecyclerItems extends RecyclerItems {

    public int delete(int id)
    {
        ListIterator<RecyclerItem> it = listIterator();
        while(it.hasNext())
        {
            RecyclerItem item = it.next();
            if(((NotificationRecyclerItem) item).getNotificationItem().id.equals(id))
            {
                //it.remove();
                return it.previousIndex();
            }
        }
        return -1;
    }
}
