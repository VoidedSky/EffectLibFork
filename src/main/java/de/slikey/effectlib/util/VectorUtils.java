package de.slikey.effectlib.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class VectorUtils {

    private VectorUtils() {
    }

    public static Vector rotateAroundAxisX(Vector vector, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = vector.getY() * cos - vector.getZ() * sin;
        z = vector.getY() * sin + vector.getZ() * cos;
        return vector.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector vector, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = vector.getX() * cos + vector.getZ() * sin;
        z = vector.getX() * -sin + vector.getZ() * cos;
        return vector.setX(x).setZ(z);
    }

    public static Vector rotateAroundAxisZ(Vector vector, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = vector.getX() * cos - vector.getY() * sin;
        y = vector.getX() * sin + vector.getY() * cos;
        return vector.setX(x).setY(y);
    }

    public static Vector rotateVector(Vector vector, double angleX, double angleY, double angleZ) {
        // double x = vector.getX(), y = vector.getY(), z = vector.getZ();
        // double cosX = Math.cos(angleX), sinX = Math.sin(angleX), cosY =
        // Math.cos(angleY), sinY = Math.sin(angleY), cosZ = Math.cos(angleZ),
        // sinZ = Math.sin(angleZ);
        // double nx, ny, nz;
        // nx = (x * cosY + z * sinY) * (x * cosZ - y * sinZ);
        // ny = (y * cosX - z * sinX) * (x * sinZ + y * cosZ);
        // nz = (y * sinX + z * cosX) * (-x * sinY + z * cosY);
        // return vector.setX(nx).setY(ny).setZ(nz);
        // Having some strange behavior up there.. Have to look in it later. TODO
        rotateAroundAxisX(vector, angleX);
        rotateAroundAxisY(vector, angleY);
        rotateAroundAxisZ(vector, angleZ);
        return vector;
    }

    /**
     * Rotate a vector about a location using that location's direction
     *
     * @param vector vector to rotate
     * @param location location to get yaw and pitch from
     * @return rotated vector
     */
    public static Vector rotateVector(Vector vector, Location location) {
        return rotateVector(vector, location.getYaw(), location.getPitch());
    }

    /**
     * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
     *
     * Thanks to SexyToad!
     *
     * @param vector vector to rotate
     * @param yawDegrees yaw degrees
     * @param pitchDegrees pitch degrees
     * @return rotated vector
     */
    public static Vector rotateVector(Vector vector, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = vector.getX();
        initialY = vector.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = vector.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector(x, y, z);
    }

    public static double angleToXAxis(Vector vector) {
        return Math.atan2(vector.getX(), vector.getY());
    }

}
