package de.slikey.effectlib.math;

import org.bukkit.configuration.ConfigurationSection;

@SuppressWarnings({"unused"})
public class dQuadraticTransform implements Transform {

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
     * This returns the derivative, or velocity, of a quadratic equation at a specific step.
     * For a quadratic function:
     * f(x) = a(x+b)^2 + c(x+b) + d
     * f'(x) = 2a(x+b) + c
     * @param input a specific step
     * @return the derivative, or velocity, of a quadratic equation
     */
    @Override
    public double get(double input) {
        return  2 * a.get(input) * (input + b.get(input)) + c.get(input);
    }

}
