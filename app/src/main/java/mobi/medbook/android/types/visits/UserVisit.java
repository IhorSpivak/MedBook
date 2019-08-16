package mobi.medbook.android.types.visits;

public class UserVisit {
    public int id;
    public int user_id;
    public int visit_id;
    public String date;
    public Long time_from;
    public Long time_to;
    public Long accepted_at;
    public Long canceled_at;
    public Long started_at;
    public Long ended_at;
    public Visit visit;
    public PartnerItem partner;
}
