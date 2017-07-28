package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button button;
    LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        final DrawView drawView = new DrawView(this);
        setContentView(drawView);

//        drawView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawView.changeColor();
//            }
//        });

        button = new Button(getApplicationContext());
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw (Canvas canvas) {
            canvas.drawColor(Color.GREEN);
            addContentView(button, layoutParams);
        }

        void changeColor (Canvas canvas) {
            canvas.drawColor(Color.RED);
        }
    }
}
