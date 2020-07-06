package art.bot;

import jdk.nashorn.internal.runtime.Debug;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
//175 250
public class MessageHandler extends ListenerAdapter {
    String[][] emotesAndRoles = {{"721144040199946281","721144447160549426","721143265922908211",
            "720779553802551306", "720779564405489754","720779603811237888","721141726579916840",
            "721144310254534676","721143736687394958","721143407984115712","721142129652531221","721142908543041566"},//id Кастомных эмоджи
            {"720771836207693914","720772026368917584","720772919265067138","720772960670974065",
                    "720774410172104724","720773006066188310","720774245638078524","720771955145441322",
                    "720773144247402637","720773072444981328","720774150662258718","720772766563041280"}};//id Ролей


    Message message;
    String firstIdl;
    String secondId;
    String namel;
    String name2;
    @Override
    public void onReady(@Nonnull ReadyEvent event) {//Отлавливаем загрузку всех объектов
            for (int i = 0; i < emotesAndRoles[0].length; i++) {//Пробегаемся циклом по всем эмоджи
                    event.getJDA().getTextChannelById("720755054503526483").addReactionById("720777779632668742", event.getJDA().getEmoteById(emotesAndRoles[0][i])).timeout(20,TimeUnit.SECONDS).complete(); //Ставим реакции от лица бота на сообщение
            }
            System.out.println(event.getJDA().getEmotes());
        }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        event.getGuild().addRoleToMember(event.getMember().getId(), event.getJDA().getRoleById("721443265605402697")).timeout(10,TimeUnit.SECONDS).complete(); //Добавляем роль пользователю
    }
    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) { //Отлавливаем добавление реакции на сообщение
        if(event.getReactionEmote().isEmote() && event.getTextChannel().getId().equals("720755054503526483") ) { //Убеждаемся что поставленная эмодзи - кастомная, а также что реакция была поставлена в нужном нам канале
            String idEmotion = event.getReactionEmote().getId(); //получаем id эмодзи
        for(int i = 0; i < emotesAndRoles[0].length; i++) { //пробегаемся циклом по всем эмодзи
            if (emotesAndRoles[0][i].equals(idEmotion)) { //Находим id эмодзи который совпадает с idEmotion
                event.getGuild().addRoleToMember(event.getMember().getId(), event.getJDA().getRoleById(emotesAndRoles[1][i])).timeout(10,TimeUnit.SECONDS).complete(); //Добавляем роль пользователю
                }
            }
        }
    }
    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {// Отлавливаем удаление реакции на сообщение
        if(event.getReactionEmote().isEmote() && event.getTextChannel().getId().equals("720755054503526483")) { //Убеждаемся что удаленная эмодзи - кастомная, а также что реакция была удалена в нужном нам канале
            String idEmotion = event.getReactionEmote().getId(); //Получаем id эмодзи
            for(int i = 0; i < emotesAndRoles[0].length; i++) {//пробегаемся циклом по всем эмодзи
                if (emotesAndRoles[0][i].equals(idEmotion)) { //Находим id эмодзи который совпадает с idEmotion
                    event.getGuild().removeRoleFromMember(event.getMember().getId(), event.getJDA().getRoleById(emotesAndRoles[1][i])).timeout(10,TimeUnit.SECONDS).complete(); //Удаляем роль у пользователя
                }
            }
        }
    }

    //Камень - ножницы бумага


    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
      //  if(event.getTextChannel().getId().equals("721459656337391617") && event.getMessage().getContentDisplay().equals("!searchOpponent")){
          //  firstIdl = event.getMember().getId();
         //   namel = event.getMember().getEffectiveName();
         //   event.getMessage().delete().queue();
          //   message = event.getMessage();
         //   message = message.getTextChannel().sendMessage("Игрок с ником " + event.getMember().getEffectiveName() + " ищет себе оппонента!").complete();
            //    System.out.println(message.getId());
            //    event.getTextChannel().addReactionById(message.getId(),event.getJDA().getEmoteById("721476097199439912")).complete();
          //  event.getTextChannel().addReactionById(message.getId(),event.getJDA().getEmoteById("721503056801300510")).complete();
            //event.getTextChannel().addReactionById(event.getMessageId(), event.getJDA().getEmoteById("721476097199439912")).timeout(20,TimeUnit.SECONDS).complete();

       // }
      //  if(event.getTextChannel().getId().equals("721459656337391617") && event.getMessage().getContentDisplay().equals(".rock")) {
//
      //  }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
      //  if(event.getChannel().getId().equals("721459656337391617") && event.getReactionEmote().getId().equals("721476097199439912") && !event.getUser().isBot()){
       //     secondId = event.getMember().getId();
       //     name2 = event.getMember().getEffectiveName();
      //      message.delete().queue();
       //     message = message.getChannel().sendMessage("Играют: " + namel + " и " + name2 + "\n" + "Выберите камень, ножницы или бумагу").complete();
       // }

    }
}