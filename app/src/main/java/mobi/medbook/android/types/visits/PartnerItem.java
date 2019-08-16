package mobi.medbook.android.types.visits;

public class PartnerItem {
    public Long partner_accepted;
    public  Partner partner;


    public class Partner{
        public int id;
        public String first_name;
        public String middle_name;
        public String last_name;
        public String avatar;
    }
}
