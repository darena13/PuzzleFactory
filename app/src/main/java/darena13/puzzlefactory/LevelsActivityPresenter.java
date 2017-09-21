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
 * Created by darena13 on 21.09.2017.
 */

public class LevelsActivityPresenter implements Levels {
    private static final String TAG = "Levels Presenter";
    private Point dSize;

    @ColorInt
    private int bgColor;  //будем брать из настроек или из другого общего места

    public LevelsActivityPresenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        dSize = new Point();
        display.getSize(dSize);
    }

    @Override
    public void leafOver(Point startPoint, int eventX) {
        //листаем горизонтально уровни
    }

    @Override
    public int choseLevel(Point event) {
        //если кнопка, возвращаем индекс и фигарим интент
        return 0;
    }

    @Override
    public void drawBackground(Canvas canvas) {
        Log.v(TAG, "drawBG");
        Rect bgRect = new Rect(0, 0, dSize.x, dSize.y);
        Paint bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        canvas.drawRect(bgRect, bgPaint);
    }

    //делаем 10 массивов = 10 уровней
    //делаем 10 кнопак
    //меню настроек
    //список паков с уровнями
    //сделать канвас во фрагменте?
}
