package com.mimas.bowling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mimas on 30.04.2017.
 */
class GameHelperTest {

    @Test

    void validateInputNumbersTest() {
        Throwable ex0 = assertThrows(IllegalArgumentException.class, () -> {
            GameHelper.validateAndGetFramesNumber(new int[]{12,2,1,1});
        });
        assertEquals("all values must be less than 10",ex0.getMessage());


        Throwable ex1 = assertThrows(IllegalArgumentException.class, () -> {
            // two strikes too close
            GameHelper.validateAndGetFramesNumber(new int[]{10,2,10,1,1});
        });
        assertEquals("wrong number of pins in attempt within frame. Max sum of all attempts is 10",ex1.getMessage());

        Throwable ex2 = assertThrows(IllegalArgumentException.class, () -> {
            // last item has no pair
            GameHelper.validateAndGetFramesNumber(new int[]{10,10,4,4,3});
        });
        assertEquals("wrong number of attempts in frame",ex2.getMessage());

        Throwable ex3 = assertThrows(IllegalArgumentException.class, () -> {
            // 4+7 = 11
            GameHelper.validateAndGetFramesNumber(new int[]{10,10,4,7,3});
        });
        assertEquals("wrong number of pins in attempt within frame. Max sum of all attempts is 10",ex3.getMessage());

    }

    @Test
    void framesCountLess10Test() {
        assertEquals(3, GameHelper.validateAndGetFramesNumber(new int[]{10,2,1,1,2}));
        assertEquals(4, GameHelper.validateAndGetFramesNumber(new int[]{10,2,8,1,2,10}));
        assertEquals(3, GameHelper.validateAndGetFramesNumber(new int[]{10,0,0,10}));

    }

    @Test
    void framesCounts10Test() {
        assertEquals(10, GameHelper.validateAndGetFramesNumber(new int[]{1,4,4,5,6,4,5,5,10,0,1,7,3,6,4,10,2,8,6}));
        assertEquals(10, GameHelper.validateAndGetFramesNumber(new int[]{10,10,10,10,10,10,10,10,10,10,10,10}));

        assertEquals(10, GameHelper.validateAndGetFramesNumber(new int[]{9,0,3,5,6,1,3,6,8,1,5,3,2,5,8,0,7,1,8,1}));
        assertEquals(10, GameHelper.validateAndGetFramesNumber(new int[]{9,0,3,7,6,1,3,7,8,1,5,5	,0,10,8,0,7,3,8,2,8}));
    }


    @Test
    void convertProtocolLengthTest() {
        // 21 = 9 x 2  + 3
        assertEquals(21, GameHelper.convertProtocol(new String[]{"9","0","3","/","6","1","3",
                "/","8","1","5","/","0","/","8","0","7","/","8","/","8"}).length);

        assertEquals(17, GameHelper.convertProtocol(new String[]{"X","3","/",
                "6","1","X","X","X","2","/","9","0","7","/","X","X","X"}).length);

    }

    @Test
    void convertProtocolValuesTest() {
        assertArrayEquals(new int[]{9,0,3,7,6,1,3,7,8,1,5,5,0,10,8,0,7,3,8,2,8},
                GameHelper.convertProtocol(new String[]{"9","0","3","/","6","1","3",
                "/","8","1","5","/","0","/","8","0","7","/","8","/","8"}));

        assertArrayEquals(new int[]{10,3,7,6,1,10,10,10,2,8,9,0,7,3,10,10,10},
                GameHelper.convertProtocol(new String[]{"X","3","/",
                "6","1","X","X","X","2","/","9","0","7","/","X","X","X"}));
   }

    @Test
    void invalidConvertProtocolTest() {

        Throwable ex0 = assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(17, GameHelper.convertProtocol(new String[]{"X","3","/",
                    "6","1","X","X","X","2","/","/","0","7","/","X","X","X"}).length);
        });
        assertEquals("Spare cannot be after spare",ex0.getMessage());

        Throwable ex1 = assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(17, GameHelper.convertProtocol(new String[]{"/","3","/",
                    "6","1","X","X","X","2","/","8","0","7","/","X","X","X"}).length);
        });
        assertEquals("Spare cannot be first in a series",ex1.getMessage());

        Throwable ex2 = assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(17, GameHelper.convertProtocol(new String[]{"5","3","/",
                    "6","1","X","X","X","/","2","8","0","7","/","X","X","X"}).length);
        });
        assertEquals("Spare cannot be after strike",ex2.getMessage());

    }

}