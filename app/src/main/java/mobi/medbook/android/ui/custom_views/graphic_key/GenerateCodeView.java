package mobi.medbook.android.ui.custom_views.graphic_key;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import mobi.medbook.android.utils.LogUtils;


public class GenerateCodeView extends CodeView {
    private long lastSeed = 0L;
    private int[][] temp = new int[4][2];

    public GenerateCodeView(Context context) {
        super(context);
    }

    public GenerateCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GenerateCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initArray(String token) {
        LogUtils.logD("hfr6yughbjjhj", "token = " + token);
        temp = new int[4][2];

        int st1 = Integer.valueOf(token.substring(0, 1));
        int st2 = Integer.valueOf(token.substring(1, 2));
        int st3 = Integer.valueOf(token.substring(2, 3));
        int st4 = Integer.valueOf(token.substring(3, 4));

        if (st1 == 0) {
            temp[0][0] = 0;
            temp[0][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,0");
        }
        if (st1 == 1) {
            temp[0][0] = 1;
            temp[0][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,1");
        }
        if (st1 == 2) {
            temp[0][0] = 2;
            temp[0][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,2");
        }
        if (st1 == 3) {
            temp[0][0] = 0;
            temp[0][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,0");
        }
        if (st1 == 4) {
            temp[0][0] = 1;
            temp[0][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,1");
        }
        if (st1 == 5) {
            temp[0][0] = 2;
            temp[0][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,2");
        }
        if (st1 == 6) {
            temp[0][0] = 0;
            temp[0][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,0");
        }
        if (st1 == 7) {
            temp[0][0] = 1;
            temp[0][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,1");
        }
        if (st1 == 8) {
            temp[0][0] = 2;
            temp[0][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,2");
        }

        if (st2 == 0) {
            temp[1][0] = 0;
            temp[1][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,0");
        }
        if (st2 == 1) {
            temp[1][0] = 1;
            temp[1][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,1");
        }
        if (st2 == 2) {
            temp[1][0] = 2;
            temp[1][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,2");
        }
        if (st2 == 3) {
            temp[1][0] = 0;
            temp[1][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,0");
        }
        if (st2 == 4) {
            temp[1][0] = 1;
            temp[1][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,1");
        }
        if (st2 == 5) {
            temp[1][0] = 2;
            temp[1][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,2");
        }
        if (st2 == 6) {
            temp[1][0] = 0;
            temp[1][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,0");
        }
        if (st2 == 7) {
            temp[1][0] = 1;
            temp[1][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,1");
        }
        if (st2 == 8) {
            temp[1][0] = 2;
            temp[1][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,2");
        }





        if (st3 == 0) {
            temp[2][0] = 0;
            temp[2][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,0");
        }
        if (st3 == 1) {
            temp[2][0] = 1;
            temp[2][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,1");
        }
        if (st3 == 2) {
            temp[2][0] = 2;
            temp[2][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,2");
        }
        if (st3 == 3) {
            temp[2][0] = 0;
            temp[2][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,0");
        }
        if (st3 == 4) {
            temp[2][0] = 1;
            temp[2][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,1");
        }
        if (st3 == 5) {
            temp[2][0] = 2;
            temp[2][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,2");
        }
        if (st3 == 6) {
            temp[2][0] = 0;
            temp[2][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,0");
        }
        if (st3 == 7) {
            temp[2][0] = 1;
            temp[2][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,1");
        }
        if (st3 == 8) {
            temp[2][0] = 2;
            temp[2][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,2");
        }



        if (st4 == 0) {
            temp[3][0] = 0;
            temp[3][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,0");
        }
        if (st4 == 1) {
            temp[3][0] = 1;
            temp[3][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,1");
        }
        if (st4 == 2) {
            temp[3][0] = 2;
            temp[3][1] = 0;
            LogUtils.logD("hfr6yughbjjhj", "0,2");
        }
        if (st4 == 3) {
            temp[3][0] = 0;
            temp[3][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,0");
        }
        if (st4 == 4) {
            temp[3][0] = 1;
            temp[3][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,1");
        }
        if (st4 == 5) {
            temp[3][0] = 2;
            temp[3][1] = 1;
            LogUtils.logD("hfr6yughbjjhj", "1,2");
        }
        if (st4 == 6) {
            temp[3][0] = 0;
            temp[3][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,0");
        }
        if (st4 == 7) {
            temp[3][0] = 1;
            temp[3][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,1");
        }
        if (st4 == 8) {
            temp[3][0] = 2;
            temp[3][1] = 2;
            LogUtils.logD("hfr6yughbjjhj", "2,2");
        }

        lastSeed = 0;
    }

    @Override
    protected void beforeFrameUpdate() {
        //LogUtils.logD("hfr6yughbjjhj", "lastSeed = " + lastSeed);
        //LogUtils.logD("hfr6yughbjjhj", "SuppSeed = " + visitCodeGenerator.getSupposedSeed());
        if(lastSeed == 0 || lastSeed <= System.currentTimeMillis()){
            LogUtils.logD("hfr6yughbjjhj", "beforeFrameUpdate() -> setCode");
            setCode(temp);
            lastSeed = System.currentTimeMillis() + 10000;
        }
    }
}
