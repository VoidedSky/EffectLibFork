package de.slikey.effectlib.effect;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.RandomUtils;

@SuppressWarnings({"unused"})
public class TornadoEffect extends Effect {

    /*
     * Tornado particle
     */
    public Particle tornadoParticle = Particle.FLAME;
    public Color tornadoColor = null;

    /*
     * Particle of the cloud
     */
    public Particle cloudParticle = Particle.CLOUD;
    public Color cloudColor = null;
    public float cloudSpeed = 0;

    /*
     * Size of the cloud
     */
    public float cloudSize = 2.5F;

    /*
     * Y-Offset from location
     */
    public double yOffset = 0.8;

    /*
     * Height of the Tornado
     */
    public float tornadoHeight = 5F;

    /*
     * Max radius of the Tornado
     */
    public float maxTornadoRadius = 5F;

    /*
     * Should the cloud appear?
     */
    public boolean showCloud = true;

    /*
     * Should the tornado appear?
     */
    public boolean showTornado = true;

    /*
     * Distance between each row
     */
    public double distance = 0.375;

    /*
     * Number of particles per circle
     */
    public int circleParticles = 64;

    /*
     * Number of particles in the cloud
     */
    public int cloudParticles = 100;

    /*
     * Amount of y-jitter between circle particles
     */
    public double circleHeight = 0;

    /*
     * Internal counter
     */
    protected int step = 0;

    public TornadoEffect(EffectManager manager) {
        super(manager);
        type = EffectType.REPEATING;
        period = 5;
        iterations = 20;
    }

    @Override
    public void reset() {
        step = 0;
    }

    @Override
    public void onRun() {
        Location l = getLocation().add(0, yOffset, 0);
        Vector vector;

        for (int i = 0; i < (cloudParticles * cloudSize); i++) {
            vector = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * cloudSize);
            if (showCloud) {
                display(cloudParticle, l.add(vector), cloudColor, cloudSpeed, 1);
                l.subtract(vector);
            }
        }

        Location t = l.clone().add(0, 0.2, 0);
        double r = 0.45 * (maxTornadoRadius * (2.35 / tornadoHeight));
        double fr;

        for (double y = 0; y < tornadoHeight; y += distance) {
            fr = r * y;
            if (fr > maxTornadoRadius) fr = maxTornadoRadius;

            for (Vector v : createCircle(y, fr)) {
                if (showTornado) {
                    if (circleHeight > 0) v.setY(v.getY() + RandomUtils.random.nextDouble() * circleHeight / 2 - circleHeight / 2);
                    display(tornadoParticle, t.add(v), tornadoColor);
                    t.subtract(v);
                    step++;
                }
            }
        }
        l.subtract(0, yOffset, 0);
    }

    public List<Vector> createCircle(double y, double radius) {
        double amount = radius * circleParticles;
        double inc = (2 * Math.PI) / amount;
        List<Vector> vectors = new ArrayList<>();

        double angle;
        double x;
        double z;
        Vector v;

        for (int i = 0; i < amount; i++) {
            angle = i * inc;
            x = radius * Math.cos(angle);
            z = radius * Math.sin(angle);
            v = new Vector(x, y, z);
            vectors.add(v);
        }
        return vectors;
    }

}
