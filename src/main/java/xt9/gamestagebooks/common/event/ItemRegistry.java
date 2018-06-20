package xt9.gamestagebooks.common.event;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.gamestagebooks.common.crafttweaker.Book;

/**
 * Created by xt9 on 2018-06-11.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class ItemRegistry {
    @SubscribeEvent
    public static void registerModels(RegistryEvent.Register<Item> event) {
        Book.books.forEach(event.getRegistry()::register);
    }
}
