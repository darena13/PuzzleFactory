package darena13.puzzlefactory;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by darena13 on 11.09.2017.
 */

public interface PlayGround {
    void hSlowMove(Point startPoint, int eventX);
    void vSlowMove(Point startPoint, int eventY);
    void hChoseLineToRotate(Point startPoint);
    void vChoseLineToRotate(Point startPoint);
    void hPutRectsInPlaces(Point startPoint);
    void vPutRectsInPlace(Point startPoint);
    void setColorsToPaintsToWin(int index);
    int[][] mixPuzzle(int index, int times);
    void setColorsToPaints(int index);
    void setXYToRects();
    void drawRects(Canvas canvas);
    void drawFrame(Canvas canvas);
    void drawBackground(Canvas canvas);
    boolean isItInBounds(Point startPoint);
    boolean isWin();
    void setColorsToMovesPaints();
    void setColors(int puzzleIndex);
}
