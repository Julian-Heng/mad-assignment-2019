package curtin.edu.citysim;

import java.io.Serializable;
import java.security.KeyException;
import java.util.HashMap;
import java.util.Map;

public class Settings implements Serializable
{
    private Map<String, Object> settings = new HashMap<>();

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

    public void setIntSetting(String k, int v) { settings.put(k, v); }
    public void setDoubleSetting(String k, int v) { settings.put(k, v); }

    public int getIntSetting(String k, int fallback)
    {
        if (settings.containsKey(k) && settings.get(k) instanceof Integer && settings.get(k) != null)
        {
            return (Integer)settings.get(k);
        }

        return fallback;
    }

    public int getIntSettings(String k)
    {
        return (Integer)settings.get(k);
    }

    public double getDoubleSetting(String k, double fallback)
    {
        if (settings.containsKey(k) && settings.get(k) instanceof Double && settings.get(k) != null)
        {
            return (Double)settings.get(k);
        }

        return fallback;
    }

    public double getDoubleSetting(String k)
    {
        return (Double)settings.get(k);
    }

    public String toString()
    {
        String out = "{\n";

        for (Map.Entry<String, Object> e : settings.entrySet())
        {
            String k = e.getKey();
            Object v = e.getValue();

            if (v instanceof Integer)
                out += String.format("    \"%s\": \"%d\",\n", k, ((Integer)v).intValue());
            else if (v instanceof Double)
                out += String.format("    \"%s\": \"%f\",\n", k, ((Double)v).doubleValue());
        }

        return out.replaceAll(",\n$", "\n}");
    }
}
