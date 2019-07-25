package ua.profarma.medbook.ui.reference;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

import ua.profarma.medbook.models.response.Drugs;
import ua.profarma.medbook.models.response.ReferenceItem;

@Parcel
public class WrapperReference{



    private List<ReferenceItem> list = null;

    public List<ReferenceItem> getList() {
        return list;
    }

    public void setList(List<ReferenceItem> list) {
        this.list = list;
    }
}
