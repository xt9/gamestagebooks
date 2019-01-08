package xt9.gamestagebooks.common.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by xt9 on 2019-01-08.
 */
public class Sender implements ICommandSender {
    private EntityPlayerMP player;

    public Sender(EntityPlayerMP player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void sendMessage(@Nonnull ITextComponent component) {}

    @Override
    public boolean sendCommandFeedback() {
        return false;
    }

    @Override
    public boolean canUseCommand(int i, String s) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return player.world;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return player.mcServer;
    }
}
