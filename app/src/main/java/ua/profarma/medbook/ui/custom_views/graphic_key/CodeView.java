package ua.profarma.medbook.ui.custom_views.graphic_key;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.CODE_COLUMNS_COUNT;
import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.CODE_LENGTH;
import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.CODE_ROWS_COUNT;
import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.VALID_INTERVAL_SECONDS;


public class CodeView extends SurfaceView implements SurfaceHolder.Callback {
    protected VisitCodeGenerator visitCodeGenerator;
    private VisitCodeGenerator.Settings settings;

    private Handler errorHandler;
    private Runnable errorRunnable;

    private Paint paint;
    private CodeView.DrawThread drawThread;
    private Context context;

    private static final float DEFAULT_DOT_SIZE = 0.25f;
    private static final float DEFAULT_OUTER_RADIUS = 1.2f;
    private static final int DEFAULT_LINE_WIDTH = 3; //dp
    private static final int DEFAULT_TEXT = 1; //dp
    private static final int DEFAULT_CIRCLE_LINE_WIDTH = 2; //dp
    private static final int DEFAULT_DOT_COLOR = Color.parseColor("#8E8E93");
    private static final int DEFAULT_COLOR_2 = Color.parseColor("#FF37A3E0");
    private static final int DEFAULT_SELECTED_DOT_COLOR = Color.parseColor("#FF37A3E0");
    private static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final int DEFAULT_COLOR_1 = Color.WHITE;

    private static final int DEFAULT_ERROR_COLOR = Color.parseColor("#F41041");

    //arrows
    private static final int DEFAULT_LINE_COLOR = Color.GREEN;
    private static final int DEFAULT_ARROW_COLOR = Color.GREEN;
    private static final float DEFAULT_ARROW_SIZE = 30f;
    private static final float DEFAULT_ARROW_ANDGLE = 10f;

    private static final float DEFAULT_DESH_SIZE = 30f;
    private static final long DEFAULT_FRAME_RATE = 30;
    private static final long DEFAULT_GRADIENT_MOVE_SPEED = 2;
    private static final long DEFAULT_ERROR_DURATION = 1000L;

    private volatile float dotSizeP = DEFAULT_DOT_SIZE;
    private int dotColor = DEFAULT_DOT_COLOR;
    private int selectedDotColor = DEFAULT_SELECTED_DOT_COLOR;
    private int errorColor = DEFAULT_ERROR_COLOR;
    private int bgColor = DEFAULT_BG_COLOR;
    private int lineColor1 = DEFAULT_COLOR_1;
    private int lineColor2 = DEFAULT_COLOR_2;
    private int lineColor = DEFAULT_LINE_COLOR;
    private int arrowColor = DEFAULT_ARROW_COLOR;
    private float arrowSizeP = DEFAULT_ARROW_SIZE;
    private float arrowAngle = DEFAULT_ARROW_ANDGLE;
    private static float deshSize = DEFAULT_DESH_SIZE;
    private int lineWidth;
    private int circleLineWidth;


    private volatile int[][] code;
    private volatile int[][] nextCode;
    private int[][] dotsCenters;
    private int[][] dotsCentersBounds;
    private int[][] selectedDotsCenters;
    private int cellWidth;
    private int cellHeight;
    private int dotRadius;

    private float gradientOffset;
    private float gradientMoveSpeed = DEFAULT_GRADIENT_MOVE_SPEED;

    private long errorDuration = DEFAULT_ERROR_DURATION;
    private boolean isError;


    public int[][] getDotsCenters() {
        return dotsCenters;
    }

    public int[][] getDotsCentersBounds() {
        return dotsCentersBounds;
    }

    public int[][] getSelectedDotsCenters() {
        return selectedDotsCenters;
    }

    public CodeView(Context context) {
        super(context);
        init(null);
    }

    public CodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected int getRowCount() {
        if (visitCodeGenerator == null) {
            return 0;
        }
        return visitCodeGenerator.getSettings().getRowCount();
    }

