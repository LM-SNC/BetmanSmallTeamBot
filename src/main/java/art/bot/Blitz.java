package art.bot;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Blitz extends ListenerAdapter {
    private MyRunnableBlitz myRunnableBlitz;
    public ConnectionManager connectionManager;
    private Thread t;
    String[] words = {};
    boolean[] wordsBool;
    String textChannelString = "719317467117256794";
    TextChannel txtChannel;
    int wordStep = -1;
    private Message globalMessage;
    int starCount = 0;
    String word = "";

    @Override
    public void onReady(@Nonnull ReadyEvent event) {

        txtChannel = event.getJDA().getTextChannelById(textChannelString);
        wordsBool = new boolean[words.length];
        myRunnableBlitz = new MyRunnableBlitz(event, this);
        connectionManager = new ConnectionManager();
        connectionManager.onConnectionBd();
        t = new Thread(myRunnableBlitz);
        t.start();


        //txtChannel.sendMessage("Викторина скоро начнётся!");
    }


    public String getUserName(String getId, Event event) {
        String name = "";
        for (Member itVar : txtChannel.getMembers()) {
            if (itVar.getId().equalsIgnoreCase(getId)) {
                name = itVar.getEffectiveName();
            }
        }
        return name;
    }

    public void onNextWord(Event event) {
        starCount = 0;
        word = "";

        System.out.println(word);

        for (int i = 0; i < words[wordStep].length(); i++) {
            word = word + "⚹";
        }
        System.out.println(word);
        Message message = (Message) txtChannel.sendMessage(word).complete();
        globalMessage = message;
        System.out.println("onNextWord(Event event);");
    }

    public void onBlitzEnd() {
        txtChannel.sendMessage("Викторина окончена! Дополнительная информация: " + "\n").complete();
    }

    public void onOpenWord() {
        char[] wordArray = words[wordStep].toCharArray();
        char[] myNameChars = word.toCharArray();
        myNameChars[starCount] = wordArray[starCount];
        word = String.valueOf(myNameChars);
        System.out.println("Задача: разгадать слово" + "\n" + "До открытия следующей буквы осталось: " + "\n" + word);
        //System.out.println("starCount" + starCount);
        int interval = 0;
        for (int i = 10; i >= 0; i--) {
            interval++;
            globalMessage.editMessage("Задача: разгадать слово" + "\n" + "До открытия следующей буквы осталось: " + i + "\n" + word).queueAfter(interval, TimeUnit.SECONDS);
        }
        starCount++;
        System.out.println("Еще один символ успешно открыт: " + wordArray[starCount - 1]);
    }

    public void onExtremeOpenWord() {

        globalMessage.editMessage("Задача: разгадать слово" + "\n" + "До открытия следующей буквы осталось: " + 0 + "\n" + words[wordStep]).complete();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        int score;
        score = 0;

        if (starCount < 2) {
            score = 5;
        } else if (starCount < 3) {
            score = 3;
        } else if (starCount < 5) {
            score = 2;
        } else {
            score = 1;
        }
        String[] message = event.getMessage().getContentRaw().split(" ");
        for (int i = 0; i < words.length; i++) {
            if (message[0].equalsIgnoreCase(words[i]))
                if (!wordsBool[i]) {
                    txtChannel.sendMessage(event.getMember().getEffectiveName() + " , ты успешно отгадал слово!" + " + " + score + " score!").complete();
                    wordsBool[i] = true;
                    connectionManager.onAddScore(event.getMember().getId(), score);
                } else {
                    txtChannel.sendMessage(event.getMember().getEffectiveName() + " , Такое слово уже отгадали!").complete();
                }
        }
    }
}
