package xt9.gamestagebooks.common.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xt9.gamestagebooks.common.item.ItemStageBook;

/**
 * Created by xt9 on 2018-06-10.
 */
@SuppressWarnings("unused")
@ZenClass("mods.gamestagebooks.Book")
public class Book {
    public static NonNullList<ItemStageBook> books = NonNullList.create();

    @ZenMethod
    public static void addBook(String stageName, String stageHumanReadable, String displayName, String unlockItemName, int bookColor) {
        CraftTweakerAPI.apply(new AddBook(new ItemStageBook(stageName, stageHumanReadable, displayName, unlockItemName, bookColor)));
    }

    private static class AddBook implements IAction {
        private ItemStageBook book;

        public AddBook(ItemStageBook book) {
            this.book = book;
        }

        @Override
        public void apply() {
            books.add(book);
        }

        @Override
        public String describe() {
            return "Adding a gamestagebook for stage: " + book.getStageName();
        }
    }
}
