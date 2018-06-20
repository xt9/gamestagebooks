package xt9.gamestagebooks;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.gamestagebooks.common.ServerProxy;
import xt9.gamestagebooks.common.crafttweaker.Book;
import xt9.gamestagebooks.common.network.UnlockGamestageMessage;

/**
 * Created by xt9 on 2018-05-23.
 */
@Mod(modid = ModConstants.MODID, version = ModConstants.VERSION, useMetadata = true, dependencies = "required-after:bookshelf;required-after:gamestages", acceptedMinecraftVersions = "[1.12,1.12.2]")
@Mod.EventBusSubscriber
public class GamestageBooks {

    @Mod.Instance(ModConstants.MODID)
    public static GamestageBooks instance;

    @SidedProxy(clientSide="xt9.gamestagebooks.client.ClientProxy", serverSide="xt9.gamestagebooks.common.ServerProxy")
    public static ServerProxy proxy;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModConstants.MODID);
        network.registerMessage(UnlockGamestageMessage.Handler.class, UnlockGamestageMessage.class, 1, Side.CLIENT);

        CraftTweakerAPI.registerClass(Book.class);
        // load script early with "#loader gamestagebooks"
        CraftTweakerAPI.tweaker.loadScript(false, ModConstants.MODID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.initColors();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        // Register block, item, and particle renders after they have been initialized and
        // registered in pre-init; however, Minecraft's RenderItem and ModelMesher instances
        // must also be ready, so we have to register renders during init, not earlier
        proxy.registerRenderers();
    }

    public static CreativeTabs creativeTab = new CreativeTabs(ModConstants.MODID) {
        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getTabIconItem() {
            return ItemStack.EMPTY;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Items.BOOK);
        }
    };
}
