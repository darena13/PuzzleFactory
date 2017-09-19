package darena13.puzzlefactory;

import android.graphics.Canvas;

/**
 * Created by darena13 on 9/18/17.
 */

public interface StartBeautifully {
    void setXYToRects();
    void setColorsToPaints();
    void drawRects(Canvas canvas);
    void drawFrame(Canvas canvas);
    void drawBackground(Canvas canvas);
}
