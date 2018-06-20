# 1.12 - Gamestagebooks
Crafttweaker addon that generate books which unlock gamestages. You can set things like what color the book should be, and what item it should display as a header when the book is used to unlock the stage.

Usage
Put all of the books in a separate script.
The first line must be `#loader gamestagebooks`
This ensures that the script is during preInit, so that items & their models can be properly registered.

### Script example
```
#loader gamestagebooks
import mods.gamestagebooks.Book;

Book.addBook("emerald_stage", "Emerald Stage", "Emerald Knowledge", "minecraft:emerald", 0x0cf200);
```
Stagename -> "emerald_stage"

Human readable stagename -> "Emerald stage"

Name of the Book -> "Emerald knowledge"

Item to display on unlock -> "minecraft:emerald"
Item to display on unlock with meta -> "minecraft:wool@4"

Color of the book  -> 0x0cf200

Color is a hexvalue prefixed with 0x