package darena13.puzzlefactory;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by darena13 on 21.09.2017.
 */

public interface Levels {
    void leafOver(Point startPoint, int eventX);
    int choseLevel(Point event);
    void drawBackground(Canvas canvas);
}
