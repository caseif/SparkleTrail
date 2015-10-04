package net.amigocraft.sparkletrail;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SparkleTrail extends JavaPlugin implements Listener {

	public static SparkleTrail plugin;
	public static Logger log;

	public HashMap<String, Long> last = new HashMap<String, Long>();

	public void onEnable(){
		plugin = this;
		log = getLogger();
		if (!ParticleEffect.isCompatible()){
			log.severe("This server software is unsupported! Disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info(this + " has been enabled!");
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if (e.getPlayer().hasPermission("sparkle.trail")){
			if (!(e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ())){
				int delay = 100;
				boolean send = true;
				if (last.containsKey(e.getPlayer().getName())){
					if (System.currentTimeMillis() - last.get(e.getPlayer().getName()) < delay)
						send = false;
				}
				if (send){
					ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.VILLAGER_HAPPY, 1, 10, 0);
					Location loc = new Location(
							e.getFrom().getWorld(),
							e.getFrom().getBlockX(),
							e.getFrom().getBlockY() - 1,
							e.getFrom().getBlockZ());
					effect.sendToLocation(loc);
				}
			}
		}
	}
}
