package ua.profarma.medbook.events.visits;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.visits.Product;

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
