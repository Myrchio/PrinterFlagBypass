package easymc.it.printerflagbypass.features;
import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.config.annotation.ConfigValue;
import com.golfing8.kore.feature.Feature;
import com.golfing8.kore.feature.PrinterFeature;
import me.frep.vulcan.api.event.VulcanFlagEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class PrinterFlagBypass extends Feature {

    public PrinterFlagBypass(Plugin plugin, String name, String description, String basePermDesc, String adminPermDesc) {
        super(plugin, name, description, basePermDesc, adminPermDesc);
    }

    private BukkitTask task;

    @ConfigValue(name="bypass-areas", path="feature")
    private List<String> bypassAreas;
    @ConfigValue(name="fast-place-checks", path="feature")
    private List<String> fastPlaceChecks;
    private final PrinterFeature printer = FactionsKore.get().getFeature(PrinterFeature.class);
    private final Material prismarine = Material.PRISMARINE;

    public void onDisable() {
        this.cancelTask(this.task);
        if (!this.cancelTask(this.task)) {
            return;
        }
    }

    public void onEnable() {
        this.startTask();
    }
    private void startTask(){
        this.task = new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.getLogger().info("[FactionsKore] - printer-flag-bypass feature is enabled!");
            }
        }.runTask(this.getPlugin());
    }

    @EventHandler
    public void onPrinter(VulcanFlagEvent e){
        if (printer.isInPrinter(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(VulcanFlagEvent e){
        if (fastPlaceChecks.contains(e.getCheck().getName())){
            if (e.getPlayer().getItemInHand() == null){
                return;
            }
            ItemStack itemInHand = e.getPlayer().getItemInHand();
            if (itemInHand.getType().equals(prismarine) && itemInHand.hasItemMeta()){
                System.out.println("flag: "+ e.getCheck() + "| " + e.getCheck().getName());
            }
        }
    }
    @EventHandler
    public void onWorld(VulcanFlagEvent e){
        System.out.println("world: " + e.getPlayer().getLocation().getWorld().getName());
        if (bypassAreas.contains(e.getPlayer().getLocation().getWorld().getName())){
            e.setCancelled(true);
        }
    }
}
