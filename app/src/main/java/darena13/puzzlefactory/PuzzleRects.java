package darena13.puzzlefactory;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by darena13 on 08.10.2017.
 */

public class PuzzleRects {
    private Rect[][] rects;

    public PuzzleRects(int vNumberOfRects, int hNumberOfRects) {
        rects = new Rect[vNumberOfRects][hNumberOfRects];
    }

    public Rect[][] getRects() {
        return rects;
    }

    public void setXY(int rectSize, int vPuzzleSizeShift, int hPuzzleSizeShift, int vOffset, int hOffset) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                rects[i][j] = new Rect(
                        j * rectSize + hPuzzleSizeShift + hOffset, //left
                        i * rectSize + vPuzzleSizeShift + vOffset, // top
                        j * rectSize + rectSize + hPuzzleSizeShift + hOffset, // right
                        i * rectSize + rectSize + vPuzzleSizeShift + vOffset); // bottom
            }
        }
    }

    public int getTop() {
        return rects[0][0].top;
    }

    public int getBottom() {
        return rects[rects.length - 1][0].bottom;
    }

    public void vMove(int distY) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                rects[i][j].set(
                        rects[i][j].left, //left
                        rects[i][j].top + distY, // top
                        rects[i][j].right, // right
                        rects[i][j].bottom + distY); // bottom
            }
        }
    }

    void drawRects(Canvas canvas, Paint[][] paints) {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                canvas.drawRect(rects[i][j], paints[i][j]);
            }
        }
    }

    void drawRects(Canvas canvas, Paint[][] paints, int movesMade) {
        int vIndex;
        if (movesMade >= rects.length) {
            return;
            //пора рисовать красный квадрат RESET
        } else {
            vIndex = movesMade;
        }
        for (int i = rects.length - 1; i >= vIndex; i--) {
            for (int j = 0; j < rects[i].length; j++) {
                canvas.drawRect(rects[i][j], paints[i][j]);
            }
        }
    }

//    void drawRects(Canvas canvas, Paint[][] paints, int movesMade) {
//        int vIndex;
//        if (movesMade >= rects.length) {
//            vIndex = 0;
//        } else {
//            vIndex = rects.length - movesMade;
//        }
//        for (int i = rects.length - 1; i >= vIndex; i--) {
//            for (int j = 0; j < rects[i].length; j++) {
//                canvas.drawRect(rects[i][j], paints[i][j]);
//            }
//        }
//    }

    void hFillToRotate(Rect[] rectsToRotate, int numberOfRects, int yIndex, int rectSize) {
        //заполняем центральную часть копиями прямоугольников из исходной строчки
        for (int i = numberOfRects; i < numberOfRects * 2; i++) {
            rectsToRotate[i] = new Rect(
                    rects[yIndex][i - numberOfRects].left,
                    rects[yIndex][i - numberOfRects].top,
                    rects[yIndex][i - numberOfRects].right,
                    rects[yIndex][i - numberOfRects].bottom
            );
        }
        //заполняем левую часть копиями прямоугольников из исходной строчки со смещением
        for (int i = 0; i < numberOfRects; i++) {
            rectsToRotate[i] = new Rect(
                    rects[yIndex][i].left - numberOfRects * rectSize,
                    rects[yIndex][i].top,
                    rects[yIndex][i].right - numberOfRects * rectSize,
                    rects[yIndex][i].bottom
            );
        }
        //заполняем правую часть копиями прямоугольников из исходной строчки со смещением
        for (int i = numberOfRects * 2; i < numberOfRects * 3; i++) {
            rectsToRotate[i] = new Rect(
                    rects[yIndex][i - (numberOfRects * 2)].left + numberOfRects * rectSize,
                    rects[yIndex][i - (numberOfRects * 2)].top,
                    rects[yIndex][i - (numberOfRects * 2)].right + numberOfRects * rectSize,
                    rects[yIndex][i - (numberOfRects * 2)].bottom
            );
        }
    }

    void vFillToRotate(Rect[] rectsToRotate, int numberOfRects, int xIndex, int rectSize) {
        //заполняем центральную часть копиями прямоугольников из исходного столбца
        for (int i = numberOfRects; i < numberOfRects * 2; i++) {
            rectsToRotate[i] = new Rect(
                    rects[i - numberOfRects][xIndex].left,
                    rects[i - numberOfRects][xIndex].top,
                    rects[i - numberOfRects][xIndex].right,
                    rects[i - numberOfRects][xIndex].bottom
            );
        }
        //заполняем верхнюю часть копиями прямоугольников из исходного столбца со смещением
        for (int i = 0; i < numberOfRects; i++) {
            rectsToRotate[i] = new Rect(
                    rects[i][xIndex].left,
                    rects[i][xIndex].top - numberOfRects * rectSize,
                    rects[i][xIndex].right,
                    rects[i][xIndex].bottom - numberOfRects * rectSize
            );
        }
        //заполняем нижнюю часть копиями прямоугольников из исходнго столбца со смещением
        for (int i = numberOfRects * 2; i < numberOfRects * 3; i++) {
            rectsToRotate[i] = new Rect(
                    rects[i - (numberOfRects * 2)][xIndex].left,
                    rects[i - (numberOfRects * 2)][xIndex].top + numberOfRects * rectSize,
                    rects[i - (numberOfRects * 2)][xIndex].right,
                    rects[i - (numberOfRects * 2)][xIndex].bottom + numberOfRects * rectSize
            );
        }

    }
}
