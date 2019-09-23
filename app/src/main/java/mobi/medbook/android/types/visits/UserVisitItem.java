package mobi.medbook.android.types.visits;

import mobi.medbook.android.models.requests.AnswerRequest;
import mobi.medbook.android.types.materials.Question;

public class UserVisitItem {
    public int visitCount;
    public int doctorId;
    public int mpId;
    public int patientFlow;
    public int lastVisitId;
    public Product[] productsArr;
    public Promo[] promoArr;
    public ProductPlan[] planProductArr;
    public Questions[] questionsArr;
    public Object[] questionsArrResult;
}
