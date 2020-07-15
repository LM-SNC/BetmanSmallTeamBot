package art.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CustomManager {

    public Message embedNotification(TextChannel textChannel, Member member, String emote, Color color, int interval, String message) {
        EmbedBuilder eb = new EmbedBuilder();
        Message searchMessage = null;
//        eb.addField(title,  emote + " " + message,true);
        eb.setAuthor(member.getUser().getAsTag(), null, member.getUser().getAvatarUrl());
        eb.setDescription(emote + " " + message);
        eb.setColor(color);
        searchMessage = textChannel.sendMessage(eb.build()).completeAfter(interval, TimeUnit.SECONDS);
//        eb.setTitle(title);
//        eb.setImage(emote.getImageUrl());
//        eb.setFooter("ERROR");
        return searchMessage;
    }
}
