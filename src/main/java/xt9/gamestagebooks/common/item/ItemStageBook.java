package xt9.gamestagebooks.common.item;

import net.darkhax.gamestages.data.GameStageSaveHandler;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xt9.gamestagebooks.GamestageBooks;
import xt9.gamestagebooks.ModConstants;
import xt9.gamestagebooks.common.commands.Sender;
import xt9.gamestagebooks.common.network.UnlockGamestageMessage;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2018-05-23.
 */
public class ItemStageBook extends ItemBase {
    private String stageName;
    private String unlockItemName;
    private String stageHumanReadable;
    private int bookColor;

    public ItemStageBook(String stageName, String stageHumanReadable, String displayName, String unlockItemName, int bookColor) {
        super(stageName, 1, displayName);
        this.stageName = stageName;
        this.stageHumanReadable = stageHumanReadable;
        this.unlockItemName = unlockItemName == null ? "minecraft:book" : unlockItemName;
        this.bookColor = bookColor;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add("Read the book to unlock new knowledge.");
    }

    public ResourceLocation getModelLocation() {
        return new ResourceLocation(ModConstants.MODID, "books");
    }

    public int getColor() {
        return bookColor;
    }

    public String getUnlockItemName() {
        return unlockItemName;
    }

    public String getStageHumanReadable() {
        return stageHumanReadable;
    }

    public String getStageName() {
        return stageName;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        if(!player.world.isRemote) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            playerMP.getServerWorld().addScheduledTask(() -> unlockStageWithBook(player.getHeldItem(hand), playerMP));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    private void unlockStageWithBook(ItemStack book, EntityPlayerMP player) {
        IStageData data = GameStageSaveHandler.getPlayerData(player.getUniqueID());
        ItemStageBook itemBook = (ItemStageBook) book.getItem();

        if(!data.hasStage(itemBook.getStageName()) && player.getServer() != null) {
            ICommandManager man = player.getServer().getCommandManager();
            man.executeCommand(new Sender(player), "gamestage silentadd @p " + itemBook.getStageName());

            book.shrink(1);
            GamestageBooks.network.sendTo(new UnlockGamestageMessage(itemBook.getStageHumanReadable(), itemBook.getUnlockItemName()), player);
            player.getServerWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_VILLAGER_AMBIENT, SoundCategory.MASTER, 1.3F, 1.0F);
            player.getServerWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 2.0F, 1.0F);
            player.getServerWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1.3F, -1.0F);
        } else {
            player.sendMessage(new TextComponentString("Already unlocked stage " + itemBook.getStageName() + "!"));
        }
    }
}
