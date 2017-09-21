package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
//import android.support.v4.app.DialogFragment;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = "COLORLOVERS Main";
    PlayActivityPresenter presenter;
    int puzzleIndex = 0; //получим из интента
    DialogFragment winDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        //возвращаем основную тему после показа сплешскрина (Branded launch)
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new PlayActivityPresenter(getApplicationContext());

        final DrawView drawView = new DrawView(this);
        setContentView(drawView);

        winDialog = new WinDialog();
    }

    class DrawView extends View {
        Point startPoint = new Point(0, 0);
        Direction direction = null;

        public DrawView(Context context) {
            super(context);
            presenter.setPaintsToWin(puzzleIndex);
            presenter.setColorsToPaints(puzzleIndex);
            presenter.setXYToRects();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            presenter.drawBackground(canvas);
            presenter.drawRects(canvas);
            presenter.drawFrame(canvas);
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
                    int dx = (int) eventX - startPoint.x;
                    int dy = (int) eventY - startPoint.y;
                    if (!(direction == null)) {
                        switch (direction) {
                            case HRZ:
                                presenter.hSlowMove(startPoint, (int) eventX);
                                break;
                            case VRT:
                                presenter.vSlowMove(startPoint, (int) eventY);
                                break;
                        }
                    } else if (Math.sqrt(dx * dx + dy * dy) > 20) {
                        if (Math.abs(dx) > Math.abs(dy)) {
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
                        presenter.hPutRectsInPlaces(startPoint);
                    } else if (direction == Direction.VRT) {
                        presenter.vPutRectsInPlace(startPoint);
                    }
                    direction = null;
                    //проверка на победу
                    if (presenter.isWin()) {
                        Log.v(TAG, "WIN!");
                        winDialog.show(getFragmentManager(), "winDialog");
                    }
                    //TODO: сохраняем прогресс
                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }
    }
}
