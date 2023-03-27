package de.slikey.effectlib;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;

import de.slikey.effectlib.util.RandomUtils;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleOptions;

public abstract class Effect implements Runnable {

    /**
     * Sub effect
     * This will play a subeffect on the effect location
     */
    private String subEffectClass = null;
    public ConfigurationSection subEffect = null;

    /**
     * Handles the type, the effect is played.
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public EffectType type = EffectType.INSTANT;

    /**
     * ParticleType of spawned particle
     */
    public Particle particle = Particle.FLAME;

    /**
     * Can be used to colorize certain particles. As of 1.8, those
     * include SPELL_MOB_AMBIENT, SPELL_MOB and REDSTONE.
     */
    public Color color = null;

    public List<Color> colorList = null;
    public String colors = null;

    /**
     * Used for dust particles in 1.17 and up, to make a color transition.
     */
    public Color toColor = null;

    public List<Color> toColorList = null;
    public String toColors = null;

    /**
     * Used only by the shriek particle in 1.19 and up
     */
    public int shriekDelay;

    /**
     * Used only by the sculk_charge particle in 1.19 and up
     */
    public float sculkChargeRotation;

    /**
     * Used only by the vibration particle in 1.17 and up
     */
    public int arrivalTime;

    /**
     * This can be used to give particles a set speed when spawned.
     * This will not work with colored particles.
     */
    @Deprecated
    public float speed = 0;

    /**
     * This can be used to give particles a set speed when spawned.
     * This will not work with colored particles.
     *
     * This is a replacement for "speed"
     */
    public float particleData = 0;

    /**
     * Delay to wait for delayed effects.
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public int delay = 0;

    /**
     * Interval to wait for repeating effects.
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public int period = 1;

    /**
     * Amount of repetitions to do.
     * Set this to -1 for an infinite effect
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public int iterations = 0;

    /**
     * Total duration of this effect in milliseconds.
     *
     * If set, this will adjust iterations to match
     * the defined delay such that the effect lasts
     * a specific duration.
     */
    public Integer duration = null;

    /**
     * Probability that this effect will play on each iteration
     */
    public double probability = 1;

    /**
     * Callback to run, after effect is done.
     *
     * @see {@link java.lang.Runnable}
     */
    public Runnable callback = null;

    /**
     * Display particles to players within this radius.
     */
    public float visibleRange = 32;

    /**
     * If true, and a "target" Location or Entity is set, the two Locations
     * will orient to face one another.
     */
    public boolean autoOrient = false;

    /**
     * If set, will offset the origin location
     */
    public Vector offset = null;

    /**
     * If set, will offset the origin location, relative to the origin direction
     */
    public Vector relativeOffset = null;

    /**
     * If set, will offset the target location
     */
    public Vector targetOffset = null;

    /**
     * These are used to modify the direction of the origin Location.
     */
    public float yawOffset = 0;
    public float pitchOffset = 0;
    public Float yaw = null;
    public Float pitch = null;

    /**
     * If set to false, Entity-bound locations will not update during the Effect
     */
    public boolean updateLocations = true;

    /**
     * If set to false, Entity-bound directions will not update during the Effect
     */
    public boolean updateDirections = true;

    /**
     * A specific player who should see this effect.
     */
    public Player targetPlayer;

    /**
     * A group of players who should see this effect.
     */
    public List<Player> targetPlayers;

    /**
     * The Material and data to use for block and item break particles
     */
    public Material material;
    public byte materialData;

    public BlockData blockData;

    public long blockDuration;

    /**
     * These can be used to spawn multiple particles per packet.
     * It will not work with colored particles, however.
     */
    public int particleCount = 1;

    /**
     * These can be used to apply an offset to spawned particles, particularly
     * useful when spawning multiples.
     */
    public float particleOffsetX = 0;
    public float particleOffsetY = 0;
    public float particleOffsetZ = 0;

    /**
     * This can be used to scale up or down a particle's size.
     *
     * This currently only works with the redstone particle in 1.13 and up.
     */
    public float particleSize = 1;

    /**
     * If set, will run asynchronously.
     * Some effects don't support this (TurnEffect, JumpEffect)
     *
     * Generally this shouldn't be changed, unless you want to
     * make an async effect synchronous.
     */
    public boolean asynchronous = true;
    protected final EffectManager effectManager;

