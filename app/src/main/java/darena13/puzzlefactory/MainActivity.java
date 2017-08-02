package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        final DrawView drawView = new DrawView(this);
        setContentView(drawView);

    }

    class DrawView extends View {
        Paint paintYellow = new Paint();
        Paint paintBlue = new Paint();
        Paint paintCyan = new Paint();
        Paint paintMagenta = new Paint();

        Rect rect_1, rect_2, rect_3, rect_4;
        Rect chosenOne = new Rect();


        public DrawView(Context context) {
            super(context);
            //left top right bottom
            rect_1 = new Rect(0, 0, 100, 100);
            rect_2 = new Rect(100, 0, 200, 100);
            rect_3 = new Rect(0, 100, 100, 200);
            rect_4 = new Rect(100, 100, 200, 200);
            paintYellow.setColor(Color.YELLOW);
            paintBlue.setColor(Color.BLUE);
            paintCyan.setColor(Color.CYAN);
            paintMagenta.setColor(Color.MAGENTA);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            canvas.drawRect(rect_1, paintYellow);
            canvas.drawRect(rect_2, paintBlue);
            canvas.drawRect(rect_3, paintCyan);
            canvas.drawRect(rect_4, paintMagenta);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final float eventX = event.getX();
            final float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (eventX >= (rect_1.exactCenterX() - 50f) && eventX <= (rect_1.exactCenterX() + 50f) && eventY >= (rect_1.exactCenterY() - 50f) && eventY <= (rect_1.exactCenterY() + 50f)) {
                        chosenOne = rect_1;
                    }
                    if (eventX >= (rect_2.exactCenterX() - 50f) && eventX <= (rect_2.exactCenterX() + 50f) && eventY >= (rect_2.exactCenterY() - 50f) && eventY <= (rect_2.exactCenterY() + 50f)) {
                        chosenOne = rect_2;
                    }
                    if (eventX >= (rect_3.exactCenterX() - 50f) && eventX <= (rect_3.exactCenterX() + 50f) && eventY >= (rect_3.exactCenterY() - 50f) && eventY <= (rect_3.exactCenterY() + 50f)) {
                        chosenOne = rect_3;
                    }
                    if (eventX >= (rect_4.exactCenterX() - 50f) && eventX <= (rect_4.exactCenterX() + 50f) && eventY >= (rect_4.exactCenterY() - 50f) && eventY <= (rect_4.exactCenterY() + 50f)) {
                        chosenOne = rect_4;
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (chosenOne == rect_1) rect_1.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    if (chosenOne == rect_2) rect_2.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    if (chosenOne == rect_3) rect_3.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    if (chosenOne == rect_4) rect_4.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                   break;
                case MotionEvent.ACTION_UP:
                    if (chosenOne == rect_1) rect_1.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    if (chosenOne == rect_2) rect_2.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    if (chosenOne == rect_3) rect_3.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    if (chosenOne == rect_4) rect_4.set((int)eventX - 50, (int)eventY - 50, (int)eventX + 50, (int)eventY + 50);
                    chosenOne = null;
                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }

    }
}
