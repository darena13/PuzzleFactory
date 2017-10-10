package darena13.puzzlefactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
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
    int puzzleIndex; //получим из интента
    DialogFragment winDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            puzzleIndex = bundle.getInt("SELECTED_PUZZLE");
            Log.v(TAG, "puzzleIndex = " + puzzleIndex);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new PlayActivityPresenter(getApplicationContext());

        final DrawView drawView = new DrawView(this);
        setContentView(drawView);

        winDialog = new WinDialog();

        //TODO: добавить кнопку "сбросить"
        //TODO: добавить подсчет ходов
        presenter.setPaintsToWin(puzzleIndex);
        presenter.setColorsToPaints(puzzleIndex);
        presenter.setXYToRects();
    }

    class DrawView extends View {
        Point startPoint = new Point(0, 0);
        Direction direction = null;

        public DrawView(Context context) {
            super(context);
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
                    //запоминаем координаты начала вектора
                    startPoint.set((int) eventX, (int) eventY);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //проекции вектора на оси
                    int dx = (int) eventX - startPoint.x;
                    int dy = (int) eventY - startPoint.y;
                    //если уже есть направление вектора, двигаем столбец/строку
                    if (!(direction == null)) {
                        switch (direction) {
                            case HRZ:
                                presenter.hSlowMove(startPoint, (int) eventX);
                                break;
                            case VRT:
                                presenter.vSlowMove(startPoint, (int) eventY);
                                break;
                        }
                        //если вектор достаточно длинный, 1) опредеяем направление 2) опредеяем какой столбец/строку двигать
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
                    //ставим прямоугольники на место
                    if (direction == Direction.HRZ) {
                        presenter.hPutRectsInPlaces(startPoint);
                    } else if (direction == Direction.VRT) {
                        presenter.vPutRectsInPlace(startPoint);
                    }
                    //обнуляем направление вектора
                    direction = null;
                    //проверка на победу
                    if (presenter.isWin()) {
                        Log.v(TAG, "WIN!");
//                        winDialog.show(getFragmentManager(), "winDialog");
                        Intent intent = new Intent(PlayActivity.this, WinActivity.class);
                        intent.putExtra("PUZZLE", puzzleIndex);
                        startActivity(intent);
                        //TODO: няшную анимацию - пусть там котик с радугой пролетает или чё
                        //TODO: показывать результат (ходов) и насколько это круто
                        //TODO: фиксируем, что пазл собран (в активити с уровнями это будет видно)
                    }
                    //TODO: сохраняем прогресс текущего пазла

                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }
    }
}
