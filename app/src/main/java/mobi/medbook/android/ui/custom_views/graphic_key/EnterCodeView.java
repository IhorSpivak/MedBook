package mobi.medbook.android.ui.custom_views.graphic_key;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EnterCodeView extends CodeView {

    private static final long CLEAR_TASK_DELAY = 1000;

    private final ArrayList<int[]> newCode = new ArrayList<>();
    private volatile float[] lastTrackingPoint;
    private OnEnterCodeEndListener enterCodeEndListener;
    private OnClearListener onClearListener;

    private Handler clearCodeHandler;
    private Runnable clearRunnable;

    public EnterCodeView(Context context) {
        super(context);
    }


    public EnterCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EnterCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EnterCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                setError(false);
                clear();

                processNextPoint(event);

                return true;
            case MotionEvent.ACTION_MOVE:

                processNextPoint(event);

                return false;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastTrackingPoint = null;
                if (enterCodeEndListener != null) {
                    enterCodeEndListener.codeEntered(createEnteredCode());
                }
                startClearTaskDelayed();
                return true;
        }

        return false;
    }


    private synchronized void processNextPoint(MotionEvent motionEvent) {

        float[] xy = new float[2];

        xy[0] = motionEvent.getX();
        xy[1] = motionEvent.getY();

        lastTrackingPoint = xy;

        int[] crossCode = new int[2];
        int[] crossingPoint = findCrossingPoint(xy, crossCode);

        if (crossingPoint == null) {
            return;
        }

        if (checkIfAlreadyContainsCode(crossCode)) {
            return;
        }

        if (newCode.size() > 0 && !checkIfValidPointPosition(newCode.get(newCode.size() - 1), crossCode)) {
            return;
        }

        newCode.add(crossCode);
        setCode(createEnteredCode());

    }

    private boolean checkIfValidPointPosition(int[] prevPoint, int[] crossCode) {
        int x = crossCode[0];
        int y = crossCode[1];

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if (x == prevPoint[0] + i && y == prevPoint[1] + j) {
                    return true;
                }
            }
        }

        return false;
    }

    private int[] findCrossingPoint(float[] xy, int[] outCode) {
        int[][] c = getDotsCentersBounds();

        if (c == null || c.length == 0) {
            return null;
        }

        RectF rectF;
        int[] coord;

        for (int i = 0; i < c.length; i++) {
            coord = c[i];
            rectF = new RectF(coord[0], coord[1], coord[2], coord[3]);
            if (rectF.contains(xy[0], xy[1])) {
                outCode[0] = i % getColumnCount();
                outCode[1] = i / getColumnCount();
                return getDotsCenters()[i];
            }
        }

        return null;
    }

    private boolean checkIfAlreadyContainsCode(int[] code) {
        for (int[] i : newCode) {
            if (i[0] == code[0] && i[1] == code[1]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfAlreadyCrossPoint(int[] point) {
        int[][] s = getSelectedDotsCenters();

        if (s == null || s.length == 0) {
            return false;
        }

        for (int i = 0; i < s.length; i++) {
            if (s[i][0] == point[0] && s[i][1] == point[1]) {
                return true;
            }
        }

        return false;
    }

    private void startClearTaskDelayed() {

        if (clearCodeHandler != null) {
            clearCodeHandler.removeCallbacks(clearRunnable);
        }

        clearCodeHandler = new Handler();
        clearRunnable = new ClearRunnable();
        clearCodeHandler.postDelayed(clearRunnable, CLEAR_TASK_DELAY);
    }

    private int[][] createEnteredCode() {
        int[][] code = new int[newCode.size()][2];

        int count = 0;

        for (int[] i : newCode) {
            code[count] = i;
            count++;
        }

        return code;
    }

    private void clear() {
        setCode(new int[0][0]);
        lastTrackingPoint = null;
        if (newCode != null) {
            newCode.clear();
        }
        if (clearCodeHandler != null) {
            clearCodeHandler.removeCallbacks(clearRunnable);
        }

        if (onClearListener != null) {
            onClearListener.cleared();
        }
    }

    @Override
    protected void init(AttributeSet attrs) {
        super.init(attrs);
        clear();
    }


    public void setEnterCodeEndListener(OnEnterCodeEndListener enterCodeEndListener) {
        this.enterCodeEndListener = enterCodeEndListener;
    }

    public void setOnClearListener(OnClearListener listener) {
        this.onClearListener = listener;
    }

    public interface OnEnterCodeEndListener {

        void codeEntered(int[][] code);
    }

    public interface OnClearListener {
        void cleared();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (clearCodeHandler != null) {
            clearCodeHandler.removeCallbacks(clearRunnable);
        }
    }

    private class ClearRunnable implements Runnable {

        @Override
        public void run() {
            clear();
        }
    }

    @Override
    protected void onDrawLines(Canvas canvas, Paint paint) {

        int[][] prevPoints = getSelectedDotsCenters();

        float[] trackingPoint = null;

        if (prevPoints == null || prevPoints.length == 0) {
            return;
        }

        if (lastTrackingPoint != null) {
            trackingPoint = new float[]{
                    lastTrackingPoint[0], lastTrackingPoint[1]
            };
        }

        if (trackingPoint == null) {
            return;
        }

        try {
            drawLine(canvas, prevPoints[prevPoints.length - 1][0], prevPoints[prevPoints.length - 1][1], (int) trackingPoint[0], (int) trackingPoint[1], paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

