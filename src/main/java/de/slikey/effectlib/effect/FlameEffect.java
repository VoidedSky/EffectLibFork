package de.slikey.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.RandomUtils;

@SuppressWarnings({"unused"})
public class FlameEffect extends Effect {

    public int particles = 10;

    public FlameEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        particle = Particle.FLAME;
        period = 1;
        iterations = 600;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        Vector v;

        if (location == null) {
            cancel();
            return;
        }

        for (int i = 0; i < particles; i++) {
            v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6D);
            v.setY(RandomUtils.random.nextFloat() * 1.8);
            location.add(v);
            display(particle, location);
            location.subtract(v);
        }
    }

}
