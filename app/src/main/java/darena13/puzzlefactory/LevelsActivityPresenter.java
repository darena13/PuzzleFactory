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

//    private Rect[][][] levelsRects;
    private PuzzleRects[] levelsRects;
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
        vOffset = dSize.y / 6;
        levelsNumber = 5;
//        levelsRects = new Rect[levelsNumber][numberOfRects][numberOfRects];
        levelsRects = new PuzzleRects[levelsNumber];

        for (int i = 0; i < levelsRects.length; i++) {
            levelsRects[i] = new PuzzleRects(numberOfRects, numberOfRects);
        }

        levelsPaints = new Paint[levelsNumber][numberOfRects][numberOfRects];

        for (int k = 0; k < levelsPaints.length; k++) {
            for (int i = 0; i < levelsPaints[k].length; i++) {
                for (int j = 0; j < levelsPaints[k][i].length; j++) {
                    levelsPaints[k][i][j] = new Paint();
                }
            }
        }

        bgColor = 0xfffceee5;
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

    //задаем координаты прямоугольников
    @Override
    public void setXYToRects(int puzzleIndex) {
        int shift = numberOfRects * rectSize * (puzzleIndex) + vOffset * (puzzleIndex);
        for (int k = 0; k < levelsRects.length; k++) {
            levelsRects[k].setXY(rectSize, vOffset * (k + 1) + numberOfRects * rectSize * k - shift, hOffset);
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
            for (int i = 0; i < levelsPaints[k].length; i++) {
                for (int j = 0; j < levelsPaints[k][i].length; j++) {
                    levelsPaints[k][i][j].setColor(colors[k][i][j]);
                }
            }
        }
    }

    @Override
    public void drawRects(Canvas canvas) {
        //рисуем все прямоугольники из изначального массива
//        for (int k = 0; k < levelsRects.length; k++) {
//            for (int i = 0; i < levelsRects[k].length; i++) {
//                for (int j = 0; j < levelsRects[k][i].length; j++) {
//                    canvas.drawRect(levelsRects[k][i][j], levelsPaints[k][i][j]);
//                }
//            }
//        }
        Rect[][] rectsToDraw;
        for (int k = 0; k < levelsRects.length; k++) {
            rectsToDraw = levelsRects[k].getRects();
            for (int i = 0; i < rectsToDraw.length; i++) {
                for (int j = 0; j < rectsToDraw[i].length; j++) {
                    canvas.drawRect(rectsToDraw[i][j], levelsPaints[k][i][j]);
                }
            }
        }
    }

    @Override
    public void moveAll(int distY) {
//        if (levelsRects[0][0][0].top > vOffset & distY > 0) {
//            return;
//        }
//        if ((dSize.y - levelsRects[levelsRects.length - 1][numberOfRects - 1][numberOfRects - 1].bottom) > vOffset & distY < 0) {
//            return;
//        }
//        for (int k = 0; k < levelsRects.length; k++) {
//            for (int i = 0; i < levelsRects[k].length; i++) {
//                for (int j = 0; j < levelsRects[k][i].length; j++) {
//                    levelsRects[k][i][j].set(
//                            levelsRects[k][i][j].left, //left
//                            levelsRects[k][i][j].top + distY, // top
//                            levelsRects[k][i][j].right, // right
//                            levelsRects[k][i][j].bottom + distY); // bottom
//                }
//            }
//        }
        Log.v(TAG, "moveAll");
        if (levelsRects[0].getTop() > vOffset & distY > 0) {
            Log.v(TAG, "moveAll - cant move further down");
            return;
        }
        if ((dSize.y - levelsRects[levelsRects.length - 1].getBottom()) > vOffset & distY < 0) {
            Log.v(TAG, "moveAll - cant move further up");
            return;
        }
        for (int k = 0; k < levelsRects.length; k++) {
            Log.v(TAG, "moveAll - MOVIN");
            levelsRects[k].vMove(distY);
        }
    }

    @Override
    public int getSelectedPuzzle(int eventY) {
        for (int i = 0; i < levelsRects.length; i++) {
            if (eventY >= levelsRects[i].getTop() && eventY <= levelsRects[i].getBottom()) {
                return i;
            }
        }
        return -1;
    }
}
