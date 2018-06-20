package xt9.gamestagebooks.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.gamestagebooks.client.gui.UnlockPopup;

/**
 * Created by xt9 on 2018-05-23.
 */
public class UnlockGamestageMessage implements IMessage {
    private String stageName;
    private String itemName;

    @SuppressWarnings("unused")
    public UnlockGamestageMessage() {}

    public UnlockGamestageMessage(String stageName, String itemName) {
        this.stageName = stageName;
        this.itemName = itemName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        stageName = ByteBufUtils.readUTF8String(buf);
        itemName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, stageName);
        ByteBufUtils.writeUTF8String(buf, itemName);
    }

    public static class Handler implements IMessageHandler<UnlockGamestageMessage, IMessage> {
        @Override
        public IMessage onMessage(UnlockGamestageMessage unlockGamestageMessage, MessageContext ctx) {
            UnlockPopup.handleMessage(unlockGamestageMessage.stageName, unlockGamestageMessage.itemName);
            return unlockGamestageMessage;
        }
    }
}
