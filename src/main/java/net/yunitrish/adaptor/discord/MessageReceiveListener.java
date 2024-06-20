package net.yunitrish.adaptor.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.text.Text;
import net.yunitrish.adaptor.AdaptorServer;
import net.yunitrish.adaptor.common.ModConfig;

public class MessageReceiveListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getChannel().getName().equals("一般")) return;
        Text message;
        String discordId = event.getAuthor().getId();
        if (AdaptorServer.data.isBindPlayer(discordId)) {
            message = Text.of("<" + ModConfig.getCustomName(discordId) + "> " + event.getMessage().getContentDisplay());
        } else {
            message = Text.of(
                    "[" + event.getGuild().getName() + "#" + event.getChannel().getName() + "] " +
                            event.getAuthor().getName() + ": "
                            + event.getMessage().getContentDisplay());
        }
        AdaptorServer.modServer.getPlayerManager().broadcast(message, false);
    }
}
