package de.lifelogr.translator.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author marco
 */
public class CommandParams
{
    private String name;
    private List<String> params;

    public CommandParams(String name, String... params)
    {
        this.name = name;
        this.params = new ArrayList<>();

        if (params != null) {
            Arrays.stream(params).forEach((param) -> {
                this.params.add(param);
            });
        }
    }

    public String getName()
    {
        return name;
    }

    public List<String> getParams()
    {
        return params;
    }
}
