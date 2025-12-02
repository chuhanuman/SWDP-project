package tile;

import spreader.Spreader;

/**
 Default implementation of a Tile in the simulation grid.
 Represents a basic tile with defense level, resources, and optional spreader.
 */
public class DefaultTile implements Tile {
    private final int defenseLevel;
    private int resources;
    private Spreader spreader;
    private final int maxResources;

    /**
     * Constructor for a tile with a spreader (occupied tile).
     * 
     * @param defenseLevel How difficult it is for aliens to spread here
     * @param resources Initial resources available on this tile
     * @param spreader The alien spreader occupying this tile
     * @param maxResources Maximum resources this tile can hold
     */
    public DefaultTile(int defenseLevel, int resources, Spreader spreader, int maxResources) {
        this.defenseLevel = defenseLevel;
        this.resources = resources;
        this.spreader = spreader;
        this.maxResources = maxResources;
    }

    /**
     * Constructor for an empty tile (no spreader).
     * 
     * @param defenseLevel How difficult it is for aliens to spread here
     * @param resources Initial resources available on this tile
     */
    public DefaultTile(int defenseLevel, int resources) {
        this(defenseLevel, resources, null, resources);
    }

    @Override
    public int getDefenseLevel() {
        return defenseLevel;
    }

    @Override
    public int getResources() {
        return resources;
    }

    @Override
    public void setResources(int resources) {
        this.resources = Math.min(resources, maxResources);
    }

    @Override
    public int getMaxResources() {
        return maxResources;
    }

    @Override
    public Spreader getSpreader() {
        return spreader;
    }

    @Override
    public void setSpreader(Spreader spreader) {
        this.spreader = spreader;
    }

    @Override
    public boolean hasSpreader() {
        return spreader != null;
    }

    @Override
    public void consumeResources(int amount) {
        this.resources = Math.max(0, this.resources - amount);
    }

    @Override
    public void addResources(int amount) {
        this.resources = Math.min(this.maxResources, this.resources + amount);
    }

    @Override
    public String toString() {
        return String.format("DefaultTile[defense=%d, resources=%d/%d, occupied=%b]",
                defenseLevel, resources, maxResources, hasSpreader());
    }
}