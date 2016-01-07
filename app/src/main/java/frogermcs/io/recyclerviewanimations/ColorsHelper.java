package frogermcs.io.recyclerviewanimations;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Miroslaw Stanek on 06.01.2016.
 */
public class ColorsHelper {
    //Some randomly picked colors from SDK's color.xml
    public static final List<Integer> COLORS = Arrays.asList(
            0xff00bcd4, 0xff3f51b5, 0xff4285f4, 0xffe91e63, 0xff0f9d58, 0xff8bc34a, 0xffff9800,
            0xffff5722, 0xff9e9e9e, 0xff00796b, 0xff00695c, 0xff3367d6, 0xff2a56c6, 0xff303f9f,
            0xff283593, 0xff7b1fa2, 0xff6a1b9a, 0xffc2185b, 0xff00bcd4, 0xff3f51b5, 0xff4285f4,
            0xffe91e63, 0xff0f9d58, 0xff8bc34a, 0xffff9800, 0xffff5722, 0xff9e9e9e, 0xff00796b,
            0xff00695c, 0xff3367d6, 0xff2a56c6, 0xff303f9f, 0xff283593, 0xff7b1fa2, 0xff6a1b9a,
            0xffc2185b, 0xff00bcd4, 0xff3f51b5, 0xff4285f4, 0xffe91e63, 0xff0f9d58, 0xff8bc34a,
            0xffff9800, 0xffff5722, 0xff9e9e9e, 0xff00796b, 0xff00695c, 0xff3367d6, 0xff2a56c6,
            0xff303f9f, 0xff283593, 0xff7b1fa2, 0xff6a1b9a, 0xffc2185b
    );

    public static Integer getRandomColor() {
        return COLORS.get(new Random().nextInt(COLORS.size()));
    }
}
