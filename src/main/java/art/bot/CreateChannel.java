import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;

//public class CreateChannel extends ListenerAdapter {
//
//
//    ArrayList<String> serversList = new ArrayList<>();
//    ArrayList<String> serversListCloned = new ArrayList<>();
//    int countServerId = 0;
//    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
//        String[] message = event.getMessage().getContentRaw().split(" ");
//        if(!event.getMessage().getAuthor().isBot() && message[0].equalsIgnoreCase("!help")){
//            event.getTextChannel().sendMessage("```" + "\n" + "Введите id канала который вы хотите клонировать с помощью: !cloneChannel id" + "\n" + "Введите id текстового канала который вы хотите удалить с помощью: !deleteTextChannel id" + "\n" + "```").queue();
//        }
//
//        if(!event.getMessage().getAuthor().isBot() && message[0].equalsIgnoreCase("!cloneChannel")){
//            System.out.println(message[1].replace("!",""));
//            String msg = "";
//            msg = message[1].replace("!","");
//            serversList.add(msg);
//            System.out.println(serversList.get(countServerId));
//            event.getTextChannel().sendMessage("```" + "\n" + "Отлично! Теперь данный канал по возможности будет клонирован" + "\n" + "```").queue();
//            countServerId++;
//        }
//        if(!event.getMessage().getAuthor().isBot() && message[0].equalsIgnoreCase("!deleteTextChannel")){
//            System.out.println(message[1].replace("!",""));
//                event.getJDA().getTextChannelById(message[1].replace("!", "")).delete().complete();
//                event.getTextChannel().sendMessage("```" + "\n" + "Канал был удалён!" + "\n" + "```").queue();
//        }
//        if(event.getTextChannel().getId().equals("719317467117256794") && event.getMessage().getContentDisplay().equals("!clear")) {
//            System.out.println("Clear all messages!");
//            for (Message mes : event.getChannel().getIterableHistory()) {
//                    event.getChannel().deleteMessageById(mes.getId()).queue();
//            }
//        }
//    }
//
//    @Override
//    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
//        for(int i = 0 ; i < serversList.size(); i++){
//            if(event.getChannelJoined().getId().equalsIgnoreCase(serversList.get(i))){
//                System.out.println("Channel: " + event.getChannelJoined().getName() + " was cloned");
//
//                VoiceChannel vh;
//                vh = event.getChannelJoined().createCopy().setName(event.getChannelJoined().getName() + "By" + event.getMember().getEffectiveName()).complete();
//                System.out.println("Channel was cloned: " + vh.getId());
//                serversListCloned.add(vh.getId());
//                event.getChannelJoined().getMembers().forEach(member -> member.kick());
//
//            }
//        }
//    }
//
//    @Override
//    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
//        for(int i = 0 ; i < serversListCloned.size(); i++){
//            if(event.getChannelLeft().getId().equalsIgnoreCase(serversListCloned.get(i))){
//                System.out.println("Channel: " + event.getChannelLeft().getName() + " was delete");
//                event.getChannelLeft().delete().complete();
//            }
//        }
//    }
//}
