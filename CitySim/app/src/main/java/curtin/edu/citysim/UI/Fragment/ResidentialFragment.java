package curtin.edu.citysim.UI.Fragment;

import java.util.List;

import curtin.edu.citysim.Core.Model.Residential;
import curtin.edu.citysim.Core.Model.Structure;

public class ResidentialFragment extends SelectorFragment
{
    public ResidentialFragment(List<Residential> structures) { super(structures); }
    @Override public String getLabel() { return "Residential"; }
}
