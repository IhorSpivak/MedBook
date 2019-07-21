package ua.profarma.medbook.types.news;

import ua.profarma.medbook.types.news.clinic_case_pojo.ClinicalCaseDrug;
import ua.profarma.medbook.types.news.clinic_case_pojo.ClinicalCaseIcod;
import ua.profarma.medbook.types.news.clinic_case_pojo.ClinicalCaseImage;

public class MedicalCaseItem {
    public Integer id;
    public String title;
    public String description;
    public Integer show_author;
    public Integer news_clinical_case_status_id;
    public Integer author_id;
    public Long created_at;
    public Long updated_at;
    public Long deleted_at;

    public ClinicalCaseDrug[] newsClinicalCaseDrugs;
    public ClinicalCaseIcod[] newsClinicalCaseIcods;
    public ClinicalCaseImage[] newsClinicalCaseImages;
    public ClinicalCaseStatus newsClinicalCaseStatus;

    public NewsArticleCC[] newsArticles;

}
