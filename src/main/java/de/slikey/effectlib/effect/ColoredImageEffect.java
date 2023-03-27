package de.slikey.effectlib.effect;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.BaseImageEffect;

@SuppressWarnings({"unused"})
public class ColoredImageEffect extends BaseImageEffect {

    public ColoredImageEffect(EffectManager effectManager) {
        super(effectManager);
    }

    protected void display(BufferedImage image, Vector v, Location location, int pixel) {
        Color c = new Color(pixel);
        display(particle, location.add(v), org.bukkit.Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()));
        location.subtract(v);
    }

}
