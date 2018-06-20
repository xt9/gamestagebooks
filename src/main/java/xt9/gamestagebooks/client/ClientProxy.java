package xt9.gamestagebooks.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xt9.gamestagebooks.client.gui.UnlockPopup;
import xt9.gamestagebooks.common.ServerProxy;
import xt9.gamestagebooks.common.crafttweaker.Book;
import xt9.gamestagebooks.common.item.ItemStageBook;

/**
 * Created by xt9 on 2018-05-23.
 */
public class ClientProxy extends ServerProxy {
    @Override
    public void registerRenderers() {
        MinecraftForge.EVENT_BUS.register(new UnlockPopup(Minecraft.getMinecraft()));
    }


    @Override
    public void initColors() {
        for (ItemStageBook book : Book.books) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
                if (tintIndex == 0) {
                    return book.getColor();
                }
                return -1;
            }, book);
        }
    }
}
