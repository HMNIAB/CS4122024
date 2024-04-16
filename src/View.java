public class View {
    GameWindow gameWindow;

    public View() {
        gameWindow = new GameWindow();
        gameWindow.open(); // TODO: move this call elsewhere once login window exists
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
