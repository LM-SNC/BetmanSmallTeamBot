import jdk.nashorn.internal.runtime.Debug;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.omg.CORBA.SystemException;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.concurrent.TimeUnit;
//175 250
public class MessageHandler extends ListenerAdapter {
    String[][] emotesAndRoles = {{"721144040199946281","721144447160549426","721143265922908211",
            "720779553802551306", "720779564405489754","720779603811237888","721141726579916840",
            "721144310254534676","721143736687394958","721143407984115712","721142129652531221","721142908543041566"},//id Кастомных эмоджи
            {"720771836207693914","720772026368917584","720772919265067138","720772960670974065",
                    "720774410172104724","720773006066188310","720774245638078524","720771955145441322",
                    "720773144247402637","720773072444981328","720774150662258718","720772766563041280"}};//id Ролей
    @Override
    public void onReady(@Nonnull ReadyEvent event) {//Отлавливаем загрузку всех объектов
            for (int i = 0; i < emotesAndRoles[0].length; i++) {//Пробегаемся циклом по всем эмоджи
                    event.getJDA().getTextChannelById("720755054503526483").addReactionById("720777779632668742", event.getJDA().getEmoteById(emotesAndRoles[0][i])).timeout(20,TimeUnit.SECONDS).complete(); //Ставим реакции от лица бота на сообщение
            }
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
}