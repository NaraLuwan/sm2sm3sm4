package com.luwan.github.sm;

import com.luwan.github.sm.sm.base.BaseUtil;
import org.junit.Test;

/**
 * 测试工具类。
 *
 * @author luwan
 * @date 2019/5/12
 */
public class UtilsTest {

    @Test
    public void unsignedBytes2UnsignedInt() {
        int times = 10;
        long start = System.currentTimeMillis();

        for (int i = 0; i < times; i++) {
            int[] ints = {i, i + 1, i + 2, i + 3};
            long l = BaseUtil.unsignedBytes2UnsignedInt(ints);
            System.out.println(l);
        }
        long end = System.currentTimeMillis();

        System.out.println("平均时间：" + (end - start) / times + "毫秒");
    }

}