package darena13.puzzlefactory;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by darena13 on 11.09.2017.
 */

public interface PlayGround {
    void hSlowMove(Point startPoint, int eventX, int eventY);
    void vSlowMove(Point startPoint);
    void setColorsToPaints();
    void setXYToRects();
    void drawRects(Canvas canvas);
}
