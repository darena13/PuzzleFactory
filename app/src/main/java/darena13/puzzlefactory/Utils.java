package darena13.puzzlefactory;

/**
 * Created by darena13 on 21.09.2017.
 */

public class Utils {
    public static int[] rotateArray(final int[] array, final int rIndex) {
        if (array == null || array.length <= 1) {
            return new int[0];
        }

        final int[] result = new int[array.length];
        final int arrayLength = array.length;

        for (int i = 0; i < arrayLength; i++) {
            int nIndex = (i + rIndex) % arrayLength;
            result[nIndex] = array[i];
        }
        return result;
    }
}
