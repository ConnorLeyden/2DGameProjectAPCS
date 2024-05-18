public class TestTerrainGenerator extends TerrainGenerator{
    @Override
    public void setTile(Chunk chunk, int localX, int localY) {
        int x = chunk.toGlobalPosX(localX);
        int y = chunk.toGlobalPosY(localY);

        TileType main = null;
        BackgroundTileType bg = null;

        if(x > 10 || y > 10) main = Tiles.REDDIRT;

        chunk.mainTilemap.setTile(localX, localY, main);
        chunk.backgroundTilemap.setTile(localX, localY, bg);
    }
    //    @Override
//    public LayeredTileType getTile(int x, int y) {
//        TileType main = null;
//        if(x > 10 || y > 10) main = Tiles.DIRT;
//
//        return new LayeredTileType(main, null);
//    }
}
