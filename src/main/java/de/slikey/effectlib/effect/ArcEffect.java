package de.slikey.effectlib.effect;

import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;

@SuppressWarnings({"unused"})
public class ArcEffect extends Effect {

    /**
     * Height of the arc in blocks
     */
    public float height = 2;

    /**
     * Particles per arc
     */
    public int particles = 100;

    /**
     * Internal counter
     */
    protected int step = 0;

    public ArcEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        particle = Particle.FLAME;
        period = 1;
        iterations = 200;
    }

    @Override
    public void reset() {
        step = 0;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        Location target = getTarget();

        if (target == null) {
            cancel();
            return;
        }

        if (location == null) {
            cancel();
            return;
        }

        Vector link = target.toVector().subtract(location.toVector());
        float length = (float) link.length();
        float pitch = (float) (4 * height / Math.pow(length, 2));

        Vector v;
        float x;
        float y;

        for (int i = 0; i < particles; i++) {
            v = link.clone().normalize().multiply(length * i / particles);
            x = ((float) i / particles) * length - length / 2;
            y = (float) (-pitch * Math.pow(x, 2) + height);

            location.add(v).add(0, y, 0);
            display(particle, location);
            location.subtract(0, y, 0).subtract(v);

            step++;
        }
    }

}
