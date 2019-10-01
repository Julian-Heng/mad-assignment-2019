package curtin.edu.citysim;

public class StructureData
{
    public static final Residential[] residential = new Residential[] {
        new Residential(R.drawable.ic_building1),
        new Residential(R.drawable.ic_building2),
        new Residential(R.drawable.ic_building3),
        new Residential(R.drawable.ic_building4)
    };

    public static final Commercial[] commercial = new Commercial[] {
        new Commercial(R.drawable.ic_building5),
        new Commercial(R.drawable.ic_building6),
        new Commercial(R.drawable.ic_building7),
        new Commercial(R.drawable.ic_building8)
    };

    public static final Road[] roads = new Road[] {
        new Road(R.drawable.ic_road_e),
        new Road(R.drawable.ic_road_ew),
        new Road(R.drawable.ic_road_n),
        new Road(R.drawable.ic_road_ne),
        new Road(R.drawable.ic_road_new),
        new Road(R.drawable.ic_road_ns),
        new Road(R.drawable.ic_road_nse),
        new Road(R.drawable.ic_road_nsew),
        new Road(R.drawable.ic_road_nsw),
        new Road(R.drawable.ic_road_nw),
        new Road(R.drawable.ic_road_s),
        new Road(R.drawable.ic_road_se),
        new Road(R.drawable.ic_road_sew),
        new Road(R.drawable.ic_road_sw),
        new Road(R.drawable.ic_road_w)
    };

    public Residential[] getResidential() { return residential; }
    public Commercial[] getCommercial() { return commercial; }
    public Road[] getRoads() { return roads; }
}
