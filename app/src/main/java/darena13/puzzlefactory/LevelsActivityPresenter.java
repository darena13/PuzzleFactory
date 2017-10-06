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
 * Created by darena13 on 21.09.2017.
 */

public class LevelsActivityPresenter implements Levels {
    private static final String TAG = "Levels Presenter";
    private Point dSize;
    private int rectSize;

    private int numberOfRects;

    private int hOffset;
    private int vOffset;

    private Rect[][][] levelsRects;
    private Paint[][][] levelsPaints;
    private int levelsNumber;

    @ColorInt
    private int bgColor;  //будем брать из настроек или из другого общего места

    public LevelsActivityPresenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        dSize = new Point();
        display.getSize(dSize);

        numberOfRects = 7; //будем брать из настроек или из другого общего места

        rectSize = dSize.x / (numberOfRects + 2);

        hOffset = dSize.x / (numberOfRects + 2);
        vOffset = dSize.y /6;
        levelsNumber = 5;
        levelsRects = new Rect[levelsNumber][numberOfRects][numberOfRects];
        levelsPaints = new Paint[levelsNumber][numberOfRects][numberOfRects];

        for (int k = 0; k < levelsPaints.length; k++) {
            for (int i = 0; i < levelsPaints[k].length; i++) {
                for (int j = 0; j < levelsPaints[k][i].length; j++) {
                    levelsPaints[k][i][j] = new Paint();
                }
            }
        }
    }

    @Override
    public void leafOver(Point startPoint, int eventX) {
        //листаем горизонтально уровни
    }

    @Override
    public int choseLevel(Point event) {
        //если кнопка, возвращаем индекс и фигарим интент
        return 0;
    }

    @Override
    public void drawBackground(Canvas canvas) {
        Log.v(TAG, "drawBG");
        Rect bgRect = new Rect(0, 0, dSize.x, dSize.y);
        Paint bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        canvas.drawRect(bgRect, bgPaint);
    }

    //делаем 10 массивов = 10 уровней
    //делаем 10 кнопак
    //меню настроек
    //список паков с уровнями
    //сделать канвас во фрагменте?

    //задаем координаты прямоугольников
    @Override
    public void setXYToRects() {
        for (int k = 0; k < levelsRects.length; k++) {
            for (int i = 0; i < levelsRects[k].length; i++) {
                for (int j = 0; j < levelsRects[k][i].length; j++) {
                    levelsRects[k][i][j] = new Rect(
                            j * rectSize + hOffset, //left
                            i * rectSize + numberOfRects * rectSize * k + vOffset * (k + 1), // top
                            j * rectSize + rectSize + hOffset, // right
                            i * rectSize + rectSize + numberOfRects * rectSize * k + vOffset * (k + 1)); // bottom
                }
            }
        }
    }

    //задаем цвета пейнтам
    @Override
    public void setColorsToLevelsPaints() {
        int[][][] colors = new int[levelsNumber][numberOfRects][numberOfRects];

        for (int k = 0; k < colors.length; k++) {
            colors[k] = ColorSets.getPuzzle(k);
        }

        for (int k = 0; k < levelsPaints.length; k++) {
            Log.v(TAG, "Length k = " + levelsPaints.length);
            for (int i = 0; i < levelsPaints[k].length; i++) {
                Log.v(TAG, "Length i = " + levelsPaints[k].length);
                for (int j = 0; j < levelsPaints[k][i].length; j++) {
                    Log.v(TAG, "Length j = " + levelsPaints[k][i].length);
                    levelsPaints[k][i][j].setColor(colors[k][i][j]);
                }
            }
        }
    }

    @Override
    public void drawRects(Canvas canvas) {
        //рисуем все прямоугольники из изначального массива
        for (int k = 0; k < levelsRects.length; k++) {
            for (int i = 0; i < levelsRects[k].length; i++) {
                for (int j = 0; j < levelsRects[k][i].length; j++) {
                    canvas.drawRect(levelsRects[k][i][j], levelsPaints[k][i][j]);
                }
            }
        }
    }
}
