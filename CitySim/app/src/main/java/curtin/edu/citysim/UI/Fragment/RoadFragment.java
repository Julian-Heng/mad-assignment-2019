package curtin.edu.citysim.UI.Fragment;

import java.util.List;

import curtin.edu.citysim.Core.Model.Road;
import curtin.edu.citysim.Core.Model.Structure;

public class RoadFragment extends SelectorFragment
{
    public RoadFragment(List<Road> structures) { super(structures); }
    @Override public String getLabel() { return "Road"; }
}
