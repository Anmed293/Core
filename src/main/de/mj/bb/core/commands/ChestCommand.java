package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ChestCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;
    private final Data data;

    public ChestCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.data = coreSpigot.getModuleManager().getData();
        coreSpigot.setCommand(this, "chest");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player user = (Player) sender;
            if (user.hasPermission("vorbau.chest") || user.hasPermission("vorbau.*") || user.isOp()) {
                ItemStack OAK = new ItemStack(Material.CHEST);
                ItemMeta OAKMeta = OAK.getItemMeta();
                OAKMeta.setDisplayName("OAK");
                OAK.setItemMeta(OAKMeta);
                ItemStack Birch = new ItemStack(Material.CHEST);
                ItemMeta birchMeta = Birch.getItemMeta();
                birchMeta.setDisplayName("BIRCH");
                Birch.setItemMeta(birchMeta);
                ItemStack spruce = new ItemStack(Material.CHEST);
                ItemMeta spruceMeta = spruce.getItemMeta();
                spruceMeta.setDisplayName("SPRUCE");
                spruce.setItemMeta(spruceMeta);
                ItemStack jungle = new ItemStack(Material.CHEST);
                ItemMeta jungleeMeta = jungle.getItemMeta();
                jungleeMeta.setDisplayName("JUNGLEE");
                jungle.setItemMeta(jungleeMeta);
                ItemStack akazia = new ItemStack(Material.CHEST);
                ItemMeta akaziaMeta = akazia.getItemMeta();
                akaziaMeta.setDisplayName("AKAZIA");
                akazia.setItemMeta(akaziaMeta);
                ItemStack darkoak = new ItemStack(Material.CHEST);
                ItemMeta dMeta = darkoak.getItemMeta();
                dMeta.setDisplayName("DARK_OAK");
                darkoak.setItemMeta(dMeta);
                if (args.length == 1 && args[0].equalsIgnoreCase("OAK")) {
                    user.getInventory().addItem(OAK);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("birch")) {
                    user.getInventory().addItem(Birch);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("spruce")) {
                    user.getInventory().addItem(spruce);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("jungle")) {
                    user.getInventory().addItem(jungle);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("akazia")) {
                    user.getInventory().addItem(akazia);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("darkoak")) {
                    user.getInventory().addItem(darkoak);
                } else if (args.length == 1 && args[0].equals("all")) {
                    ItemStack chestall = new ItemStack(Material.CHEST);
                    ItemMeta Metaall = chestall.getItemMeta();
                    Metaall.setDisplayName("§cWoods All");

                    Inventory inventar = Bukkit.createInventory(null, 9, "§cWoods All");
                    inventar.setItem(0, OAK);
                    inventar.setItem(1, Birch);
                    inventar.setItem(2, spruce);
                    inventar.setItem(3, jungle);
                    inventar.setItem(4, akazia);
                    inventar.setItem(5, darkoak);
                    user.openInventory(inventar);
                } else if (args.length == 1 && args[0].equals("clear")) {
                    Inventory inventory = user.getInventory();
                    int i = inventory.getSize() - 1;
                    while (i >= 0) {
                        System.out.println(i + "/" + inventory.getSize());
                        if (inventory.getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase("BIRCH")) {
                            inventory.clear(i);
                        }
                        i--;
                    }
                    user.sendMessage(data.getPrefix() + "§aDie Chest's wurden erfolgreich gecleart.");
                } else {
                    user.sendMessage(data.getPrefix() + "§cBenutze: </chest> <Chest-Name>");
                }
            } else
                user.sendMessage(data.getNoPerm());
        } else sender.sendMessage(data.getOnlyPlayer());
        return false;
    }
}
