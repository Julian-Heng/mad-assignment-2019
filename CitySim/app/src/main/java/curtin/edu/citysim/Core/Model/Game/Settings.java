package curtin.edu.citysim.Core.Model.Game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Settings class to store settings for a GameData object
 */
public class Settings implements Serializable
{
    private Map<String,Number> settings = new HashMap<>();

    public Settings()
    {
        settings.put("mapWidth", 50);
        settings.put("mapHeight", 10);
        settings.put("initialMoney", 1000);
        settings.put("familySize", 4);
        settings.put("shopSize", 6);
        settings.put("salary", 10);
        settings.put("taxRate", 0.3);
        settings.put("serviceCost", 2);
        settings.put("houseBuildingCost", 100);
        settings.put("commBuildingCost", 500);
        settings.put("roadBuildingCost", 20);
    }

    /*
    public void setIntSetting(String k, int v) { settings.put(k, v); }
    public void setDoubleSetting(String k, double v) { settings.put(k, v); }
     */
    public void setSetting(String k, Number v) { settings.put(k, v); }

    /**
     * Fetch an integer setting or return fallback
     *
     * @param k setting key
     * @param fallback value to return if key is not found
     * @return setting value
     */
    public int getIntSetting(String k, int fallback)
    {
        try
        {
            return settings.getOrDefault(k, fallback).intValue();
        }
        catch (NullPointerException e)
        {
            return fallback;
        }
    }

    public int getIntSetting(String k) { return settings.get(k).intValue(); }

    /**
     * Fetch an double setting or return fallback
     *
     * @param k setting key
     * @param fallback value to return if key is not found
     * @return setting value
     */
    public double getDoubleSetting(String k, double fallback)
    {
        try
        {
            return settings.getOrDefault(k, fallback).doubleValue();
        }
        catch (NullPointerException e)
        {
            return fallback;
        }
    }

    public double getDoubleSetting(String k) { return settings.get(k).doubleValue(); }

    public String toString()
    {
        String out = "{\n";

        for (Map.Entry<String, ? super Number> e : settings.entrySet())
            out += String.format("    \"%s\": \"%s\",\n", e.getKey(), e.getValue().toString());

        return out.replaceAll(",\n$", "\n}");
    }
}
