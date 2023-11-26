package de.slikey.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.Particle;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.RandomUtils;

@SuppressWarnings({"unused"})
public class LoveEffect extends Effect {

    public LoveEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        particle = Particle.HEART;
        period = 2;
        iterations = 600;
    }

    @Override
    public void onRun() {
        Location location = getLocation();

        if (location == null) {
            cancel();
            return;
        }

        location.add(RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6d));
        location.add(0, RandomUtils.random.nextFloat() * 2, 0);
        display(particle, location);
    }

}
