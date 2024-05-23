public class Tiles {
    public static final TileType DIRT = new TileType(
            new ConstantImageTileRenderer<>("./images/dirt_single.png"),
            new ConstantImageTileRenderer<>("./images/dirt_single_bg.png"),
            "Dirt"
    );

    public static final TileType REDDIRT = new TileType(
            new ConstantImageTileRenderer<>("./images/reddirt_single.png"),
            new ConstantImageTileRenderer<>("./images/reddirt_single_bg.png"),
            "Red Dirt"
    );
    public static final TileType STONE = new TileType(
            new ConstantImageTileRenderer<>("./images/stone_single.png"),
            new ConstantImageTileRenderer<>("./images/stone_single_bg.png"),
            "Stone"
    );

    public static final TileType BASALT = new TileType(
            new ConstantImageTileRenderer<>("./images/basalt_single.png"),
            new ConstantImageTileRenderer<>("./images/basalt_single_bg.png"),
            "Basalt"
    );


    public static final TileType TREASURE = new TileType(
            new ConstantImageTileRenderer<>("./images/treasure.png")
    );

    public static final TileType IRON = new TileType(
            new ConstantImageTileRenderer<>("./images/iron_single.png")
    );
}
