package gic.minesweeper.util;

import gic.minesweeper.model.CellPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MineSweeperUtilTest {

    // Tests for inputValidate(String userInput)
    @Test
    void testInputValidate_ValidInteger() {
        assertEquals(123, MineSweeperUtil.inputValidate("123"));
    }

    @Test
    void testInputValidate_NullInput() {
        assertEquals(0, MineSweeperUtil.inputValidate(null));
    }

    @Test
    void testInputValidate_EmptyString() {
        assertEquals(0, MineSweeperUtil.inputValidate("   "));
    }

    @Test
    void testInputValidate_InvalidInteger() {
        assertEquals(0, MineSweeperUtil.inputValidate("abc"));
    }

    @Test
    void testInputValidate_ExceptionInside() {
        // It's difficult to force an exception inside, but we already test catch with invalid parse
        // So this test is covered by invalid integer test.
        assertEquals(0, MineSweeperUtil.inputValidate("!@#"));
    }

    // Tests for converter(String input, int size)
    @Test
    void testConverter_ValidInput() {
        CellPosition expected = new CellPosition(0, 0); // A1 maps to row=0, col=0
        CellPosition actual = MineSweeperUtil.converter("A1", 4);
        assertEquals(expected, actual);
    }

    @Test
    void testConverter_InputLengthLessThan2() {
        assertNull(MineSweeperUtil.converter("A", 4));
        assertNull(MineSweeperUtil.converter("", 4));
        assertNull(MineSweeperUtil.converter(null, 4));  // NullPointerException possible? - your code doesn't handle null
    }

    @Test
    void testConverter_RowOutOfBounds() {
        assertNull(MineSweeperUtil.converter("Z1", 4)); // 'Z' far outside grid size
        assertNull(MineSweeperUtil.converter("@1", 4)); // char before 'A', negative row index
    }

    @Test
    void testConverter_ColumnOutOfBounds() {
        assertNull(MineSweeperUtil.converter("A0", 4)); // col index -1
        assertNull(MineSweeperUtil.converter("A5", 4)); // col index 4 (size 4 → max index 3)
    }

    @Test
    void testConverter_InvalidColumnNumberFormat() {
        assertNull(MineSweeperUtil.converter("A!", 4));
        assertNull(MineSweeperUtil.converter("Aabc", 4));
    }
}
