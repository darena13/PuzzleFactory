package darena13.puzzlefactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class WinActivity extends AppCompatActivity {
    private static final String TAG = "COLORLOVERS AfterWin";
    WinActivityPresenter presenter;
    int puzzleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new WinActivityPresenter(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            puzzleIndex = bundle.getInt("PUZZLE");
        }

        final WinActivity.DrawView drawView = new WinActivity.DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
//            presenter.setColorsToLevelsPaints();
//            presenter.setXYToRects();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            presenter.drawBackground(canvas);
            presenter.drawText(canvas);

//            presenter.drawRects(canvas);
//            presenter.drawFrame(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final float eventX = event.getX();
            final float eventY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    return true;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:
                    Intent intent = new Intent(WinActivity.this, LevelsActivity.class);
                    intent.putExtra("PUZZLE", puzzleIndex);
                    startActivity(intent);
                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }
    }
}