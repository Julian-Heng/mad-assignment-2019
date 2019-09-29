package curtin.edu.citysim;

import java.security.KeyException;
import java.util.HashMap;
import java.util.Map;

public class Settings
{
    private Map<String, Integer> integerSettings = new HashMap<>();
    private Map<String, Double> doubleSettings = new HashMap<>();

    public Settings()
    {
        integerSettings.put("mapWidth", 50);
        integerSettings.put("mapHeight", 10);
        integerSettings.put("initialMoney", 1000);
        integerSettings.put("familySize", 4);
        integerSettings.put("shopSize", 6);
        integerSettings.put("salary", 10);
        doubleSettings.put("taxRate", 0.3);
        integerSettings.put("serviceCost", 2);
        integerSettings.put("houseBuildingCost", 100);
        integerSettings.put("commBuildingCost", 500);
        integerSettings.put("roadBuildingCost", 20);
    }

    public void setIntegerSetting(String k, int v) { integerSettings.put(k, v); }
    public void setDoubleSetting(String k, double v) { doubleSettings.put(k, v); }

    public int getIntegerSetting(String k, int defaultValue)
    {
        return integerSettings.containsKey(k) ? integerSettings.get(k) : defaultValue;
    }

    public int getIntegerSetting(String k) { return integerSettings.get(k); }

    public double getDoubleSetting(String k, double defaultValue)
    {
        return doubleSettings.containsKey(k) ? doubleSettings.get(k) : defaultValue;
    }

    public double getDoubleSetting(String k) { return doubleSettings.get(k); }
}
