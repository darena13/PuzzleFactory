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

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "COLORLOVERS Start";
    StartActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //возвращаем основную тему после показа сплешскрина (Branded launch)
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new StartActivityPresenter(getApplicationContext());

        final StartActivity.DrawView drawView = new StartActivity.DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
            presenter.setColorsToPaints();
            presenter.setXYToRects();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            presenter.drawBackground(canvas);
            presenter.drawRects(canvas);
            presenter.drawFrame(canvas);
            //TODO: добавить какую-нибудь милую анимацию
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final float eventX = event.getX();
            final float eventY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //проверяем не кнопочка ли
                    if (presenter.isBtn((int) eventX,(int) eventY)) {
                        Intent intent = new Intent(StartActivity.this, LevelsActivity.class);
                        startActivity(intent);
                    }
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
