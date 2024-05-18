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
            new ConstantImageTileRenderer<>("./images/dirt_single.png"),
            new ConstantImageTileRenderer<>("./images/dirt_single_bg.png"),
            "Basalt"
    );
}
