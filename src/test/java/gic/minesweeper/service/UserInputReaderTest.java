package gic.minesweeper.service;

import gic.minesweeper.model.CellPosition;
import gic.minesweeper.model.UserInput;
import gic.minesweeper.util.MineSweeperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInputReaderTest {

    @Test
    @Timeout(5)
    public void testGetInitialUserInput_ValidFlow() {
        String input = "4\n4\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        try (MockedStatic<MineSweeperUtil> mockedUtil = mockStatic(MineSweeperUtil.class)) {
            mockedUtil.when(() -> MineSweeperUtil.inputValidate("4")).thenReturn(4);

            UserInputReader reader = new UserInputReader(testScanner);
            UserInput result = reader.getInitialUserInput();

            assertEquals(4, result.getGridSize());
            assertEquals(4, result.getMinesCount());
        }
    }
    @Test
    @Timeout(5)
    public void testGetCellInput_ValidInput() {
        // Simulate input: "B2\n" (Expect row=1, col=1)
        String input = "B2\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        // Mock static method converter
        try (MockedStatic<MineSweeperUtil> mockedUtil = mockStatic(MineSweeperUtil.class)) {
            mockedUtil.when(() -> MineSweeperUtil.converter("B2", 4))
                    .thenReturn(new CellPosition(1, 1));

            UserInputReader reader = new UserInputReader(testScanner);
            CellPosition result = reader.getCellInput(4);

            assertNotNull(result);
            assertEquals(1, result.row());
            assertEquals(1, result.col());

            // Confirm converter was called
            mockedUtil.verify(() -> MineSweeperUtil.converter("B2", 4), times(1));
        }
    }

    @Test
    @Timeout(5)
    public void testGetCellInput_ExceptionHandling() {
        // Simulate input: "invalid\n"
        String input = "invalid\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        try (MockedStatic<MineSweeperUtil> mockedUtil = mockStatic(MineSweeperUtil.class)) {
            // Simulate exception from converter
            mockedUtil.when(() -> MineSweeperUtil.converter("invalid", 4))
                    .thenThrow(new RuntimeException("Invalid input format"));

            UserInputReader reader = new UserInputReader(testScanner);
            CellPosition result = reader.getCellInput(4);

            assertNull(result); // Should catch exception and return null
        }
    }
}
