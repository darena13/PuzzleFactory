package darena13.puzzlefactory;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by darena13 on 08.10.2017.
 */

public class PuzzleRects {
    private Rect[][] rects;

    public PuzzleRects(int vNumberOfRects, int hNumberOfRects) {
        rects = new Rect[vNumberOfRects][hNumberOfRects];
    }

    public Rect[][] getRects() {
        return rects;
    }

    public void setXY(int rectSize, int vPuzzleSizeShift, int hPuzzleSizeShift, int vOffset, int hOffset) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                rects[i][j] = new Rect(
                        j * rectSize + hPuzzleSizeShift + hOffset, //left
                        i * rectSize + vPuzzleSizeShift + vOffset, // top
                        j * rectSize + rectSize + hPuzzleSizeShift + hOffset, // right
                        i * rectSize + rectSize + vPuzzleSizeShift + vOffset); // bottom
            }
        }
    }

    public int getTop() {
        return rects[0][0].top;
    }

    public int getBottom() {
        return  rects[rects.length - 1][0].bottom;
    }

    public void vMove(int distY) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                rects[i][j].set(
                        rects[i][j].left, //left
                        rects[i][j].top + distY, // top
                        rects[i][j].right, // right
                        rects[i][j].bottom + distY); // bottom
            }
        }
    }

    void drawRects(Canvas canvas, Paint[][] paints) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                canvas.drawRect(rects[i][j], paints[i][j]);
            }
        }
    }

    void drawRects(Canvas canvas, Paint[][] paints, int movesMade) {
        int vIndex;
        if (movesMade >= rects.length) {
            vIndex = 0;
        } else {
            vIndex = rects.length - movesMade;
        }
        for (int i = rects.length - 1; i >= vIndex; i--) {
            for (int j = 0; j < rects[i].length; j++) {
                canvas.drawRect(rects[i][j], paints[i][j]);
            }
        }
    }

}
