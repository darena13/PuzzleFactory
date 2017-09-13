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

        rectsToRotate = new Rect[numberOfRects + 2];

        rectsLeftsOnStart = new int[numberOfRects + 2];
        rectsRightsOnStart = new int[numberOfRects + 2];

        paintsToRotate = new Paint[numberOfRects + 2];
    }

    public void chosenLineH(Point startPoint) {
        //вычисляем какую строчку двигать
        int yIndex = startPoint.y / rectSize;
        //копируем прямоугольники из строчки в новый массив
        for (int i = 1; i < rectsToRotate.length - 1; i++) {
            rectsToRotate[i] = pictureRects[yIndex][i - 1];
        }
        //добавляем прямоугольник в начало строчки
        rectsToRotate[0] = new Rect(
                rectsToRotate[1].left - rectSize,
                rectsToRotate[1].top,
                rectsToRotate[1].right - rectSize,
                rectsToRotate[1].bottom);
        //и в конец
        rectsToRotate[rectsToRotate.length - 1] = new Rect(
                rectsToRotate[rectsToRotate.length - 2].left + rectSize,
                rectsToRotate[rectsToRotate.length - 2].top,
                rectsToRotate[rectsToRotate.length - 2].right + rectSize,
                rectsToRotate[rectsToRotate.length - 2].bottom);
        //запоминаем исходные координаты прямоугольников
        for (int i = 0; i < rectsLeftsOnStart.length; i++) {
            rectsLeftsOnStart[i] = rectsToRotate[i].left;
        }
        for (int i = 0; i < rectsRightsOnStart.length; i++) {
            rectsRightsOnStart[i] = rectsToRotate[i].right;
        }
        //заполняем массив красок для прямоугольников
        for (int i = 1; i < paintsToRotate.length - 1; i++) {
            paintsToRotate[i] = paints[yIndex][i - 1];
        }
        paintsToRotate[0] = paintsToRotate[paintsToRotate.length - 2];
        paintsToRotate[paintsToRotate.length - 1] = paintsToRotate[1];
        if (rectsToRotate[rectsToRotate.length - 1].left == dSize.x) {

        }
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
        //рисуем все прямоугольники
        for (int i = 0; i < pictureRects.length; i++) {
            for (int j = 0; j < pictureRects[i].length; j++) {
                canvas.drawRect(pictureRects[i][j], paints[i][j]);
            }
        }
        //рисуем выбранную линию
        if (!(rectsToRotate[0] == null)) {
            for (int i = 0; i < rectsToRotate.length; i++) {
                canvas.drawRect(rectsToRotate[i], paintsToRotate[i]);
            }
        }
    }
}
