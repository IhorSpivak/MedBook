package mobi.medbook.android.types.materials;

import androidx.annotation.Nullable;

public class Material {

    public int id;
    public int manufacturer_id;
    public ManufacturerItem manufacturer;
    public MaterialTranslation[] translations;
    public Test[] tests;
    public Video[] videos;
    public Presentation[] presentations;
    //public Comparativy[] comparatives;
    public Count count;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        /* obj ссылается на null */

        if (obj == null)
            return false;

        /* Удостоверимся, что ссылки имеют тот же самый тип */

        if (!(getClass() == obj.getClass()))
            return false;
        else {


            Material tmp = (Material) obj;
            if (tmp.id == this.id)
                return true;
            else return false;
        }
    }

}