    protected DynamicLocation origin = null;
    protected DynamicLocation target = null;

    /**
     * This will store the base number of iterations
     */
    protected int maxIterations;

    /**
     * Should this effect stop playing if the origin entity becomes invalid?
     */
    public boolean disappearWithOriginEntity = false;
    
    /**
     * Should this effect stop playing if the target entity becomes invalid?
     */
    public boolean disappearWithTargetEntity = false;

    private boolean done = false;

    private boolean playing = false;

    private long startTime;

    public Effect(EffectManager effectManager) {
        if (effectManager == null) throw new IllegalArgumentException("EffectManager cannot be null!");

        this.effectManager = effectManager;
        visibleRange = effectManager.getParticleRange();
    }

    protected List<Color> parseColorList(String colors) {
        List<Color> colorList = new ArrayList<>();
        String[] args = colors.split(",");
        if (args.length >= 1) {
            for (String str : args) {
                try {
                    int rgb = Integer.parseInt(str.trim().replace("#", ""), 16);
                    colorList.add(Color.fromRGB(rgb));
                } catch (NumberFormatException ignored) {}
            }
        }
        return colorList;
    }

    protected void initialize() {
        if (period < 1) period = 1;

        if (colors != null) {
            colorList = parseColorList(colors);
        }
        if (toColors != null) {
            toColorList = parseColorList(toColors);
        }

        if (subEffect != null) {
            subEffectClass = subEffect.getString("subEffectClass");
        }
    }

    public final void cancel() {
        cancel(true);
    }

    public final void cancel(boolean callback) {
        if (callback) done();
        else done = true;
    }

    public final boolean isDone() {
        return done;
    }

    public boolean isPlaying() {
        return playing;
    }

    public abstract void onRun();

    /**
     * Called when this effect is done playing (when {@link #done()} is called).
     */
    public void onDone() { }

    @Override
    public final void run() {
        if (!validate()) {
            cancel();
            return;
        }

        if (done) {
            effectManager.removeEffect(this);
            return;
        }

        try {
            if (RandomUtils.checkProbability(probability)) {
                onRun();
            }
        } catch (Exception ex) {
            done();
            effectManager.onError(ex);
        }

        if (type == EffectType.REPEATING) {
            if (iterations == -1) return;
            iterations--;
            if (iterations < 1) done();
        } else {
            done();
        }
    }

    /**
     * Effects should override this if they want to be reusable, this is called prior to starting so
     * state can be reset.
     */
    protected void reset() {
        done = false;
    }

    public void prepare() {
        reset();
        updateDuration();
    }

    public final void start() {
        prepare();
        effectManager.start(this);
        playing = true;
    }

    public final void infinite() {
        type = EffectType.REPEATING;
        iterations = -1;
    }

    /**
     * Extending Effect classes can use this to determine the Entity this
     * Effect is centered upon.
     *
     * This may return null, even for an Effect that was set with an Entity,
     * if the Entity gets GC'd.
     */
    public Entity getEntity() {
        return origin == null ? null : origin.getEntity();
    }

    /**
     * Extending Effect classes can use this to determine the Entity this
     * Effect is targeted upon. This is probably a very rare case, such as
     * an Effect that "links" two Entities together somehow. (Idea!)
     *
     * This may return null, even for an Effect that was set with a target Entity,
     * if the Entity gets GC'd.
     */
    public Entity getTargetEntity() {
        return target == null ? null : target.getEntity();
    }

    /**
     * Extending Effect classes should use this method to obtain the
     * current "root" Location of the effect.
     *
     * This method will not return null when called from onRun. Effects
     * with invalid locations will be cancelled.
     */
    public final Location getLocation() {
        return origin == null ? null : origin.getLocation();
    }

    /**
     * Extending Effect classes should use this method to obtain the
     * current "target" Location of the effect.
     *
     * Unlike getLocation, this may return null.
     */
    public final Location getTarget() {
        return target == null ? null : target.getLocation();
    }

