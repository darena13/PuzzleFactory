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
    int numberOfRects;
    Display display;
    Point dSize;
    int rectSize;
    Rect[][] pictureRects;

    Paint[][] paints;
    @ColorInt
    int topLeftColor;
    int topRightColor;
    int bottomRightColor;
    int bottomLeftColor;

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
    }

    @Override
    public void hSlowMove(Point startPoint) {
        int xIndex = startPoint.x / rectSize;
        int yIndex = startPoint.y / rectSize;
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
    }
}
