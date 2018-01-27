public class Default{

    // Game
    public static final String gameMainTitle = "Star Wars";
    public static final String gameSubTitle = "Game of Cards";
    public static final String gameTitle = gameMainTitle + ": " + gameSubTitle;

    // GridPane
    public static final int gridGap = 4;
    public static final int gridPadding = 10;

    // Player
    public static final int handSize = 5;

    // GUICard
    public static final double cardWidth = 160;
    public static final int cardColumns = 4;
    public static final int cardGapTotal = gridGap * (cardColumns - 1);
    public static final double cardColumnWidth = (cardWidth - cardGapTotal) / cardColumns;
    public static final String cardBGColor = "#ffff";

    // GUIArena components
    public static final double arenaWidth = (cardWidth + cardGapTotal + (gridPadding * 2)) * handSize;
    public static final double arenaColumnWidth = (arenaWidth - cardGapTotal) / handSize;

    // Text
    public static final String font = "Tahoma";

}
