package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "COLORLOVERS Main";
    PlayActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        presenter = new PlayActivityPresenter(getApplicationContext());

        final DrawView drawView = new DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {
        Point startPoint = new Point(0, 0);
        Direction direction = null;

        public DrawView(Context context) {
            super(context);
            presenter.setColorsToPaints();
            presenter.setXYToRects();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            presenter.drawRects(canvas);
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

                    if (!(direction == null)) {
                        switch (direction) {
                            case HRZ:
                                presenter.hSlowMove(startPoint, (int) eventX);
                                break;
                            case VRT:
                                presenter.vSlowMove(startPoint, (int) eventY);
                                break;
                        }
                    } else if (Math.sqrt(eventX * eventX + eventY * eventY) > 50) {
                        if (Math.abs(eventX - startPoint.x) > Math.abs(eventY - startPoint.y)) {
                            direction = Direction.HRZ;
                            Log.v(TAG, "HRZ");
                            presenter.hChoseLineToRotate(startPoint);
                        } else {
                            direction = Direction.VRT;
                            Log.v(TAG, "VRT");
                            presenter.vChoseLineToRotate(startPoint);
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (direction == Direction.HRZ) {
                        presenter.hPutRectsInPlaces();
                    } else {
                        presenter.vPutRectsInPlace();
                    }
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
