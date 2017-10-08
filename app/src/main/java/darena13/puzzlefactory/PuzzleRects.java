package darena13.puzzlefactory;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by darena13 on 08.10.2017.
 */

public class PuzzleRects {
    private Rect[][] rects;
    private Point topRight;
    private Point topLeft;
    private Point bottomRight;
    private Point bottomLeft;
    private int top;
    private int bottom;

    public PuzzleRects(int numberOfRects) {
        rects = new Rect[numberOfRects][numberOfRects];
    }

    public Rect[][] getRects() {
        return rects;
    }

    public void setRects(Rect[][] rects) {
        this.rects = rects;
    }

    public void setXY(int rectSize, int puzzleSize, int vOffset, int hOffset) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                rects[i][j] = new Rect(
                        j * rectSize + hOffset, //left
                        i * rectSize + puzzleSize + vOffset, // top
                        j * rectSize + rectSize + hOffset, // right
                        i * rectSize + rectSize + puzzleSize + vOffset); // bottom
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
}
