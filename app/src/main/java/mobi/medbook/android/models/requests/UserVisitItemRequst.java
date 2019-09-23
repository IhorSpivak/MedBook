package mobi.medbook.android.models.requests;

import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.types.visits.ProductPlan;
import mobi.medbook.android.types.visits.Promo;
import mobi.medbook.android.types.visits.Questions;

class UserVisitItemRequst {

    public int visitCount;
    public int doctorId;
    public int mpId;
    public int patientFlow;
    public int lastVisitId;
    public Product[] productsArr;
    public Promo[] promoArr;
    public ProductPlan[] planProductArr;
    public AnswerRequest[] questionsArr;

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getMpId() {
        return mpId;
    }

    public void setMpId(int mpId) {
        this.mpId = mpId;
    }

    public int getPatientFlow() {
        return patientFlow;
    }

    public void setPatientFlow(int patientFlow) {
        this.patientFlow = patientFlow;
    }

    public int getLastVisitId() {
        return lastVisitId;
    }

    public void setLastVisitId(int lastVisitId) {
        this.lastVisitId = lastVisitId;
    }

    public Product[] getProductsArr() {
        return productsArr;
    }

    public void setProductsArr(Product[] productsArr) {
        this.productsArr = productsArr;
    }

    public Promo[] getPromoArr() {
        return promoArr;
    }

    public void setPromoArr(Promo[] promoArr) {
        this.promoArr = promoArr;
    }

    public ProductPlan[] getPlanProductArr() {
        return planProductArr;
    }

    public void setPlanProductArr(ProductPlan[] planProductArr) {
        this.planProductArr = planProductArr;
    }

    public AnswerRequest[] getQuestionsArr() {
        return questionsArr;
    }

    public void setQuestionsArr(AnswerRequest[] questionsArr) {
        this.questionsArr = questionsArr;
    }
}