    /**
     * Set the Location this Effect is centered on.
     */
    public void setDynamicOrigin(DynamicLocation location) {
        if (location == null) throw new IllegalArgumentException("Origin Location cannot be null!");
        origin = location;

        if (offset != null) origin.addOffset(offset);
        if (relativeOffset != null) origin.addRelativeOffset(relativeOffset);

        origin.setDirectionOffset(yawOffset, pitchOffset);
        origin.setYaw(yaw);
        origin.setPitch(pitch);
        origin.setUpdateLocation(updateLocations);
        origin.setUpdateDirection(updateDirections);
        origin.updateDirection();
    }

    /**
     * Set the Location this Effect is targeting.
     */
    public void setDynamicTarget(DynamicLocation location) {
        target = location;
        if (target != null && targetOffset != null) target.addOffset(targetOffset);
        if (target == null) return;
        target.setUpdateLocation(updateLocations);
        target.setUpdateDirection(updateDirections);
    }

    protected final boolean validate() {
        // Check if the origin and target entities are present
        if (disappearWithOriginEntity && (origin != null && !origin.hasValidEntity())) return false;
        if (disappearWithTargetEntity && (target != null && !target.hasValidEntity())) return false;

        // Check for a valid Location
        updateLocation();
        updateTarget();
        Location location = getLocation();
        if (location == null) return false;
        if (autoOrient) {
            Location targetLocation = target == null ? null : target.getLocation();
            if (targetLocation != null) {
                Vector direction = targetLocation.toVector().subtract(location.toVector());
                location.setDirection(direction);
                targetLocation.setDirection(direction.multiply(-1));
            }
        }

        return true;
    }

    protected void updateDuration() {
        if (duration != null) {
            if (period < 1) period = 1;
            iterations = duration / period / 50;
        }
        maxIterations = iterations;
    }

    protected void updateLocation() {
        if (origin != null) origin.update();
    }

    protected void updateTarget() {
        if (target != null) target.update();
    }

    protected void display(Particle effect, Location location) {
        display(effect, location, color);
    }

    protected void display(Particle particle, Location location, Color color) {
        display(particle, location, color, particleData != 0 ? particleData : speed, particleCount);
    }

    protected void display(Particle particle, Location location, float speed, int amount) {
        display(particle, location, color, speed, amount);
    }

    protected void display(Particle particle, Location location, Color color, float speed, int amount) {
        display(particle, location, color, toColor, speed, amount);
    }

    protected void display(Particle particle, Location location, Color color, Color toColor, float speed, int amount) {
        // display particles only when particleCount is equal or more than 0
        if (particleCount >= 0) {
            if (targetPlayers == null && targetPlayer != null) {
                targetPlayers = new ArrayList<>();
                targetPlayers.add(targetPlayer);
            }

            Color currentColor = color;
            if (colorList != null && !colorList.isEmpty()) {
                currentColor = colorList.get(ThreadLocalRandom.current().nextInt(colorList.size()));
            }

            Color currentToColor = toColor;
            if (toColorList != null && !toColorList.isEmpty()) {
                currentToColor = toColorList.get(ThreadLocalRandom.current().nextInt(colorList.size()));
            }

            ParticleOptions options = new ParticleOptions(particleOffsetX, particleOffsetY, particleOffsetZ, speed, amount, particleSize, currentColor, currentToColor, arrivalTime, material, materialData, blockData, blockDuration, shriekDelay, sculkChargeRotation);
            options.target = target;

            effectManager.display(particle, options, location, visibleRange, targetPlayers);
        }

        if (subEffectClass != null) effectManager.start(subEffectClass, subEffect, location);
    }

    private void done() {
        playing = false;
        done = true;
        effectManager.done(this);
        onDone();
    }

    public EffectType getType() {
        return type;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    public int getDelay() {
        return delay;
    }

    public int getPeriod() {
        return period;
    }
    
    public void setEntity(Entity entity) {
        setDynamicOrigin(new DynamicLocation(entity));
    }

    public void setLocation(Location location) {
        setDynamicOrigin(new DynamicLocation(location));
    }

    public DynamicLocation getDynamicOrigin() {
        return origin;
    }

    public DynamicLocation getDynamicTarget() {
        return target;
    }

    public void setTargetEntity(Entity entity) {
        target = new DynamicLocation(entity);
    }

    public void setTargetLocation(Location location) {
        target = new DynamicLocation(location);
    }

    public Player getTargetPlayer() {
    	return targetPlayer;
    }

    public void setTargetPlayer(Player p) {
    	targetPlayer = p;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void reloadParameters() { }

}
