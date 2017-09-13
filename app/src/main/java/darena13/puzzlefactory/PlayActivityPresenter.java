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

    //    Rect rectToRotate;
    private Rect[] rectsToRotate;

    private int[] rectsLeftsOnStart;
    private int[] rectsRightsOnStart;

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

//        rectToRotate = new Rect(100, 100, 200, 200);
        rectsToRotate = new Rect[numberOfRects];

        rectsLeftsOnStart = new int[numberOfRects];
        rectsRightsOnStart = new int[numberOfRects];
    }

    public void chosenLineH(Point startPoint) {
        int yIndex = startPoint.y / rectSize;
        for (int i = 0; i < rectsToRotate.length; i++) {
            rectsToRotate[i] = pictureRects[yIndex][i];
        }

        for (int i = 0; i < rectsLeftsOnStart.length; i++) {
            rectsLeftsOnStart[i] = rectsToRotate[i].left;
        }

        for (int i = 0; i < rectsRightsOnStart.length; i++) {
            rectsRightsOnStart[i] = rectsToRotate[i].right;
        }

//        rectToRotate.set(0, rectSize*yIndex, rectSize*numberOfRects, rectSize*(yIndex+1));
    }

    @Override
    public void hSlowMove(Point startPoint, int eventX, int eventY) {
//        rectToRotate.set(eventX - rectSize/2, rectToRotate.top, eventX + rectSize/2, rectToRotate.bottom);

        int projOnX = eventX - startPoint.x;

        for (int i = 0; i < rectsToRotate.length; i++) {
            rectsToRotate[i].set(rectsLeftsOnStart[i] + projOnX, rectsToRotate[i].top, rectsRightsOnStart[i] + projOnX, rectsToRotate[i].bottom);
        }
    }

    @Override
    public void vSlowMove(Point startPoint) {
//        int xIndex = startPoint.x / rectSize;
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
//        int paintsCount = 0;
//        for (Rect[] pictureRect : pictureRects) {
//            for (Rect rect : pictureRect) {
//                canvas.drawRect(rect, paints[paintsCount % paints.length]);
//                paintsCount++;
//
//            }
//        }
        for (int i = 0; i < pictureRects.length; i++) {
            for (int j = 0; j < pictureRects[i].length; j++) {
                canvas.drawRect(pictureRects[i][j], paints[i][j]);
            }
        }
        if (!(rectsToRotate[0] == null)) {
            for (int i = 0; i < rectsToRotate.length; i++) {
                canvas.drawRect(rectsToRotate[i], paints[i][0]); //придумать как взять нужные пейнты
            }
        }
//        canvas.drawRect(rectToRotate, paints[0][0]);
    }
}
