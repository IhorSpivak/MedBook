package ua.profarma.medbook.types.requests;

import ua.profarma.medbook.types.news.IdCCDrug;
import ua.profarma.medbook.types.news.IdCCIcod;
import ua.profarma.medbook.types.news.CCImage;

public class RequestMedicalCaseBody {

    public IdCCIcod[] news_clinical_case_icod_id;
    public IdCCDrug[] news_clinical_case_drug_id;
    public CCImage[] news_clinical_case_image;
    public int news_clinical_case_status_id;
    public String title;
    public String description;
    public int show_author;
    //public int public;

//    {
//        "news_clinical_case_icod_id": [{"icod_id":8},{"icod_id":12}],
//        "news_clinical_case_drug_id": [{"drug_id":2},{"drug_id":3},{"drug_id":4}],
//        "news_clinical_case_image": [{"image": "https://filestorage.medresearch.com.ua/medbook_case/2_a-ks5ULcAIURKCzx.jpg"}, {"image": "https://filestorage.medresearch.com.ua/medbook_case/2_a3-ks5ULcAIURKCzx.jpg"}],
//        "news_clinical_case_status_id": 1,
//            "title": "Интересный случай на работе",
//            "description": "Сижу я как-то на работе, спать хочется, а ко мне пациенты идут и идут...Устал я немного, встал, закрыл дверь изнутри и лег спать...Выспался....",
//            "show_author": 1,
//            "public": 1
//    }
}