    protected int getColumnCount() {
        if (visitCodeGenerator == null) {
            return 0;
        }
        return visitCodeGenerator.getSettings().getColumnCount();
    }

    public void initCodeGenerator(int id, VisitCodeGenerator.Settings settings) {
        visitCodeGenerator = new VisitCodeGenerator(id, settings);
    }

    public void initCodeGenerator(int id) {
        initCodeGenerator(id, new VisitCodeGenerator.Settings());
    }

    protected void setCode(int[][] code) {
        if (code == null) {
            this.code = code;
            return;
        }
//        if (code.length > 0) {
//            for (int i = 0; i < 4; i++)
//                LogUtils.logD("hfr6yughbjjhj", "" + code[i][0] + ", " + code[i][1]);
//
//            LogUtils.logD("hfr6yughbjjhj", "========================== " + code.length);
//        }
        this.nextCode = code;
    }

    public void setError(boolean error) {
        if (errorHandler != null) {
            errorHandler.removeCallbacks(errorRunnable);
        }

        if (isError == error && !error) {
            return;
        }

        isError = error;

        if (!isError) {
            return;
        }

        errorHandler = new Handler();
        errorRunnable = new ErrorRunnable();
        errorHandler.postDelayed(errorRunnable, errorDuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //keep imageView ratio
        float c = getColumnCount();
        float r = getRowCount();

        float ratio;

        if (r == 0 || c == 0) {
            ratio = 1;
        } else {
            ratio = r / c;
        }

        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * ratio));
    }

    protected void init(AttributeSet attrs) {
        context = getContext();
        lineWidth = AppUtils.convertDpToPx(context, DEFAULT_LINE_WIDTH);
        circleLineWidth = AppUtils.convertDpToPx(context, DEFAULT_CIRCLE_LINE_WIDTH);

        if (attrs != null) {
            unpackAttrs(attrs);
        }

        getHolder().addCallback(this);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
    }


    private void unpackAttrs(AttributeSet attrs) {

    }

    public void setDotSizeP(float dotSizeP) {
        this.dotSizeP = dotSizeP;
    }

    private void drawNewCode(Canvas canvas) {
        paint.setShader(null);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(bgColor);

        if (nextCode != null) {
            code = nextCode;
            nextCode = null;
        }

        recalulateDotsCenters(canvas.getHeight(), canvas.getWidth());

        //draw lines
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineColor);


        for (int i = 0; i < selectedDotsCenters.length - 1; i++) {
            //LogUtils.logD("hfr6yughbjjhj", selectedDotsCenters[i][0] + ", "
            //        + selectedDotsCenters[i][1] + " - " + selectedDotsCenters[i + 1][0] + "," + selectedDotsCenters[i + 1][1]);
            drawLine(canvas, selectedDotsCenters[i][0], selectedDotsCenters[i][1], selectedDotsCenters[i + 1][0], selectedDotsCenters[i + 1][1], paint);
        }

        onDrawLines(canvas, paint);

       /* paint.setColor(arrowColor);
        for (int i = 0; i < selectedDotsCenters.length - 1; i++) {
            drawArrow(canvas, selectedDotsCenters[i][0], selectedDotsCenters[i][1], selectedDotsCenters[i + 1][0], selectedDotsCenters[i + 1][1], paint);
        }*/

        //draw circles
        paint.setShader(null);
        paint.setStrokeWidth(circleLineWidth);
        paint.setColor(dotColor);
        paint.setTextSize(AppUtils.convertDpToSp(context, 8));
        paint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < dotsCenters.length; i++) {
            canvas.drawCircle(dotsCenters[i][0], dotsCenters[i][1], dotRadius, paint);
        }

