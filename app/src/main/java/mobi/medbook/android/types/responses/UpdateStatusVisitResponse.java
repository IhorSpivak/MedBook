package mobi.medbook.android.types.responses;


import mobi.medbook.android.api.MResponse;

public class UpdateStatusVisitResponse extends MResponse {
    public UpdateStatusVisitItem[] items;

    public class UpdateStatusVisitItem {
        public int id;
        public int user_id;
        public int visit_id;
        public Long time_from;
        public Long time_to;
        public Long accepted_at;
    }
}
