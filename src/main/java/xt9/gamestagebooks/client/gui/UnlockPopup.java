package xt9.gamestagebooks.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

/**
 * Created by xt9 on 2018-06-10.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class UnlockPopup extends GuiScreen {
    private FontRenderer fontRender;
    private Minecraft minecraft;
    private static String message = "";
    private static ItemStack messageItem;
    private static int ticksToRender = 0;
    private long lastTick = 0;

    public UnlockPopup(Minecraft mc) {
        super();
        minecraft = mc;
        fontRender = mc.fontRenderer;
        this.itemRender = minecraft.getRenderItem();
        setGuiSize(89, 12);
    }

    public static void handleMessage(String stageName, String itemName) {
        ticksToRender = 200;

        int metadata = 0;
        String[] parts = itemName.split("@");

        Item item = Item.getByNameOrId(parts[0]);
        if(item == null) {
            item = Items.STICK;
        }

        try {
            if(parts.length >= 2) {
                metadata = Integer.parseInt(parts[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number for meta or amount");
        }

        messageItem = new ItemStack(item, 1, metadata);
        message = "Unlocked " + stageName;
    }

    @SubscribeEvent(priority=EventPriority.NORMAL)
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        doTickChecks();

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        } else if(!minecraft.inGameHasFocus) {
            return;
        }

        if(ticksToRender > 0) {
            renderMessage();
        }
    }

    private void renderMessage() {
        float itemScale = 2.7F;

        int itemX = getScreenCenterX(itemScale) - 8;
        drawItemStack(itemX, 10, messageItem, itemScale);

        float scale1 = 1.9F;
        float scale2 = 1.1F;

        int x1 = getScreenCenterX(scale1) - getHalfLineWidth(message, scale1);
        renderScaledStringWithColor(scale1, x1, 74, message, 0xFFFFFF);

        String line2 = "You ponder the contents of the book";
        int x2 = getScreenCenterX(scale2) - getHalfLineWidth(line2, scale2);
        renderScaledString(scale2, x2, 92, line2);

        String line3 = "And you find yourself enlightened";
        int x3 = getScreenCenterX(scale2) - getHalfLineWidth(line3, scale2);
        renderScaledString(scale2, x3, 104, line3);
    }

    private void doTickChecks() {
        if(lastTick != minecraft.world.getTotalWorldTime()) {
            lastTick = minecraft.world.getTotalWorldTime();

            if(ticksToRender > 0) {
                ticksToRender--;
            }
        }
    }

    private void renderScaledStringWithColor(float scale, int x, int y, String text, int color) {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        drawString(fontRender, text, x, (int) (y / scale), color);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void renderScaledString(float scale, int x, int y, String text) {
        renderScaledStringWithColor(scale, x, y, text, 0xFFFFFF);
    }

    private int getItemHalfWidth(float glScale) {
        return (int) (((16 / 2) * glScale) / glScale);
    }

    private int getHalfLineWidth(String text, float glScale) {
        return (int) (((fontRender.getStringWidth(text) / 2) * glScale) / glScale);
    }

    private int getScreenCenterX() {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        return scaledResolution.getScaledWidth() / 2;
    }

    private int getScreenCenterX(float glScale) {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        return (int) ((scaledResolution.getScaledWidth() / 2) / glScale);
    }

    private int getLeftCornerX() {
        return 5;
    }

    private int getRightCornerX() {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        return scaledResolution.getScaledWidth() - width - 5;
    }

    private void drawItemStack(int x, int y, ItemStack stack, float scale) {
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, 0.0F, 32.0F);

        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();

        RenderHelper.disableStandardItemLighting();
    }
}
