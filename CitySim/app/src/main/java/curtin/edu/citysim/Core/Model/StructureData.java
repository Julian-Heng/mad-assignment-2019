package curtin.edu.citysim.Core.Model;

import android.provider.ContactsContract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import curtin.edu.citysim.R;

public class StructureData
{
    private List<Residential> residential = Arrays.asList(new Residential[] {
        new Residential(R.drawable.ic_building1),
        new Residential(R.drawable.ic_building2),
        new Residential(R.drawable.ic_building3),
        new Residential(R.drawable.ic_building4)
    });

    private List<Commercial> commercial = Arrays.asList(new Commercial[] {
        new Commercial(R.drawable.ic_building5),
        new Commercial(R.drawable.ic_building6),
        new Commercial(R.drawable.ic_building7),
        new Commercial(R.drawable.ic_building8)
    });

    private List<Road> road = Arrays.asList(new Road[] {
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
    });

    public Residential getResidential(int i) { return residential.get(i); }
    public Commercial getCommercial(int i) { return commercial.get(i); }
    public Road getRoad(int i) { return road.get(i); }
}
