public class OverworldTerrainGenerator extends TerrainGenerator {
    private final Noise mainNoise;
    private final Noise caveNoise;
    private final double yCoefficient = 1;
    private final double caveWidth = 2;
    private final double caveDepthStart = 10;
    private final double caveDepthEnd = 30;
    private int caveMultiplier = 1;
    public final int seed;

    public OverworldTerrainGenerator(int seed){
        this.seed = seed;

        mainNoise = new CompositeNoise(
                1, 1,
                new ValueNoise(0.1, 5, StaticRandom.getRandom(seed, 0)),
                new ValueNoise(0.005, 15, StaticRandom.getRandom(seed, 1)),
                new ValueNoise(0.01, 30, StaticRandom.getRandom(seed, 2))
        );

        caveNoise = new ValueNoise(0.1,10, StaticRandom.getRandom(seed, 3));
    }

    @Override
    public void setTile(Chunk chunk, int localX, int localY) {
        TileType main = null;
        BackgroundTileType bg = null;

        int x = chunk.toGlobalPosX(localX);
        int y = chunk.toGlobalPosY(localY);

        double geologicalDepth = (y*yCoefficient) + mainNoise.sample(x, y);

        if(geologicalDepth > 0) {
            main = Tiles.REDDIRT;
            bg = Tiles.REDDIRT.backgroundType;
        }
        if(geologicalDepth > 5) {
            main = Tiles.STONE;
            bg = Tiles.STONE.backgroundType;
        }
        if (geologicalDepth > 200) {
            main = Tiles.BASALT;
            bg = Tiles.BASALT.backgroundType;
        }

        if (geologicalDepth > 200) {
            caveMultiplier = 2;
        } else {
            caveMultiplier = 1;
        }
        double caveValue = caveNoise.sample(x, y);
        double caveThreshold = caveMultiplier * Util.invLerp(caveDepthStart, caveDepthEnd, geologicalDepth);

        if(caveValue > -caveThreshold && caveValue < caveThreshold){
            main = null;
        }

        chunk.mainTilemap.setTile(localX, localY, main);
        chunk.backgroundTilemap.setTile(localX, localY, bg);
    }
}
