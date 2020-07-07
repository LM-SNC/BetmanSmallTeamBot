package art.bot;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GiftBot extends ListenerAdapter {
    String emotesNumber[] = {"728372880743596063", "728372858824294511", "728372894438260736", "728372909080444929"};
    public String[][] answerAndQuestion = {{"```" + "\n" + "В каком году путин обнулился?\n" +
            "1) 2020\n" +
            "2) 2015\n" +
            "3) 2018\n" +
            "4) 2004" + "\n" + "```", "```" + "\n" + "В каком году путин обнулился?\n" +
            "1) 2020\n" +
            "2) 2015\n" +
            "3) 2018\n" +
            "4) 2004" + "\n" + "```"},
            {"728372880743596063", "728372858824294511"}
    };

    private Message globalMessage;
    private String channelStr = "719317467117256794";
    private TextChannel textChannelUse;
    private MyRunnable myRunnable;
    private Thread t;
    private int iteration;
    int questionStep;

    private String[][] userEmojiAnswer;
    private ArrayList<String> acceptUsersIdStart = new ArrayList();
    private ArrayList<String> looseList = new ArrayList<>();


    public boolean onStartBool;
    boolean onQuestionTime;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        textChannelUse = event.getJDA().getTextChannelById(channelStr);
        onStartBool = true;
        Message message = (Message) event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Викторина" + "\n" + "Осталось: 1:00" + "\n" + "```")).complete();
        globalMessage = message;

        myRunnable = new MyRunnable(this, event);
        t = new Thread(myRunnable);
        t.start();
        for (int i = 10; i >= 0; i--) {
            iteration += 1;
            message.editMessage("```" + "\n" + "Викторина" + "\n" + "Осталось: 0:" + i + "\n" + "Вы принимаете участие в викторине?" + "\n" + "```").queueAfter(iteration, TimeUnit.SECONDS);
        }
        System.out.println(event.getJDA().getEmotes() + " SNC");
        globalMessage.addReaction(event.getJDA().getEmoteById("729000468730085426")).queue();

        //event.getJDA().getTextChannelById(channelStr).sendMessage("Как зовут создателя сервера? - " + "\n" + "а) Лев" + "\n" + "б) Миша" + "\n" + "г) Ваня" + "\n" + "д) Гриша").queue();
    }

    public void startVictorIn(Event event) {
        System.out.println("Count accept user: " + acceptUsersIdStart.size());
//        String onlineList = "";
//        for (int i = 0; i < event.getJDA().getTextChannelById(channelStr).getMembers().size(); i++){
//            if(event.getJDA().getTextChannelById(channelStr).getMembers().get(i).getOnlineStatus().equals(OnlineStatus.ONLINE)){
//                onlineList = onlineList + ", " + event.getJDA().getTextChannelById(channelStr).getMembers().get(i).getEffectiveName();
//            }
//        }
        //event.getJDA().getTextChannelById(channelStr).getMembers().forEach(member -> member.getPermissions().remove(Permission.MESSAGE_ADD_REACTION));Удаляем права
        String acceptListString = "";
        userEmojiAnswer = new String[2][acceptUsersIdStart.size()];

        for (int i = 0; i < acceptUsersIdStart.size(); i++) {
            acceptListString = acceptListString + " " + getUserName(acceptUsersIdStart.get(i), event);
            System.out.println(i);
            System.out.println(acceptUsersIdStart.size() + " size");
            userEmojiAnswer[0][i] = acceptUsersIdStart.get(i);
        }


        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Викторина началсь!" + "\n" + "Приняли участие:" + acceptListString + "\n" + "```")).complete();
        acceptUsersIdStart.clear();
        //event.getJDA().getTextChannelById(channelStr).sendMessage("Не успели принять участие: " + onlineList).queue();

    }

    public String getUserName(String getId, Event event) {
        String name = "";
        for (Member itVar : textChannelUse.getMembers()) {
            if (itVar.getId().equalsIgnoreCase(getId)) {
                name = itVar.getEffectiveName();
            }
        }
        return name;
    }

    public void onNextQuestion(Event event) {
        iteration = 0;
        Message message;
        String msg;
        message = (Message) event.getJDA().getTextChannelById(channelStr).sendMessage(answerAndQuestion[0][questionStep]).complete();
        globalMessage = message;
        msg = message.getContentDisplay();
        //Message message = (Message) event.getJDA().getTextChannelById(channelStr).sendMessage(answerAndQuestion[0][0]).queue();

        for (int i = 0; i < emotesNumber.length; i++) {
            globalMessage.addReaction(event.getJDA().getEmoteById(emotesNumber[i])).queue();
            // System.out.println(globalMessage.getReactions());
        }

        for (int i = 10; i >= 0; i--) {
            iteration += 1;
            message.editMessage(msg + "\n" + "Осталось: " + i).queueAfter(iteration, TimeUnit.SECONDS);
        }
    }

    public void onResult(Event event) {
        String trueUsers = "";
        String falseUsers = "";
        for (String loose : looseList) {
            for (String user : userEmojiAnswer[0]) {
                if (loose.equalsIgnoreCase(user)) {
                    System.out.println("Игрок " + getUserName(user, event) + " находится в looseList!");
                    return;
                } else {
                    System.out.println("Игрок " + getUserName(user, event) + " не находится в looseList!");
                }
            }
        }
        for (String user : userEmojiAnswer[0]) {
            for (String var : userEmojiAnswer[1]) {
                if (var == null) {
                    falseUsers = falseUsers + " " + getUserName(user, event);
                    System.out.println("Игрок " + getUserName(user, event) + " не успел ответить!");
                    looseList.add(user);
                    System.out.println("Игрок " + getUserName(user, event) + " добавлен в looseList!");
                } else if (var.equalsIgnoreCase(answerAndQuestion[1][questionStep])) {
                    System.out.println("Игрок " + getUserName(user, event) + "ответил правильно!");
                    trueUsers = trueUsers + " " + getUserName(user, event);
                } else {
                    falseUsers = falseUsers + " " + getUserName(user, event);
                    System.out.println("Игрок " + getUserName(user, event) + " ответил неправильно!");
                    looseList.add(user);
                    System.out.println("Игрок " + getUserName(user, event) + " добавлен в looseList!");
                }
            }
        }
        for (int i = 0; i < userEmojiAnswer[1].length; i++) {
            userEmojiAnswer[1][i] = null;
        }
        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Время истекло!" + "\n" + "Правильно ответили:" + trueUsers + "\n" + "Выбыли:" + falseUsers + "\n" + "```")).queue();
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if (event.getReaction().getReactionEmote().getId().equalsIgnoreCase("729000468730085426") && !event.getUser().isBot() && onStartBool && event.getMessageId().equalsIgnoreCase(globalMessage.getId())) {
            for (String var : acceptUsersIdStart) {
                if (event.getMember().getId().equalsIgnoreCase(var)) {
                    System.out.println("GiftBot::onMessageReactionAdd(); -- ID: " + event.getMember().getId() + " Name: " + getUserName(event.getMember().getId(), event) + " уже есть в массиве(acceptUsersIdStart)!");
                    return;
                }
            }
            acceptUsersIdStart.add(event.getMember().getId());
            System.out.println("GiftBot::onMessageReactionAdd(); -- ID: " + event.getMember().getId() + " Name: " + getUserName(event.getMember().getId(), event) + " был успешно добавлен в массив(acceptUsersIdStart)!");
        }
        for (String loose : looseList) {
            for (String user : userEmojiAnswer[0]) {
                if (loose.equalsIgnoreCase(user)) {
                    return;
                } else {
                }
            }
        }
        if (!event.getUser().isBot() && onQuestionTime && event.getMessageId().equalsIgnoreCase(globalMessage.getId())) {
            for (int i = 0; i < userEmojiAnswer[0].length; i++) {
                if (event.getMember().getId().equalsIgnoreCase(userEmojiAnswer[0][i])) {
                    userEmojiAnswer[1][i] = event.getReactionEmote().getId();
                    System.out.println("GiftBot::onMessageReactionAdd(); -- ID: " + event.getMember().getId() + " Name: " + getUserName(event.getMember().getId(), event) + " ReactionId: " + event.getReactionEmote().getId() + " был успешно добавлен в массив(userEmojiAnswer[1][" + i + "]");
                }
            }
        }
    }

    public void sendMessageForTrueAnswer(MessageReceivedEvent event) {
//        if(queuenumber < questionAndAnswer[0].length) {
//            event.getTextChannel().sendMessage("Следующий вопрос будет доступен через: " + interval + " секунд").queue();
//            event.getTextChannel().sendMessage(questionAndAnswer[0][queuenumber--]).queueAfter(interval, TimeUnit.SECONDS);
//        }
    }

    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    }

}

class MyRunnable implements Runnable {

    GiftBot giftBot;
    Event event;

    public MyRunnable(GiftBot giftBot, Event event) {
        this.giftBot = giftBot;
        this.event = event;
    }

    public void run() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        giftBot.startVictorIn(event);
        giftBot.onStartBool = false;

        for (int i = 0; i < giftBot.answerAndQuestion.length; i++) {
            giftBot.onNextQuestion(event);
            giftBot.onQuestionTime = true;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            giftBot.onQuestionTime = false;
            giftBot.onResult(event);
            giftBot.questionStep++;
        }
        System.out.println("Викторина окончена!");
    }
}