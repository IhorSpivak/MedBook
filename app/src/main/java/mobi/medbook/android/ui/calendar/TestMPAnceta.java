package mobi.medbook.android.ui.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.promo.PromoRecyclerItem;
import mobi.medbook.android.singltones.SingletoneForMPTest;
import mobi.medbook.android.singltones.SingltonForAncetaTest;
import mobi.medbook.android.singltones.SingltonForPatterns;
import mobi.medbook.android.types.materials.Question;
import mobi.medbook.android.types.visits.Questions;
import mobi.medbook.android.ui.reference.ReferenceWebViewActivity;

public class TestMPAnceta extends AppCompatActivity {


    private static final int NUM_PAGES = 5;


    ImageView ivClose;

    @BindView(R.id.fragmentViewPager)
    ViewPager mPager;

    @BindView(R.id.btn_tests_next)
    Button btn_tests_next;

    @BindView(R.id.btn_tests_prev)
    Button btn_tests_prev;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    public List<Questions> list;



    private TestAncetaPagerAdapters pagerAdapter;

    public static void startActivity(Activity activity, Integer id) {
        Intent intent = new Intent(activity, TestMPAnceta.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_mp_anceta);
        ivClose = findViewById(R.id.close);
        btn_tests_next = findViewById(R.id.btn_tests_next);
        btn_tests_prev = findViewById(R.id.btn_tests_prev);

        mPager = (ViewPager) findViewById(R.id.fragmentViewPager);
        pagerAdapter = new TestAncetaPagerAdapters(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setOffscreenPageLimit(5);

        list = SingltonForAncetaTest.getInstance().getList();

        btn_tests_prev.setVisibility(View.GONE);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected( int i) {
                if(i == 0){
                    btn_tests_prev.setVisibility(View.GONE);
                } else {
                    btn_tests_prev.setVisibility(View.VISIBLE);
                }
                if(i == list.size() -1 ){
                    btn_tests_next.setText("Завершити");
                } else {
                    btn_tests_next.setText("Далі");
                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        btn_tests_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size() - 1 == mPager.getCurrentItem()){
                        onFinishDialog();

                }
                mPager.setCurrentItem(getItem(+1), true);
            }
        });

        btn_tests_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(getItem(-1), true);
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size() >= SingletoneForMPTest.getInstance().getList().size()){
                    onExitDialog();
                } else {
                    onNotCompleteDialog();
                }

            }
        });
        setupViewPager();


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            SingltonForAncetaTest.getInstance().getList().clear();
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            titles.add(list.get(i).question);
            fragments.add(SlideFragmentTest.newInstance(list.get(i).question_id, list.get(i).question, list.get(i).awnsersArr));
        }

        pagerAdapter.setFragments(fragments, titles);
        pagerAdapter.notifyDataSetChanged();

    }

    private void onExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ви дійсно хочете вийти с заповнення анкети")
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onFinishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Завершити заповнення анкеты?")
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            finish();


                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void onNotCompleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ви не відповіли на усі питання! Ви дійсно хочете завершити заповнення анкети?")
                .setNegativeButton("Повернутися",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletoneForMPTest.getInstance().getList().clear();
                        finish();


                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }




}
