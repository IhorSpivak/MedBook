package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;

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
