package de.slikey.effectlib.effect;

import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.VectorUtils;

@SuppressWarnings({"unused"})
public class HelixEffect extends Effect {

    /**
     * Amount of strands
     */
    public int strands = 8;

    /**
     * Particles per strand
     */
    public int particles = 80;

    /**
     * Radius of helix
     */
    public float radius = 10;

    /**
     * Factor for the curves. Negative values reverse rotation.
     */
    public float curve = 10;

    /**
     * Rotation of the helix (Fraction of PI)
     */
    public double rotation = Math.PI / 4;

    /**
     * Whether to orient to the direction of the source location
     */
    public boolean orient = false;

    /**
     * Should it rotate?
     */
    public boolean enableRotation = false;

    /**
     * Rotation of the torus.
     */
    public double xRotation, yRotation, zRotation = 0;

    /**
     * Turns the helix by this angle each iteration around the x-axis
     */
    public double angularVelocityX = Math.PI / 200;

    /**
     * Turns the helix by this angle each iteration around the y-axis
     */
    public double angularVelocityY = Math.PI / 170;

    /**
     * Turns the helix by this angle each iteration around the z-axis
     */
    public double angularVelocityZ = Math.PI / 155;

    /**
     * Current step. Works as a counter
     */
    protected float step = 0;

    public HelixEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        particle = Particle.FLAME;
        period = 10;
        iterations = 8;
    }

    @Override
    public void reset() {
        step = 0;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        Vector v;

        if (location == null) {
            cancel();
            return;
        }

        float ratio;
        double angle;

        for (int i = 1; i <= strands; i++) {
            for (int j = 1; j <= particles; j++) {

                v = new Vector();
                ratio = (float) j / particles;
                angle = curve * ratio * 2 * Math.PI / strands + (2 * Math.PI * i / strands) + rotation;

                v.setX(Math.cos(angle) * ratio * radius);
                v.setZ(Math.sin(angle) * ratio * radius);

                VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);

                if (enableRotation) {
                    VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);
                }

                if (orient) v = VectorUtils.rotateVector(v, location);

                location.add(v);
                display(particle, location);
                location.subtract(v);

                step++;
            }
        }
    }

}
