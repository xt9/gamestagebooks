package xt9.gamestagebooks.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xt9.gamestagebooks.GamestageBooks;
import xt9.gamestagebooks.ModConstants;

/**
 * Created by xt9 on 2018-05-23.
 */
public class ItemBase extends Item {
    public String itemName;
    public String displayName;

    public ItemBase(String name, int stackSize, String displayName) {
        setUnlocalizedName(ModConstants.MODID + "." + name);
        setCreativeTab(GamestageBooks.creativeTab);
        setRegistryName(ModConstants.MODID, name);
        setMaxStackSize(stackSize);
        this.itemName = name;
        this.displayName = displayName;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return displayName;
    }
}
