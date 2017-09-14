package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by darena13 on 11.09.2017.
 */

public class PlayActivityPresenter implements PlayGround {
    private int numberOfRects;
    private Display display;
    private Point dSize;
    private int rectSize;
    private Rect[][] pictureRects;

    private Paint[][] paints;

    @ColorInt
    private int topLeftColor;
    private int topRightColor;
    private int bottomRightColor;
    private int bottomLeftColor;

    private Rect[] rectsToRotate;

    private int[] rectsLeftsOnStart;
    private int[] rectsRightsOnStart;
    private Paint[] paintsToRotate;

    public PlayActivityPresenter(Context context) {
        this.numberOfRects = 7;
        topLeftColor = 0xfffb9908;
        topRightColor = 0xff177a27;
        bottomRightColor = 0xffd34989;
        bottomLeftColor = 0xff1c4cb9;

        pictureRects = new Rect[numberOfRects][numberOfRects];
        paints = new Paint[numberOfRects][numberOfRects];

        for (int i = 0; i < paints.length; i++) {
            for (int j = 0; j < paints[i].length; j++) {
                paints[i][j] = new Paint();
            }
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        dSize = new Point();
        display.getSize(dSize);
        rectSize = dSize.x / numberOfRects;

        rectsToRotate = new Rect[numberOfRects * 3];

        rectsLeftsOnStart = new int[numberOfRects * 3];
        rectsRightsOnStart = new int[numberOfRects * 3];

        paintsToRotate = new Paint[numberOfRects * 3];
    }

    public void chosenLineH(Point startPoint) {
        //вычисляем какую строчку двигать
        int yIndex = startPoint.y / rectSize;
        //копируем прямоугольники из строчки в новый массив три раза
//        System.arraycopy(pictureRects[yIndex], 0, rectsToRotate, 0, pictureRects[yIndex].length);
//        System.arraycopy(pictureRects[yIndex], 0, rectsToRotate, numberOfRects, pictureRects[yIndex].length);
//        System.arraycopy(pictureRects[yIndex], 0, rectsToRotate, numberOfRects * 2, pictureRects[yIndex].length);

        //заполняем центральную часть копиями прямоугольников из исходной строчки
        for (int i = numberOfRects; i < pictureRects[yIndex].length; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[yIndex][i - numberOfRects].left,
                    pictureRects[yIndex][i - numberOfRects].top,
                    pictureRects[yIndex][i - numberOfRects].right,
                    pictureRects[yIndex][i - numberOfRects].bottom
            );
        }
        //заполняем левую часть копиями прямоугольников из исходной строчки со смещением
        for (int i = 0; i < pictureRects[yIndex].length; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[yIndex][i].left - numberOfRects * rectSize,
                    pictureRects[yIndex][i].top,
                    pictureRects[yIndex][i].right - numberOfRects * rectSize,
                    pictureRects[yIndex][i].bottom
            );
        }
        //заполняем правую часть копиями прямоугольников из исходной строчки со смещением
        for (int i = numberOfRects * 2 + 1; i < pictureRects[yIndex].length; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[yIndex][i - (numberOfRects * 2 + 1)].left + numberOfRects * rectSize,
                    pictureRects[yIndex][i - (numberOfRects * 2 + 1)].top,
                    pictureRects[yIndex][i - (numberOfRects * 2 + 1)].right + numberOfRects * rectSize,
                    pictureRects[yIndex][i - (numberOfRects * 2 + 1)].bottom
            );
        }
        //запоминаем исходные координаты прямоугольников
        for (int i = 0; i < rectsLeftsOnStart.length; i++) {
            rectsLeftsOnStart[i] = rectsToRotate[i].left;
        }
        for (int i = 0; i < rectsRightsOnStart.length; i++) {
            rectsRightsOnStart[i] = rectsToRotate[i].right;
        }
        //заполняем массив красок для прямоугольников
        System.arraycopy(paints[yIndex], 0, paintsToRotate, 0, paints[yIndex].length);
        System.arraycopy(paints[yIndex], 0, paintsToRotate, numberOfRects, paints[yIndex].length);
        System.arraycopy(paints[yIndex], 0, paintsToRotate, numberOfRects * 2, paints[yIndex].length);

//        paintsToRotate[0] = paintsToRotate[paintsToRotate.length - 2];
//        paintsToRotate[paintsToRotate.length - 1] = paintsToRotate[1];

    }

    @Override
    public void hSlowMove(Point startPoint, int eventX, int eventY) {
        //расстояние по горизонтали
        int projOnX = eventX - startPoint.x;
        //двигаем на это расстояние
        for (int i = 0; i < rectsToRotate.length; i++) {
            rectsToRotate[i].set(rectsLeftsOnStart[i] + projOnX, rectsToRotate[i].top, rectsRightsOnStart[i] + projOnX, rectsToRotate[i].bottom);
        }
    }

    @Override
    public void vSlowMove(Point startPoint) {

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
        int[][] colors = GradientGenerator.fourColors(numberOfRects, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
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
        //рисуем выбранную линию (строчку или столбец)
        if (!(rectsToRotate[0] == null)) {
            for (int i = 0; i < rectsToRotate.length; i++) {
                canvas.drawRect(rectsToRotate[i], paintsToRotate[i]);
            }
        }
    }
}
