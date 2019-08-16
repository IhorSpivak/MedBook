package mobi.medbook.android.types.responses;

import mobi.medbook.android.api.MResponse;
import mobi.medbook.android.types.visits.UserVisit;


public class StartVisitResponse extends MResponse {
//    {"success":true,"errors":null,"items":
//        [{
    public StartVisitItem[] items;
//            "expires_at":1558956154,
//            "visit":{"id":77,
//                        "user_id":15667,
//                        "visit_id":39,
//                        "event_id":null,
//                        "date":"2019-05-27",
//                        "time_from":1558952555,
//                        "time_to":1558953155,
//                        "accepted_at":1558939978,
//                        "canceled_at":null,
//                        "started_at":null,
//                        "ended_at":null}}]}

    public class StartVisitItem{
        public int is_medpred;
        public String token;
        public long expires_at;
        public UserVisit visit;
    }

}
