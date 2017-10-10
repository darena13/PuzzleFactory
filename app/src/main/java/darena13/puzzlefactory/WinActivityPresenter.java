package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by darena13 on 10.10.2017.
 */

public class WinActivityPresenter implements AfterWin{
    private static final String TAG = "Win Presenter";

    private Point dSize;@ColorInt
    private int bgColor;  //будем брать из настроек или из другого общего места
    private String congrat;
    private Paint textPaint;


    public WinActivityPresenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        dSize = new Point();
        display.getSize(dSize);

        congrat = "WOW! You are like rainbow moon light on unicorn skin at warm summer night!"; //будем генерить и локализировать
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xFFFF4081);
        textPaint.setTextSize(100);
        textPaint.setStyle(Paint.Style.STROKE);

        bgColor = 0xfffceee5;
    }

    @Override
    public void drawBackground(Canvas canvas) {
        Log.v(TAG, "drawBG");
        Rect bgRect = new Rect(0, 0, dSize.x, dSize.y);
        Paint bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        canvas.drawRect(bgRect, bgPaint);
    }

    @Override
    public void drawText(Canvas canvas) {
        Log.v(TAG, "drawText");
        canvas.drawText(congrat, 0, dSize.y / 2, textPaint);
    }
}
