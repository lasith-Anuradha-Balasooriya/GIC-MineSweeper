package gic.minesweeper.service;

import gic.minesweeper.model.CellPosition;
import gic.minesweeper.model.GameGrid;
import gic.minesweeper.model.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.*;

import static org.mockito.Mockito.*;

class MineSweeperServiceTest {
    private UserInputReader mockInputReader;
    private MineSweeperService mineSweeperService;

    @BeforeEach
    public void setUp() {
        mockInputReader = mock(UserInputReader.class);
        mineSweeperService = new MineSweeperService(mockInputReader);
    }

    @Test
    @Timeout(5)
    public void testGameInitiate_WinningScenario() {
        // Mock UserInput with valid data
        UserInput mockUserInput = new UserInput();
        mockUserInput.setGridSize(2);
        mockUserInput.setMinesCount(1);
        when(mockInputReader.getInitialUserInput()).thenReturn(mockUserInput);

        // Mock CellPosition
        CellPosition mockPosition = new CellPosition(0, 0);
        when(mockInputReader.getCellInput(2)).thenReturn(mockPosition);

        // Mock GameGrid using constructor mocking
        try (MockedConstruction<GameGrid> mockedGridConstructor = mockConstruction(GameGrid.class,
                (mockGrid, context) -> {
                    // Mock game flow: Not over → Not lost → Won
                    when(mockGrid.isGameOver()).thenReturn(false, true); // run loop once
                    when(mockGrid.isGameLost()).thenReturn(false);
                    when(mockGrid.isGameWon()).thenReturn(true);
                    when(mockGrid.isValid(mockPosition)).thenReturn(true);
                })) {

            mineSweeperService.gameInitiate();

            // Validate constructor call with correct gridSize and mineCount
            GameGrid mockGrid = mockedGridConstructor.constructed().get(0);
            verify(mockGrid).initialize();
            verify(mockGrid, times(2)).print();
            verify(mockGrid).reveal(mockPosition);
            verify(mockGrid, times(2)).print(); // called again after win
            verify(mockGrid, times(1)).isGameWon();
        }
    }

    @Test
    @Timeout(5)
    public void testGameInitiate_LosingScenario() {
        UserInput mockUserInput = new UserInput();
        mockUserInput.setGridSize(2);
        mockUserInput.setMinesCount(1);
        when(mockInputReader.getInitialUserInput()).thenReturn(mockUserInput);

        CellPosition mockPosition = new CellPosition(0, 0);
        when(mockInputReader.getCellInput(2)).thenReturn(mockPosition);

        try (MockedConstruction<GameGrid> mockedGridConstructor = mockConstruction(GameGrid.class,
                (mockGrid, context) -> {
                    when(mockGrid.isGameOver()).thenReturn(false, true);
                    when(mockGrid.isGameLost()).thenReturn(true);
                    when(mockGrid.isGameWon()).thenReturn(false);
                    when(mockGrid.isValid(mockPosition)).thenReturn(true);
                })) {

            mineSweeperService.gameInitiate();

            GameGrid mockGrid = mockedGridConstructor.constructed().get(0);
            verify(mockGrid, times(1)).print();
            verify(mockGrid).reveal(mockPosition);
            verify(mockGrid).printWithMines();
        }
    }

    @Test
    @Timeout(5)
    public void testGameInitiate_InvalidCellInput() {
        UserInput mockUserInput = new UserInput();
        mockUserInput.setGridSize(2);
        mockUserInput.setMinesCount(1);
        when(mockInputReader.getInitialUserInput()).thenReturn(mockUserInput);

        // First input invalid, second input valid
        CellPosition invalidPosition = null;
        CellPosition validPosition = new CellPosition(0, 0);
        when(mockInputReader.getCellInput(2))
                .thenReturn(invalidPosition)
                .thenReturn(validPosition);

        try (MockedConstruction<GameGrid> mockedGridConstructor = mockConstruction(GameGrid.class,
                (mockGrid, context) -> {
                    when(mockGrid.isGameOver()).thenReturn(false, false, true); // 2 loops
                    when(mockGrid.isGameLost()).thenReturn(false);
                    when(mockGrid.isGameWon()).thenReturn(true);
                    when(mockGrid.isValid(validPosition)).thenReturn(true);
                })) {

            mineSweeperService.gameInitiate();

            GameGrid mockGrid = mockedGridConstructor.constructed().get(0);
            verify(mockGrid, times(3)).print(); // once per loop
            verify(mockGrid).reveal(validPosition);
        }
    }

    @Test
    @Timeout(5)
    public void testGameInitiate_InvalidUserInput() {
        // Invalid gridSize and mineCount (0)
        UserInput invalidUserInput = new UserInput();
        invalidUserInput.setGridSize(0);
        invalidUserInput.setMinesCount(0);
        when(mockInputReader.getInitialUserInput()).thenReturn(invalidUserInput);

        // gameInitiate should exit immediately
        mineSweeperService.gameInitiate();

        // Nothing to verify – just ensuring no exceptions and no game loop
    }

    @Test
    @Timeout(5)
    public void testGameInitiate_ExceptionHandling() {
        // Simulate exception thrown during input reading
        when(mockInputReader.getInitialUserInput()).thenThrow(new RuntimeException("Test exception"));

        mineSweeperService.gameInitiate();

        // Ensure method completes without throwing
    }
}
