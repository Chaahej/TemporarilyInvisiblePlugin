import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Countdown
{
    public boolean isInvincible;

    TemporarilyInvisibleListener TIL;

    public void startTimer(final Player player, final PotionEffect potion)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(TIL.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                effectFadingOff(player, potion);
            }
        }, 100L);
    }

    public void effectFadingOff(Player player, PotionEffect potion)
    {
        player.sendMessage("You can die again.");

        isInvincible = false;
        player.removePotionEffect(potion.getType());

        for(Player players : Bukkit.getServer().getOnlinePlayers())
        {
            players.showPlayer(player);
        }
    }
}
