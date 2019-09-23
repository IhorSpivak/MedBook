package mobi.medbook.android.singltones;

import java.util.ArrayList;
import java.util.List;

import mobi.medbook.android.models.requests.AnswerRequest;
import mobi.medbook.android.types.materials.Answer;
import mobi.medbook.android.types.visits.Questions;

public class SingletoneForMPTest {

    private static SingletoneForMPTest uniqInstance;
    public List<AnswerRequest> list = new ArrayList<>();
    private SingletoneForMPTest() {
    }
    public static SingletoneForMPTest getInstance() {
        if (uniqInstance == null) {
            {
                if (uniqInstance == null)
                    uniqInstance = new SingletoneForMPTest();
            }
        }
        return uniqInstance;
    }


    public List<AnswerRequest> getList() {
        return list;
    }

    public void setList(List<AnswerRequest> list) {
        this.list = list;
    }

}
