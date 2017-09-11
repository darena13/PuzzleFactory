package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        final DrawView drawView = new DrawView(this);
        setContentView(drawView);

    }

    class DrawView extends View {
        Paint paintRect_1 = new Paint();
        Paint paintRect_2 = new Paint();
        Paint paintRect_3 = new Paint();
        Paint paintRect_4 = new Paint();
        Paint paintRect_5 = new Paint();
        Paint paintRect_6 = new Paint();
        Paint paintRect_7 = new Paint();
        Paint paintRect_8 = new Paint();
        Paint paintRect_9 = new Paint();
        Paint paintRect_10 = new Paint();
        Paint paintRect_11 = new Paint();
        Paint paintRect_12 = new Paint();
        Paint paintRect_13 = new Paint();
        Paint paintRect_14 = new Paint();
        Paint paintRect_15 = new Paint();
        Paint paintRect_16 = new Paint();


        Rect chosenOne = new Rect();

        Rect[][] pictureRects = new Rect[4][4];

        final Point startPoint = new Point(0, 0);
        Direction direction = null;


        public DrawView(Context context) {
            super(context);
            //left top right bottom

            paintRect_1.setColor(Color.rgb(204, 102, 255));
            paintRect_2.setColor(Color.rgb(152, 251, 152));
            paintRect_3.setColor(Color.rgb(128, 159, 255));
            paintRect_4.setColor(Color.rgb(179, 179, 0));
            paintRect_5.setColor(Color.rgb(255, 0, 128));
            paintRect_6.setColor(Color.rgb(0, 206, 209));
            paintRect_7.setColor(Color.rgb(220, 20, 60));
            paintRect_8.setColor(Color.rgb(75, 0, 130));
            paintRect_9.setColor(Color.rgb(173, 255, 47));
            paintRect_10.setColor(Color.rgb(250, 128, 114));
            paintRect_11.setColor(Color.rgb(255, 105, 180));
            paintRect_12.setColor(Color.rgb(138, 43, 226));
            paintRect_13.setColor(Color.rgb(219, 112, 147));
            paintRect_14.setColor(Color.rgb(107, 142, 35));
            paintRect_15.setColor(Color.rgb(255, 127, 80));
            paintRect_16.setColor(Color.rgb(0, 139, 139));

            for (int i = 0; i < pictureRects.length; i++) {
                for (int j = 0; j < pictureRects[i].length; j++) {
                    pictureRects[i][j] = new Rect(j * 100, i * 100, j * 100 + 100, i * 100 + 100);
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            for (int i = 0; i < ; i++) {
                
            }
            canvas.drawRect(rect_1, paintRect_1);
            canvas.drawRect(rect_2, paintRect_2);
            canvas.drawRect(rect_3, paintRect_3);
            canvas.drawRect(rect_4, paintRect_4);
            canvas.drawRect(rect_5, paintRect_5);
            canvas.drawRect(rect_6, paintRect_6);
            canvas.drawRect(rect_7, paintRect_7);
            canvas.drawRect(rect_8, paintRect_8);
            canvas.drawRect(rect_9, paintRect_9);
            canvas.drawRect(rect_10, paintRect_10);
            canvas.drawRect(rect_11, paintRect_11);
            canvas.drawRect(rect_12, paintRect_12);
            canvas.drawRect(rect_13, paintRect_13);
            canvas.drawRect(rect_14, paintRect_14);
            canvas.drawRect(rect_15, paintRect_15);
            canvas.drawRect(rect_16, paintRect_16);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final float eventX = event.getX();
            final float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    startPoint.set((int) eventX, (int) eventY);

                    return true;
                case MotionEvent.ACTION_MOVE:

                    if (direction == null) {

                        if (eventX < startPoint.x && Math.abs(eventX - startPoint.x) > Math.abs(eventY - startPoint.y)) {
                            direction = Direction.LEFT;
                        }

                        if (eventY > startPoint.y && Math.abs(eventX - startPoint.x) < Math.abs(eventY - startPoint.y)) {
                            direction = Direction.TOP;
                        }

                        if (eventX < startPoint.x && Math.abs(eventX - startPoint.x) > Math.abs(eventY - startPoint.y)) {
                            direction = Direction.RIGHT;
                        }

                        if (eventY > startPoint.y && Math.abs(eventX - startPoint.x) < Math.abs(eventY - startPoint.y)) {
                            direction = Direction.DOWN;
                        }

                    } else {
                        if (direction == Direction.LEFT) {
                            rect_16.set((int) eventX - 50, (int) eventY - 50, (int) eventX + 50, (int) eventY + 50);
                        }
                        if (direction == Direction.TOP) {
                            rect_13.set((int) eventX - 50, (int) eventY - 50, (int) eventX + 50, (int) eventY + 50);
                        }
                        if (direction == Direction.RIGHT) {
                            rect_4.set((int) eventX - 50, (int) eventY - 50, (int) eventX + 50, (int) eventY + 50);
                        }
                        if (direction == Direction.DOWN) {
                            rect_1.set((int) eventX - 50, (int) eventY - 50, (int) eventX + 50, (int) eventY + 50);
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:

                    direction = null;

                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }

    }
}
