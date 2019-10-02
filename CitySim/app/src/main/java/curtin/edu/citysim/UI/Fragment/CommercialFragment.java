package curtin.edu.citysim.UI.Fragment;

import java.util.List;

import curtin.edu.citysim.Core.Model.Commercial;
import curtin.edu.citysim.Core.Model.Structure;

public class CommercialFragment extends SelectorFragment
{
    public CommercialFragment(List<Commercial> structures) { super(structures); }
    @Override public String getLabel() { return "Commercial"; }
}
