package darena13.puzzlefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by darena13 on 11.09.2017.
 */

public class PlayActivityPresenter implements PlayGround {
    private static final String TAG = "Play Presenter";

    private int numberOfRects;
    private Point dSize;
    private int rectSize;
    private int hintRectSize;
    private Rect[][] pictureRects;
    private PuzzleRects hintRects;
    private Paint[][] paints;
    private Paint[][] paintsToWin;

    @ColorInt
    private int bgColor;  //будем брать из настроек или из другого общего места

    private Rect[] rectsToRotate;

    private int[] rectsLeftsOnStart;
    private int[] rectsRightsOnStart;
    private int[] rectsTopsOnStart;
    private int[] rectsBottomsOnStart;
    private Paint[] paintsToRotate;

    private int yIndex;
    private int xIndex;

    private int movesPerfect;
    private int movesMade;
    private PuzzleRects movesRects;
    private Paint[][] movesPaints;

    PlayActivityPresenter(Context context) {
        //перенести всякую инициализацию в отдельный метод? или наделать сеттеров?
        this.numberOfRects = 7; //будем брать из настроек

        pictureRects = new Rect[numberOfRects][numberOfRects];
        paints = new Paint[numberOfRects][numberOfRects];
        paintsToWin = new Paint[numberOfRects][numberOfRects];

        hintRects = new PuzzleRects(numberOfRects, numberOfRects);
        movesRects = new PuzzleRects(numberOfRects, 1);
        movesPaints = new Paint[numberOfRects][1];


        for (int i = 0; i < paints.length; i++) {
            for (int j = 0; j < paints[i].length; j++) {
                paints[i][j] = new Paint();
            }
        }

        for (int i = 0; i < paintsToWin.length; i++) {
            for (int j = 0; j < paintsToWin[i].length; j++) {
                paintsToWin[i][j] = new Paint();
            }
        }

        for (int i = 0; i < movesPaints.length; i++) {
            for (int j = 0; j < movesPaints[i].length; j++) {
                movesPaints[i][j] = new Paint();
            }
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        dSize = new Point();
        display.getSize(dSize);
        rectSize = dSize.x / numberOfRects;
        hintRectSize = rectSize / 2;

        rectsToRotate = new Rect[numberOfRects * 3];

        rectsLeftsOnStart = new int[numberOfRects * 3];
        rectsRightsOnStart = new int[numberOfRects * 3];
        rectsTopsOnStart = new int[numberOfRects * 3];
        rectsBottomsOnStart = new int[numberOfRects * 3];

        paintsToRotate = new Paint[numberOfRects * 3];

        bgColor = 0xfffceee5;
    }

    //HORIZONTAL
    @Override
    public void hChoseLineToRotate(Point startPoint) {
        //вычисляем какую строчку двигать
        if (isItInBounds(startPoint)) {
            yIndex = startPoint.y / rectSize;
        } else {
            return;
        }
        Log.v(TAG, "yIndex = " + yIndex);
        Log.v(TAG, "startPoint = " + startPoint.x + " " + startPoint.y);
        //заполняем центральную часть копиями прямоугольников из исходной строчки
        for (int i = numberOfRects; i < numberOfRects * 2; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[yIndex][i - numberOfRects].left,
                    pictureRects[yIndex][i - numberOfRects].top,
                    pictureRects[yIndex][i - numberOfRects].right,
                    pictureRects[yIndex][i - numberOfRects].bottom
            );
        }
        //заполняем левую часть копиями прямоугольников из исходной строчки со смещением
        for (int i = 0; i < numberOfRects; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[yIndex][i].left - numberOfRects * rectSize,
                    pictureRects[yIndex][i].top,
                    pictureRects[yIndex][i].right - numberOfRects * rectSize,
                    pictureRects[yIndex][i].bottom
            );
        }
        //заполняем правую часть копиями прямоугольников из исходной строчки со смещением
        for (int i = numberOfRects * 2; i < numberOfRects * 3; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[yIndex][i - (numberOfRects * 2)].left + numberOfRects * rectSize,
                    pictureRects[yIndex][i - (numberOfRects * 2)].top,
                    pictureRects[yIndex][i - (numberOfRects * 2)].right + numberOfRects * rectSize,
                    pictureRects[yIndex][i - (numberOfRects * 2)].bottom
            );
        }
        //запоминаем исходные координаты прямоугольников
        for (int i = 0; i < rectsLeftsOnStart.length; i++) {
            rectsLeftsOnStart[i] = rectsToRotate[i].left;
        }
        for (int i = 0; i < rectsRightsOnStart.length; i++) {
            rectsRightsOnStart[i] = rectsToRotate[i].right;
        }
        //заполняем массив красок для прямоугольников
        System.arraycopy(paints[yIndex], 0, paintsToRotate, 0, paints[yIndex].length);
        System.arraycopy(paints[yIndex], 0, paintsToRotate, numberOfRects, paints[yIndex].length);
        System.arraycopy(paints[yIndex], 0, paintsToRotate, numberOfRects * 2, paints[yIndex].length);
        Log.v(TAG, "hLine is chosen");
    }

    @Override
    public void hSlowMove(Point startPoint, int eventX) {
        //расстояние по горизонтали
        int projOnX;
        if (isItInBounds(startPoint)) {
            projOnX = eventX - startPoint.x;
        } else {
            return;
        }
        //двигаем на это расстояние
        for (int i = 0; i < rectsToRotate.length; i++) {
            rectsToRotate[i].set(rectsLeftsOnStart[i] + projOnX, rectsToRotate[i].top, rectsRightsOnStart[i] + projOnX, rectsToRotate[i].bottom);
        }
    }

    @Override
    public void hPutRectsInPlaces(Point startPoint) {
        //выравниваем прямоугольники по сетке
        int shift;
        if (isItInBounds(startPoint)) {
            shift = rectsToRotate[numberOfRects * 3 - 1].left % rectSize;
        } else {
            return;
        }
        if (shift < rectSize / 2) {
            for (Rect rect : rectsToRotate) {
                rect.set(rect.left - shift, rect.top, rect.right - shift, rect.bottom);
            }
        } else {
            for (Rect rect : rectsToRotate) {
                rect.set(rect.left - shift + rectSize, rect.top, rect.right - shift + rectSize, rect.bottom);
            }
        }

        //копируем цвета видимых прямоугольников в основной массив
        int mostLeftIndex = 0;
        for (int i = 0; i < rectsToRotate.length; i++) {
            if (rectsToRotate[i].left == 0) {
                mostLeftIndex = i;
                Log.v(TAG, "mostLeftIndex = " + mostLeftIndex);
                break;
            }
        }
        System.arraycopy(paintsToRotate, mostLeftIndex, paints[yIndex], 0, numberOfRects);

        movesMade += 1;
    }

    //VERTICAL
    @Override
    public void vChoseLineToRotate(Point startPoint) {
        //вычисляем какой столбец двигать
        if (isItInBounds(startPoint)) {
            xIndex = startPoint.x / rectSize;
        } else {
            return;
        }
//        Log.v(TAG, "xIndex = " + xIndex);
//        Log.v(TAG, "startPoint = " + startPoint.x + " " + startPoint.y);
        //заполняем центральную часть копиями прямоугольников из исходной строчки
        for (int i = numberOfRects; i < numberOfRects * 2; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[i - numberOfRects][xIndex].left,
                    pictureRects[i - numberOfRects][xIndex].top,
                    pictureRects[i - numberOfRects][xIndex].right,
                    pictureRects[i - numberOfRects][xIndex].bottom
            );
        }
        //заполняем верхнюю часть копиями прямоугольников из исходного столбца со смещением
        for (int i = 0; i < numberOfRects; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[i][xIndex].left,
                    pictureRects[i][xIndex].top - numberOfRects * rectSize,
                    pictureRects[i][xIndex].right,
                    pictureRects[i][xIndex].bottom - numberOfRects * rectSize
            );
        }
        //заполняем нижнюю часть копиями прямоугольников из исходнго столбца со смещением
        for (int i = numberOfRects * 2; i < numberOfRects * 3; i++) {
            rectsToRotate[i] = new Rect(
                    pictureRects[i - (numberOfRects * 2)][xIndex].left,
                    pictureRects[i - (numberOfRects * 2)][xIndex].top + numberOfRects * rectSize,
                    pictureRects[i - (numberOfRects * 2)][xIndex].right,
                    pictureRects[i - (numberOfRects * 2)][xIndex].bottom + numberOfRects * rectSize
            );
        }
        //запоминаем исходные координаты прямоугольников
        for (int i = 0; i < rectsLeftsOnStart.length; i++) {
            rectsTopsOnStart[i] = rectsToRotate[i].top;
        }
        for (int i = 0; i < rectsRightsOnStart.length; i++) {
            rectsBottomsOnStart[i] = rectsToRotate[i].bottom;
        }
        //заполняем массив красок для прямоугольников //запихнуть в один цикл?
        for (int i = numberOfRects; i < numberOfRects * 2; i++) {
            paintsToRotate[i] = paints[i - numberOfRects][xIndex];
        }
        for (int i = 0; i < numberOfRects; i++) {
            paintsToRotate[i] = paints[i][xIndex];
        }
        for (int i = numberOfRects * 2; i < numberOfRects * 3; i++) {
            paintsToRotate[i] = paints[i - (numberOfRects * 2)][xIndex];
        }
        Log.v(TAG, "vLine is chosen");
    }


    @Override
    public void vSlowMove(Point startPoint, int eventY) {
        //расстояние по вертикали
        int projOnY;
        if (isItInBounds(startPoint)) {
            projOnY = eventY - startPoint.y;
        } else {
            return;
        }
        //двигаем на это расстояние
        for (int i = 0; i < rectsToRotate.length; i++) {
            rectsToRotate[i].set(rectsToRotate[i].left, rectsTopsOnStart[i] + projOnY, rectsToRotate[i].right, rectsBottomsOnStart[i] + projOnY);
        }
    }

    @Override
    public void vPutRectsInPlace(Point startPoint) {
        //выравниваем прямоугольники по сетке
        int shift;
        if (isItInBounds(startPoint)) {
            shift = rectsToRotate[numberOfRects * 3 - 1].top % rectSize;
        } else {
            return;
        }
        if (shift < rectSize / 2) {
            for (Rect rect : rectsToRotate) {
                rect.set(rect.left, rect.top - shift, rect.right, rect.bottom - shift);
            }
        } else {
            for (Rect rect : rectsToRotate) {
                rect.set(rect.left, rect.top - shift + rectSize, rect.right, rect.bottom - shift + rectSize);
            }
        }

        //копируем цвета видимых прямоугольников в основной массив
        int mostTopIndex = 0;
        for (int i = 0; i < rectsToRotate.length; i++) {
            if (rectsToRotate[i].top == 0) {
                mostTopIndex = i;
                Log.v(TAG, "mostTopIndex = " + mostTopIndex);
                break;
            }
        }
        for (int i = 0; i < numberOfRects; i++) {
            paints[i][xIndex] = paintsToRotate[i + mostTopIndex];
        }

        movesMade += 1;
    }

    //задаем координаты прямоугольников
    @Override
    public void setXYToRects() {
        for (int i = 0; i < pictureRects.length; i++) {
            for (int j = 0; j < pictureRects[i].length; j++) {
                pictureRects[i][j] = new Rect(
                        j * rectSize,
                        i * rectSize,
                        j * rectSize + rectSize,
                        i * rectSize + rectSize);
            }
        }
        hintRects.setXY(hintRectSize, 0, 0, rectSize * (numberOfRects + 1), hintRectSize * 3);
        movesRects.setXY(hintRectSize, 0, 0, rectSize * (numberOfRects + 1),  hintRectSize);
    }

    @Override
    public int[][] mixPuzzle(int index, int times) {
        Log.v(TAG, "mixPuzzle");
        int[][] colors = Utils.copyTwoDimArr(ColorSets.getPuzzle(index));
        Random ran = new Random();
        //сколько-то раз поворачиваем рандомную колонку и строчку ИЛИ строчку потом колонку на рандомное расстояние
        for (int i = 0; i < times; i++) {
            if (ran.nextBoolean()) {
                rotateColumn(colors);
                rotateLine(colors);
            } else {
                rotateLine(colors);
                rotateColumn(colors);
            }
        }
        movesPerfect = times;
        return colors;
    }

    private void rotateColumn(int[][] colors) {
        Random ran = new Random();
        int randomLine = ran.nextInt(numberOfRects);
        int randomDist = ran.nextInt(numberOfRects - 1) + 1;
        int[] line = new int[numberOfRects];
        int[] rotatedLine;
        //повернуть рандомную колонку на рандомное количество прямоугольников
        for (int i = 0; i < colors.length; i++) {
            line[i] = colors[i][randomLine];
        }
        rotatedLine = Utils.rotateArray(line, randomDist);
        for (int i = 0; i < colors.length; i++) {
            colors[i][randomLine] = rotatedLine[i];
        }
    }

    private void rotateLine(int[][] colors) {
        Random ran = new Random();
        int randomLine = ran.nextInt(numberOfRects);
        int randomDist = ran.nextInt(numberOfRects - 1) + 1;
        int[] line = new int[numberOfRects];
        int[] rotatedLine;
        //повернуть рандомный строчку на рандомное количество прямоугольников
        for (int i = 0; i < colors.length; i++) {
            line[i] = colors[randomLine][i];
        }
        rotatedLine = Utils.rotateArray(line, randomDist);
        for (int i = 0; i < colors.length; i++) {
            colors[randomLine][i] = rotatedLine[i];
        }
    }

    //прикрываем, что торчит
    @Override
    public void drawFrame(Canvas canvas) {
        Log.v(TAG, "drawFrame");
        Rect frameBottomRect = new Rect(0, numberOfRects * rectSize, dSize.x, dSize.y);
        Paint framePaint = new Paint();
        framePaint.setColor(bgColor);
        canvas.drawRect(frameBottomRect, framePaint);
    }

    //рисуем фон
    @Override
    public void drawBackground(Canvas canvas) {
        Log.v(TAG, "drawBG");
        Rect bgRect = new Rect(0, 0, dSize.x, dSize.y);
        Paint bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        canvas.drawRect(bgRect, bgPaint);
    }

    //проверяем, что нажатие в области пазла
    @Override
    public boolean isItInBounds(Point startPoint) {
        return startPoint.x < numberOfRects * rectSize && startPoint.y < numberOfRects * rectSize;
    }

    @Override
    public boolean isWin() {
        for (int i = 0; i < paints.length; i++) {
            for (int j = 0; j < paints[0].length; j++) {
                if (!(paints[i][j].getColor() == paintsToWin[i][j].getColor())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setColors(int puzzleIndex) {
        setColorsToPaintsToWin(puzzleIndex);
        setColorsToPaints(puzzleIndex);
        setColorsToMovesPaints();
    }

    //задаем правильные цвета для проверки на победу и для подсказки
    @Override
    public void setColorsToPaintsToWin(int index) {
        int[][] colors = ColorSets.getPuzzle(index);
        for (int i = 0; i < paintsToWin.length; i++) {
            for (int j = 0; j < paintsToWin[i].length; j++) {
                paintsToWin[i][j].setColor(colors[i][j]);
            }
        }
    }

    //задаем перемешанные цвета для пазла
    @Override
    public void setColorsToPaints(int index) {
        int[][] colors = mixPuzzle(index, 1); //rotate 1 time
        for (int i = 0; i < paints.length; i++) {
            for (int j = 0; j < paints[i].length; j++) {
                paints[i][j].setColor(colors[i][j]);
            }
        }
    }

    //задаем цвета для ходов
    @Override
    public void setColorsToMovesPaints() {
        int[][] colors = ColorSets.getPuzzle(1000);
        for (int i = 0; i < movesPaints.length; i++) {
            for (int j = 0; j < movesPaints[i].length; j++) {
                movesPaints[i][j].setColor(colors[i][j]);
            }
        }
    }

    //наконец, когда у всех прямоугольников есть координаты и цвет, рисуем все прямоугольники
    @Override
    public void drawRects(Canvas canvas) {
        //рисуем все прямоугольники из изначального массива
        for (int i = 0; i < pictureRects.length; i++) {
            for (int j = 0; j < pictureRects[i].length; j++) {
                canvas.drawRect(pictureRects[i][j], paints[i][j]);
            }
        }
        //рисуем выбранную линию (строчку или столбец)
        if (!(rectsToRotate[0] == null)) {
            for (int i = 0; i < rectsToRotate.length; i++) {
                canvas.drawRect(rectsToRotate[i], paintsToRotate[i]);
            }
        }
        //рисуем рамку, чтобы не торчало
        drawFrame(canvas);
        //рисуем подсказку
        hintRects.drawRects(canvas, paintsToWin);
        movesRects.drawRects(canvas, movesPaints, movesMade);
    }
}
