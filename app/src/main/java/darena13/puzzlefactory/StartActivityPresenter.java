package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by darena13 on 9/18/17.
 */

public class StartActivityPresenter implements StartBeautifully {
    private static final String TAG = "Start Presenter";

    private int numberOfRects;
    private Point dSize;
    private int rectSize;

    private Rect[][] pictureRects;
    private Paint[][] paints;

    @ColorInt
    private int bgColor;  //будем брать из настроек или из другого общего места

    Rect btnRect;

    StartActivityPresenter(Context context) {
        this.numberOfRects = 7;  //будем брать из настроек

        pictureRects = new Rect[numberOfRects][numberOfRects];
        paints = new Paint[numberOfRects][numberOfRects];

        for (int i = 0; i < paints.length; i++) {
            for (int j = 0; j < paints[i].length; j++) {
                paints[i][j] = new Paint();
            }
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        dSize = new Point();
        display.getSize(dSize);
        rectSize = dSize.x / numberOfRects;

        bgColor = 0xfffceee5;

        btnRect =  new Rect(rectSize * 3, (numberOfRects + 3) * rectSize, dSize.x, (numberOfRects + 4) * rectSize);
    }

    @Override
    public void setXYToRects() {
        for (int i = 0; i < pictureRects.length; i++) {
            for (int j = 0; j < pictureRects[i].length; j++) {
                pictureRects[i][j] = new Rect(j * rectSize, i * rectSize, j * rectSize + rectSize, i * rectSize + rectSize);
            }
        }
    }

    @Override
    public void setColorsToPaints() {
//        int[][] colors = GradientGenerator.fourColors(numberOfRects, 0xfffceee5, 0xfffceee5, 0xfffceee5, 0xfffceee5);
//        int[][] colors = PuzzleGenerator.puzzle(numberOfRects, 0);
        int[][] colors = ColorSets.puzzle_0;
        for (int i = 0; i < paints.length; i++) {
            for (int j = 0; j < paints[i].length; j++) {
                paints[i][j].setColor(colors[i][j]);
            }
        }
    }

    @Override
    public void drawRects(Canvas canvas) {
        //рисуем все прямоугольники из изначального массива
        for (int i = 0; i < pictureRects.length; i++) {
            for (int j = 0; j < pictureRects[i].length; j++) {
                canvas.drawRect(pictureRects[i][j], paints[i][j]);
            }
        }
//        //рисуем выбранную линию (строчку или столбец)
//        if (!(rectsToRotate[0] == null)) {
//            for (int i = 0; i < rectsToRotate.length; i++) {
//                canvas.drawRect(rectsToRotate[i], paintsToRotate[i]);
//            }
//        }
    }
    public void drawFrame(Canvas canvas) {
        Log.v(TAG, "drawFrame");
        Rect frameBottomRect = new Rect(0, numberOfRects * rectSize, dSize.x, dSize.y);
        Paint framePaint = new Paint();
        framePaint.setColor(0xffffffff);
        canvas.drawRect(frameBottomRect, framePaint);

        Rect logoRect =  new Rect(0, (numberOfRects + 1) * rectSize, dSize.x, (numberOfRects + 2) * rectSize);
        Paint logoPaint = new Paint();
        logoPaint.setColor(0xffFFE0CC);
        canvas.drawRect(logoRect, logoPaint);

        Paint btnPaint = new Paint();
        btnPaint.setColor(0xffE65F9E);
        canvas.drawRect(btnRect, btnPaint);
    }

    @Override
    public void drawBackground(Canvas canvas) {
        Log.v(TAG, "drawBG");
        Rect bgRect = new Rect(0, 0, dSize.x, dSize.y);
        Paint bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        canvas.drawRect(bgRect, bgPaint);
    }

    public boolean isBtn (int eventX, int eventY) {
       return true;
    }
}
