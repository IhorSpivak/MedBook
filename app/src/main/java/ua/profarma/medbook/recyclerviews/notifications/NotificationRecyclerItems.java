package ua.profarma.medbook.recyclerviews.notifications;

import java.util.ListIterator;

import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;

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
