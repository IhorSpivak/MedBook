package mobi.medbook.android.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.medbook.android.R;
import mobi.medbook.android.types.visits.AnswerMP;

public class SlideFragmentTest extends Fragment {

    private static final String ID_QUESTION = "extra_id_fragment";
    private static final String QUERY = "extra_query";
    private static final String LIST = "extra_list";
    ArrayList<AnswerMP> list;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.tv)
    TextView tv;



    private AnswerListAdapter answerListAdapter;
    private GridLayoutManager mGridLayoutManager;
    private String query;
    private String idQuestion;

    public static SlideFragmentTest newInstance(String idQuestion, String text, ArrayList<AnswerMP> list) {
        Bundle args = new Bundle();
        args.putString(ID_QUESTION, idQuestion);
        args.putString(QUERY, text);
        args.putSerializable(LIST, list);
        SlideFragmentTest fragment = new SlideFragmentTest();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_slide_test, container, false);
        ButterKnife.bind(this, v);

        rv.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        rv.setLayoutManager(mGridLayoutManager);

        answerListAdapter = new AnswerListAdapter(idQuestion);
        answerListAdapter.setItemClickListener(item -> {});

       list = (ArrayList<AnswerMP>)getArguments().getSerializable(LIST);
        idQuestion = getArguments().getString(ID_QUESTION);

        answerListAdapter.addAll(list);

        query = getArguments().getString(QUERY);
        tv.setText(query);

        rv.setAdapter(answerListAdapter);




        return v;
    }
}
