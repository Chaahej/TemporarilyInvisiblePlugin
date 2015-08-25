import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class TemporarilyInvisiblePlugin extends JavaPlugin
{
     public static Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onLoad()
    {
        log.info("[TemporarilyInvisiblePlugin] is loading.");
    }

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new TemporarilyInvisibleListener(this), this);

        log.info("[TemporarilyInvisiblePlugin] is enabling.");
    }

    @Override
    public void onDisable()
    {
        log.info("[TemporarilyInvisiblePlugin] is shutting down...");
    }
}