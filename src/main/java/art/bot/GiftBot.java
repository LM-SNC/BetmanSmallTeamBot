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
    public String[][] answerAndQuestion = {{"```" + "\n" + "В каком году путин обнулился?\n" + "1) 2008\n" + "2) 2015\n" + "3) 2020\n" + "4) 2000" + "\n" + "```", "```" + "\n" + "У фермера было 30 овец, и все, кроме пятнадцати умерли. Сколько овец осталось у фермера? \n" + "1) 23\n" + "2) 15\n" + "3) 18\n" + "4) 9" + "\n" + "```", "```" + "\n" + "Столица венгрии?\n" + "1) Белград\n" + "2) Канберра\n" + "3) Оттава\n" + "4) Будапешт" + "\n" + "```", "```" + "\n" + "В греческой мифологии: герой, совершивший 12 подвигов\n" + "1) Орфей\n" + "2) Одиссей\n" + "3) Персей\n" + "4) Геракл" + "\n" + "```", "```" + "\n" + "Знак, обозначающий звук определенной высоты\n" + "1) Нота\n" + "2) Цифра\n" + "3) Слово\n" + "4) Децибел" + "\n" + "```", "```" + "\n" + "Пустыня в Африке\n" + "1) Калахари\n" + "2) Сахара\n" + "3) Патагонская\n" + "4) Чиуауа " + "\n" + "```"},
            {"728372894438260736", "728372858824294511", "728372909080444929", "728372909080444929", "728372880743596063", "728372858824294511"}
    };

    String[] keys = {"hRnon9DnnPLOEsdhm8Ub", "rimv4mijjxmsrg21ETMq", "rY8JozrtWdluIyPC4hhW", "JJaPtB6IZxnRAmI6iHzx", "CpXmafhKXFtGodtU9sRu", "bEbRffSy3KiGPJMatS2C", "2HfBJ0i10QBIffLp8lKV", "pk5DPjZKiWEX62kPPlX1", "hRnon9DnnPLOEsdhm8Ub", "rimv4mijjxmsrg21ETMq", "rY8JozrtWdluIyPC4hhW", "JJaPtB6IZxnRAmI6iHzx", "CpXmafhKXFtGodtU9sRu",};
    int key = 0;

    private Message globalMessage;
    private String channelStr = "";
    private TextChannel textChannelUse;
    private MyRunnable myRunnable;
    private Thread t;
    private int iteration;
    int questionStep = -1;

    private String[][] userEmojiAnswer;
    private ArrayList<String> acceptUsersIdStart = new ArrayList();
    private ArrayList<String> looseList = new ArrayList<>();

    String falseUsers = "";
    String trueUsers = "";
    String falseUsersInfinity = "";
    String trueUsersInfinity = "";


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
        for (int i = 300; i >= 0; i--) {
            iteration += 1;
            message.editMessage("```" + "\n" + "Викторина" + "\n" + "Осталось: " + i + "\n" + "Вы принимаете участие в викторине?" + "\n" + "```").queueAfter(iteration, TimeUnit.SECONDS);
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
//        acceptUsersIdStart.clear();
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

        for (int i = 11; i >= 0; i--) {
            iteration++;
            message.editMessage(msg + "\n" + "Осталось: " + i).queueAfter(iteration, TimeUnit.SECONDS);
        }
    }

    public void onResult(Event event) {
        System.out.println("Question number: " + questionStep);
        trueUsers = "";
        falseUsers = "";
//        for (String loose : looseList) {
//            for (String user : userEmojiAnswer[0]) {
//                if (loose.equalsIgnoreCase(user)) {
//                    System.out.println("Игрок " + getUserName(user, event) + " находится в looseList!");
//                } else {
//                    System.out.println("Игрок " + getUserName(user, event) + " не находится в looseList!");
//                }
//            }
//        }
        for (int i = 0; i < userEmojiAnswer[0].length; i++) {
            if (userEmojiAnswer[1][i] == null) {
                falseUsers = falseUsers + " " + getUserName(userEmojiAnswer[0][i], event);
                System.out.println("Игрок " + getUserName(userEmojiAnswer[0][i], event) + " не успел ответить!");
                looseList.add(userEmojiAnswer[0][i]);
                System.out.println("Игрок " + getUserName(userEmojiAnswer[0][i], event) + " добавлен в looseList!");
            } else if (userEmojiAnswer[1][i].equalsIgnoreCase(answerAndQuestion[1][questionStep])) {
                System.out.println("Игрок " + getUserName(userEmojiAnswer[0][i], event) + "ответил правильно!");
                trueUsers = trueUsers + " " + getUserName(userEmojiAnswer[0][i], event);
            } else {
                falseUsers = falseUsers + " " + getUserName(userEmojiAnswer[0][i], event);
                System.out.println("Игрок " + getUserName(userEmojiAnswer[0][i], event) + " ответил неправильно!");
                looseList.add(userEmojiAnswer[0][i]);
                System.out.println("Игрок " + getUserName(userEmojiAnswer[0][i], event) + " добавлен в looseList!");
            }
        }
        for (int i = 0; i < userEmojiAnswer[1].length; i++) {
            userEmojiAnswer[1][i] = null;
        }
        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Время истекло!" + "```")).queue();
    }

    public void startVictorInEnd(Event event) {
        ArrayList<String> winList = new ArrayList<>();
        ArrayList<String> looseListEnd = new ArrayList<>();

        boolean duplicated;
        boolean doWhile = false; //Никита, не бей. Костыли - движлк прогресса

        System.out.println("Ведём подсчёт!");
        for (String user : userEmojiAnswer[0]) {
            duplicated = false;


            for (String loose : looseList) {
                doWhile = true;
                duplicated = false;
                for (String win : winList) {
                    if (user.equalsIgnoreCase(win)) {
                        System.out.println(getUserName(user, event) + " игрок уже есть в массиве winList");
                        duplicated = true;
                    }
                }

                for (String loosse : looseListEnd) {
                    if (user.equalsIgnoreCase(loosse)) {
                        System.out.println(getUserName(user, event) + " игрок уже есть в массиве looseListEnd");
                        duplicated = true;
                    }
                }

                if (user.equalsIgnoreCase(loose) && !duplicated) {
                    System.out.println(getUserName(user, event) + " игрок был добавлен в список looseListEnd");
                    looseListEnd.add(user);
                } else if (!duplicated) {
                    System.out.println(getUserName(user, event) + " игрок был добавлен в список winList");
                    winList.add(user);
                }
            }
            if (!doWhile) {
                for (String users : userEmojiAnswer[0]) {
                    winList.add(users);
                }
            }
        }
        for (String looss : looseListEnd) {
            falseUsersInfinity = falseUsersInfinity + " " + getUserName(looss, event);
        }
        for (String win : winList) {
            trueUsersInfinity = trueUsersInfinity + " " + getUserName(win, event);
            for (Member member : textChannelUse.getMembers()) {
                if (win.equalsIgnoreCase(member.getId())) {
                    member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("Ты выиграл! " + keys[key])).queue();
                    System.out.println(getUserName(win, event) + " данному игроку был выдан ключ");
                    key++;
                }
            }
        }

        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Викторина окончена!" + "\n" + "Победители:" + trueUsersInfinity + "\n" + "Выбывшие:" + falseUsersInfinity + "\n" + "```")).queue();


//        if (win.equalsIgnoreCase(member.getId())) {
//            member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage("Ты выиграл!")).queue();
//            System.out.println(getUserName(user, event) + " данному игроку был выдан ключ");
//        }
        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Ключи были отправлены!" + "\n" + "```")).queue();
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
            Thread.sleep(310000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        giftBot.startVictorIn(event);
        giftBot.onStartBool = false;

        for (int i = 0; i < giftBot.answerAndQuestion[0].length; i++) {
            giftBot.questionStep++;
            giftBot.onNextQuestion(event);
            giftBot.onQuestionTime = true;
            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            giftBot.onQuestionTime = false;
            giftBot.onResult(event);
        }
        giftBot.startVictorInEnd(event);
        System.out.println("Викторина окончена!");
    }
}