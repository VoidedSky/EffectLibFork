package de.slikey.effectlib.util;

import org.bukkit.Color;
import org.bukkit.Material;

@SuppressWarnings({"unused"})
public class ParticleOptions {

    public Color color;
    public Color toColor;

    public float offsetX;
    public float offsetY;
    public float offsetZ;

    public float speed;
    public int amount;
    public int arrivalTime;
    public float size;

    public Material material;
    public byte materialData;

    public String blockData;
    public long blockDuration;

    public Object data;

    public DynamicLocation target;

    public int shriekDelay;
    public float sculkChargeRotation;

    public ParticleOptions() {

    }

    public ParticleOptions(float offsetX, float offsetY, float offsetZ, float speed, int amount, float size, Color color, Material material, byte materialData) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.size = size;
        this.color = color;
        this.material = material;
        this.materialData = materialData;
    }

    public ParticleOptions(float offsetX, float offsetY, float offsetZ, float speed, int amount, float size, Color color, Color toColor, int arrivalTime, Material material, byte materialData) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.size = size;
        this.color = color;
        this.toColor = toColor;
        this.material = material;
        this.materialData = materialData;
        this.arrivalTime = arrivalTime;
    }

    public ParticleOptions(float offsetX, float offsetY, float offsetZ, float speed, int amount, float size, Color color, Color toColor, int arrivalTime, Material material, byte materialData, String blockData, long blockDuration) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.size = size;
        this.color = color;
        this.toColor = toColor;
        this.material = material;
        this.materialData = materialData;
        this.arrivalTime = arrivalTime;
        this.blockData = blockData;
        this.blockDuration = blockDuration;
    }

    public ParticleOptions(float offsetX, float offsetY, float offsetZ, float speed, int amount, float size, Color color, Color toColor, int arrivalTime, Material material, byte materialData, String blockData, long blockDuration, int shriekDelay, float sculkChargeRotation) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.size = size;
        this.color = color;
        this.toColor = toColor;
        this.material = material;
        this.materialData = materialData;
        this.arrivalTime = arrivalTime;
        this.blockData = blockData;
        this.blockDuration = blockDuration;
        this.shriekDelay = shriekDelay;
        this.sculkChargeRotation = sculkChargeRotation;
    }

}
