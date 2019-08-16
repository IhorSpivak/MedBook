package mobi.medbook.android.events.visits;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.visits.Product;


public class EventUserVisitQuestionnaireUpdate extends Event {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public EventUserVisitQuestionnaireUpdate(Product product) {
        super(EVENT_USER_VISIT_QUESTIONNAIRE_MP_UPDATE);
        this.product = product;
    }
}