//        paint.setShader(null);
//        paint.setStrokeWidth(AppUtils.convertDpToPx(context, DEFAULT_TEXT));
//        paint.setColor(Color.parseColor("#F764A2"));
//        paint.setStyle(Paint.Style.STROKE);
//        for (int i = 0; i < dotsCenters.length; i++) {
//            canvas.drawText(String.valueOf(i), dotsCenters[i][0], dotsCenters[i][1], paint);
//        }

        //draw selected circles bounds
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);

        for (int i = 0; i < selectedDotsCenters.length; i++) {
            canvas.drawCircle(selectedDotsCenters[i][0], selectedDotsCenters[i][1], dotRadius * DEFAULT_OUTER_RADIUS, paint);
        }

        //draw selected circles
        if (!isError) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(selectedDotColor);

            for (int i = 0; i < selectedDotsCenters.length; i++) {
                canvas.drawCircle(selectedDotsCenters[i][0], selectedDotsCenters[i][1], dotRadius, paint);
            }

            return;
        }

        //draw selected circles bounds error
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(errorColor);
        paint.setStrokeWidth(circleLineWidth);

        for (int i = 0; i < selectedDotsCenters.length; i++) {
            canvas.drawCircle(selectedDotsCenters[i][0], selectedDotsCenters[i][1], dotRadius, paint);
        }
    }

    protected void onDrawLines(Canvas canvas, Paint paint) {

    }

    protected void drawLine(Canvas canvas, int startX, int startY, int endX, int endY, Paint paint) {
        paint.setShader(CodeView.LineShader.with(startX, startY, endX, endY, lineColor1, isError ? errorColor : lineColor2, gradientOffset));
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    private void drawArrow(Canvas canvas, int startX, int startY, int endX, int endY, Paint paint) {
        float[] v = new float[]{endX - startX, endY - startY};

        normalize(v);

        float[] arrow1 = new float[]{endX + arrowSizeP * (v[0] * (float) Math.cos(arrowAngle) - v[1] * (float) Math.sin(arrowAngle)),
                endY + arrowSizeP * (v[0] * (float) Math.sin(arrowAngle) + v[1] * (float) Math.cos(arrowAngle))};

        float[] arrow2 = new float[]{endX + arrowSizeP * (v[0] * (float) Math.cos(-arrowAngle) - v[1] * (float) Math.sin(-arrowAngle)),
                endY + arrowSizeP * (v[0] * (float) Math.sin(-arrowAngle) + v[1] * (float) Math.cos(-arrowAngle))};


        canvas.drawLine(endX, endY, arrow1[0], arrow1[1], paint);
        canvas.drawLine(endX, endY, arrow2[0], arrow2[1], paint);
    }

    private void normalize(float[] v) {
        int c = 0;
        for (int i = 0; i < v.length; i++) {
            c += Math.pow(v[i], 2);
        }

        double norm2 = (float) Math.sqrt(c);

        for (int i = 0; i < v.length; i++) {
            v[i] = (float) (v[i] / norm2);
        }
    }

    private void recalulateDotsCenters(int height, int width) {
        int h, w;
        int paddingStart, paddingEnd, paddingTop;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            paddingStart = getPaddingStart();
            paddingEnd = getPaddingEnd();
        } else {
            paddingStart = getPaddingLeft();
            paddingEnd = getPaddingRight();
        }
        paddingTop = getPaddingTop();

        w = width - paddingStart - paddingEnd;
        h = height - paddingTop - getPaddingBottom();

        int c = getColumnCount();
        int r = getRowCount();

        dotsCenters = new int[c * r][2];
        dotsCentersBounds = new int[c * r][4];

        cellWidth = w / c;
        cellHeight = h / r;

        dotRadius = (int) (Math.min(cellHeight, cellHeight) * dotSizeP) / 2;

        int count = 0;

        for (int j = 0; j < c; j++) {
            for (int i = 0; i < r; i++) {
                dotsCenters[count] = new int[]{paddingStart + i * cellWidth + cellWidth / 2, paddingTop + j * cellHeight + cellHeight / 2};

                dotsCentersBounds[count] = new int[]{
                        paddingStart + i * cellWidth + cellWidth / 2 - dotRadius,
                        paddingTop + j * cellHeight + cellHeight / 2 - dotRadius,
                        paddingStart + i * cellWidth + cellWidth - dotRadius,
                        paddingTop + j * cellHeight + cellHeight - dotRadius};
                count++;
            }
        }

        selectedDotsCenters = new int[code.length][2];

        for (int i = 0; i < code.length; i++) {
            selectedDotsCenters[i] = new int[]{paddingStart + code[i][0] * cellWidth + cellWidth / 2, paddingTop + code[i][1] * cellHeight + cellHeight / 2};
        }
    }

    private class DrawThread extends Thread {
        private volatile boolean runFlag = false;
        private SurfaceHolder surfaceHolder;
        private long prevTime;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            // сохраняем текущее время
            prevTime = System.currentTimeMillis();
        }

        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (runFlag) {

                long now = System.currentTimeMillis();
                long elapsedTime = now - prevTime;

                if (elapsedTime < 1000f / DEFAULT_FRAME_RATE) {
                    continue;
                }

                prevTime = now;

                gradientOffset += gradientMoveSpeed * (DEFAULT_FRAME_RATE / 1000f);

                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        beforeFrameUpdate();
                        drawNewCode(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    protected void beforeFrameUpdate() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new CodeView.DrawThread(holder);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    private static class LineShader extends LinearGradient {

        public static LinearGradient with(int startX, int startY, int endX, int endY, int startColor, int endColor, float offset) {

            boolean se = ((long) offset) % 2 == 0;
            offset = offset - (long) offset;

            double length = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

            double alpha = Math.acos(((double) (endX - startX)) / length);

            double currentOffset = offset * deshSize;

            double dX = currentOffset * Math.cos(alpha);
            double dY = currentOffset * Math.sin(alpha);

            int size = (int) (length / deshSize) + 1;

            size = Math.max(2, size);

            int[] colors = new int[size];

            for (int i = 0; i < size; i++) {
                boolean cc = i % 2 == 0;
                if (se) {
                    colors[i] = cc ? startColor : endColor;
                } else {
                    colors[i] = cc ? endColor : startColor;
                }
            }

            float[] positions = new float[size];

            /*for (int i = 1; i < size; i++) {
                if (i == 1) {
                    positions[i] = 0;
                    continue;
                }

                if (i == size - 1) {
                    positions[i] = 1;
                    continue;
                }

                positions[i] = i / (float) (size - 1);
            }*/


            for (int i = 1; i < size - 1; i++) {
                positions[i] = i / (float) (size - 2) + (1f / (size - 2)) * offset;
            }

            positions[0] = 0;
            positions[size - 1] = 1;

            //
           /* positions[0] = 0;

            for (int i = 1; i < size; i++) {
                positions[i] = positions[i - 1] + (1f / size) * offset;
            }*/

           /* positions[0] = 0;

            for (int i = 1; i < size; i++) {
                positions[i] = positions[i - 1] + (1f / size) * offset;
            }*/

            return new CodeView.LineShader(startX, startY, endX, endY, colors, positions);
        }

        private LineShader(int startX, int startY, int endX, int endY, int[] colors, float[] positions) {
            super(startX, startY, endX, endY, colors, positions, TileMode.REPEAT);
        }

        private LineShader(float x0, float y0, float x1, float y1, @NonNull int[] colors, @Nullable float[] positions, @NonNull TileMode tile) {
            super(x0, y0, x1, y1, colors, positions, tile);
        }

        private LineShader(float x0, float y0, float x1, float y1, int color0, int color1, @NonNull TileMode tile) {
            super(x0, y0, x1, y1, color0, color1, tile);
        }
    }

    private class ErrorRunnable implements Runnable {
        @Override
        public void run() {
            isError = false;
        }
    }

    public void onDestroy() {
        if (errorHandler != null) {
            errorHandler.removeCallbacks(errorRunnable);
        }
    }
}
