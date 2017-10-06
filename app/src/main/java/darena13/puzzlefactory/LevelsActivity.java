package darena13.puzzlefactory;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LevelsActivity extends AppCompatActivity {
    private static final String TAG = "COLORLOVERS Levels";
    LevelsActivityPresenter presenter;
    int puzzleIndex = 0; //получим из интента

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new LevelsActivityPresenter(getApplicationContext());

        final LevelsActivity.DrawView drawView = new LevelsActivity.DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {
        Point startPoint = new Point(0, 0);

        public DrawView(Context context) {
            super(context);

            presenter.setColorsToLevelsPaints();
            presenter.setXYToRects();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            presenter.drawBackground(canvas);
            presenter.drawRects(canvas);
//            presenter.drawFrame(canvas);
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

                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }
    }
}