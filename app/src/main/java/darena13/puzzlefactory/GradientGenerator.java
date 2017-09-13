package darena13.puzzlefactory;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by darena13 on 11.09.2017.
 */

public class GradientGenerator {
    static int[][] fourColors(int numberOfRects, @ColorInt int topLeft, @ColorInt int topRight, @ColorInt int bottomLeft, @ColorInt int bottomRight) {
        int[][] colors = new int[numberOfRects][numberOfRects];

        colors[0][0] = topLeft;
        colors[0][numberOfRects - 1] = topRight;
        colors[numberOfRects - 1][0] = bottomLeft;
        colors[numberOfRects - 1][numberOfRects - 1] = bottomRight;

        int blend = 255 / numberOfRects;
        for (int j = 1; j < numberOfRects - 1; j++) {
                colors[0][j] = Color.rgb(
                        Color.red(colors[0][j - 1]) + (Color.red(topRight) - Color.red(colors[0][j - 1])) * blend * j / 255,
                        Color.green(colors[0][j - 1]) + (Color.green(topRight) - Color.green(colors[0][j - 1])) * blend * j / 255,
                        Color.blue(colors[0][j - 1] + (Color.blue(topRight) - Color.blue(colors[0][j - 1])) * blend * j / 255)
                );

        }
        for (int j = 1; j < numberOfRects - 1; j++) {
            colors[numberOfRects - 1][j] = Color.rgb(
                    Color.red(colors[numberOfRects - 1][j - 1]) + (Color.red(bottomRight) - Color.red(colors[numberOfRects - 1][j - 1])) * blend * j / 255,
                    Color.green(colors[numberOfRects - 1][j - 1]) + (Color.green(bottomRight) - Color.green(colors[numberOfRects - 1][j - 1])) * blend * j / 255,
                    Color.blue(colors[numberOfRects - 1][j - 1] + (Color.blue(bottomRight) - Color.blue(colors[numberOfRects - 1][j - 1])) * blend * j / 255)
            );

        }
        for (int k = 0; k < numberOfRects; k++) {
            for (int j = 1; j < numberOfRects - 1; j++) {
                colors[j][k] = Color.rgb(
                        Color.red(colors[j - 1][k]) + (Color.red(colors[numberOfRects - 1][k]) - Color.red(colors[j - 1][k])) * blend * j / 255,
                        Color.green(colors[j - 1][k]) + (Color.green(colors[numberOfRects - 1][k]) - Color.green(colors[j - 1][k])) * blend * j / 255,
                        Color.blue(colors[j - 1][k] + (Color.blue(colors[numberOfRects - 1][k]) - Color.blue(colors[j - 1][k])) * blend * j / 255)
                );
            }
        }

        return colors;
    }

    static int[] oneColor(int numberOfRects, @ColorInt int topLeft) {
        int[] colors = new int[numberOfRects * numberOfRects];
        colors[0] = topLeft;
        for (int i = 1; i < colors.length; i++) {
            colors[i] = Color.rgb(
                    Color.red(colors[i - 1]) + (Color.red(Color.WHITE) - Color.red(colors[i - 1])) * 5 / 255,
                    Color.green(colors[i - 1]) + (Color.green(Color.WHITE) - Color.green(colors[i - 1])) * 5 / 255,
                    Color.blue(colors[i - 1] + (Color.blue(Color.WHITE) - Color.blue(colors[i - 1])) * 5 / 255)
            );
        }
        return colors;
    }
}
