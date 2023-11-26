package de.slikey.effectlib.math;

import org.bukkit.configuration.ConfigurationSection;

@SuppressWarnings({"unused"})
public class dSinTransform implements Transform {

    private Transform a;
    private Transform b;
    private Transform c;

    @Override
    public void load(ConfigurationSection parameters) {
        a = Transforms.loadTransform(parameters, "a");
        b = Transforms.loadTransform(parameters, "b");
        c = Transforms.loadTransform(parameters, "c");
    }

    /**
     * This returns the derivative, or velocity, of a sin equation at a specific step.
     * For a sin function:
     * f(x) = a*sin(b(x+c)) + d
     * f'(x) = a*b*cos(b(x+c))
     * @param input a specific step
     * @return the derivative, or velocity, of a sin equation
     */
    @Override
    public double get(double input) {
        double bValue = b.get(input);
        return a.get(input) * bValue * Math.cos(bValue * (input + c.get(input)));
    }

}
