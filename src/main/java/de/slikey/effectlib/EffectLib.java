package de.slikey.effectlib;

import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"unused"})
public final class EffectLib extends JavaPlugin {

    private static EffectLib instance;

    public EffectLib() {
        instance = this;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        EffectManager.disposeAll();
        HandlerList.unregisterAll(this);
    }

    public List<EffectManager> getEffectManagers() {
        return EffectManager.getManagers();
    }

    public static EffectLib instance() {
        return instance;
    }

}
