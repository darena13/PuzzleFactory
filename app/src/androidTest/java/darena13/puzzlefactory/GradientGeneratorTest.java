package darena13.puzzlefactory;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by darena13 on 11.09.2017.
 */

public class GradientGeneratorTest {
    @Test
    public void oneColorTest() throws Exception {
        assertArrayEquals(new int[][]{{0xfffb9908, 0xff177a27}, {0xffd34989, 0xff1c4cb9}}, GradientGenerator.fourColors(2, 0xfffb9908, 0xff177a27, 0xffd34989, 0xff1c4cb9));
    }
}
