package mobi.medbook.android.models;


import mobi.medbook.android.types.news.IdCCIcod;


public class NewRequestReference {

    public IdCCIcod[] pharm_advice_icod_id;
    public IdCCDrug1[] pharm_advice_drug_id;
    public UserDetails[] pharm_advice_patient;
    public String title;
    public String description;
    public int template;
}
