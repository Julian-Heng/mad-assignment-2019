package curtin.edu.citysim;

public class StructureData
{
    private StructureData instance;
    private Residential[] residential;
    private Commercial[] commercial;
    private Road[] roads;

    public StructureData getInstance() { return (instance = (instance != null ? instance : new StructureData())); }

    public void setResidential(Residential[] residential) { this.residential = residential; }
    public void setCommercial(Commercial[] commercial) { this.commercial = commercial; }
    public void setRoads(Road[] roads) { this.roads = roads; }

    public Residential[] getResidential() { return residential; }
    public Commercial[] getCommercial() { return commercial; }
    public Road[] getRoads() { return roads; }
}
