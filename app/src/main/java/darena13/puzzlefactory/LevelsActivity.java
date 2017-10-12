package darena13.puzzlefactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            puzzleIndex = bundle.getInt("PUZZLE");
        }

        final LevelsActivity.DrawView drawView = new LevelsActivity.DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {
        int startPointY;
        long startTime;
        int lastPointY;
        int distY;

        public DrawView(Context context) {
            super(context);
            presenter.setColorsToLevelsPaints();
            presenter.setXYToRects(puzzleIndex); //передать смещение на нужный пазл
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
                    startPointY = (int) eventY;
                    startTime = System.currentTimeMillis();
                    lastPointY = (int) eventY;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    distY = (int) eventY - lastPointY;
                    lastPointY = (int) eventY;
                    presenter.moveAll(distY);
                    break;
                case MotionEvent.ACTION_UP:
                    //посчитать какой пазл поставить наверх и поставить (?)

                    //проверить не было ли нажатия и перейти к выбранному пазлу
                    int selectedPuzzle;
                    if (Math.abs(startPointY - eventY) < 20 & (System.currentTimeMillis() - startTime) < 500) {
                        Log.v(TAG, "PRESSED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        selectedPuzzle = presenter.getSelectedPuzzle((int) eventY);
                        if (selectedPuzzle >= 0) {
                            Log.v(TAG, "selectedPuzzle = " + selectedPuzzle);
                            Intent intent = new Intent(LevelsActivity.this, PlayActivity.class);
                            intent.putExtra("SELECTED_PUZZLE", selectedPuzzle);
                            startActivity(intent);
                        }
                    }
                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }
    }
}