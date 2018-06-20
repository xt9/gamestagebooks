package xt9.gamestagebooks.common.event;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xt9.gamestagebooks.common.crafttweaker.Book;

/**
 * Created by xt9 on 2018-06-11.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelRegistry {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Book.books.forEach(book -> ModelLoader.setCustomModelResourceLocation(book, 0, new ModelResourceLocation(book.getModelLocation(), "inventory")));
    }
}
