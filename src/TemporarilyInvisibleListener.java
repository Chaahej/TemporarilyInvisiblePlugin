import CTF.Arena.ArenaManager;
import CTF.Teams.Team;
import CTF.Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TemporarilyInvisibleListener implements Listener
{
    public static JavaPlugin plugin;

    TeamManager teamManager;
    ArenaManager arenaManager;
    Countdown countdown;
    //Flag flag;

    public TemporarilyInvisibleListener(JavaPlugin myPlugin)
    {
        plugin = myPlugin;

        teamManager = new TeamManager();
        arenaManager = new ArenaManager();
        countdown = new Countdown();
        //flag = new Flag();
    }

    @EventHandler
    public void pickUpFlag(PlayerPickupItemEvent pickUp)
    {
        if(!countdown.isInvincible)
        {
            if(pickUp.getItem().getItemStack().getType().equals(Material.WOOL))
            {
                Player player = pickUp.getPlayer();

                Team team;
                team = teamManager.GetPlayerTeam(player);

                if(team != null)
                {
                    DyeColor color = ((Wool) pickUp.getItem().getItemStack().getData()).getColor();

                    if(color == DyeColor.BLUE || color == DyeColor.RED)
                    {
                        //if((arenaManager.GetFlag("Red").CheckBlocks(pickUp.getPlayer().getWorld(), DyeColor.BLUE)) || (arenaManager.GetFlag("Blue").CheckBlocks(pickUp.getPlayer().getWorld(), DyeColor.RED)))
                        //{
                            player.sendMessage("You are now invincible.");

                            hasFlag(player);
                        //}
                    }
                }

                return;
            }
        }
    }

    public boolean hasFlag(Player player)
    {
        PotionEffect potion = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
        player.addPotionEffect(potion);

        countdown.isInvincible = true;

        for(Player players : Bukkit.getServer().getOnlinePlayers())
        {
            if(!players.equals(player))
            {
                players.hidePlayer(player);
            }
        }

        countdown.startTimer(player, potion);

        return true;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent damageEvent)
    {
        Entity entity = damageEvent.getEntity();

        if(entity instanceof Player)
        {
            if(countdown.isInvincible)
            {
                ((Player) entity).setFireTicks(0);
                damageEvent.setCancelled(true);
            }

            else
            {
                damageEvent.setCancelled(false);
            }
        }
    }
}