package de.slikey.effectlib.math;

import org.bukkit.configuration.ConfigurationSection;

@SuppressWarnings({"unused"})
public class EchoTransform implements Transform {

    @Override
    public void load(ConfigurationSection parameters) {
    }

    @Override
    public double get(double input) {
        return input;
    }

}
