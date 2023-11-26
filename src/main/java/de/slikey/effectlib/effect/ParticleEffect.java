package de.slikey.effectlib.effect;

import org.bukkit.Particle;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;

@SuppressWarnings({"unused"})
public class ParticleEffect extends Effect {

    public ParticleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        particle = Particle.VILLAGER_ANGRY;
        period = 1;
        iterations = 1;
    }

    @Override
    public void onRun() {
        display(particle, getLocation());
    }
}
