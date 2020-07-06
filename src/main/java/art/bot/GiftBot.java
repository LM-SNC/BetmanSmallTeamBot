package art.bot;

import net.dv8tion.jda.api.entities.Message;
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

    Message globalMessage;
    String channelStr = "719317467117256794";
    MyRunnable myRunnable;
    Thread t;
    int iteration;


    public ArrayList<String> acceptUsersId = new ArrayList();
    public ArrayList<UsersMassInformation> updateAcceptUserId = new ArrayList();
    public ArrayList<String> nonActiveList = new ArrayList();

    public ArrayList<String> reactionUserId = new ArrayList();
    public ArrayList<String> reactionEmojiId = new ArrayList();

    public boolean onStartBool;
    int questionStep;
    boolean onQuestionTime;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
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
        System.out.println("Count accept user: " + acceptUsersId.size());
//        String onlineList = "";
//        for (int i = 0; i < event.getJDA().getTextChannelById(channelStr).getMembers().size(); i++){
//            if(event.getJDA().getTextChannelById(channelStr).getMembers().get(i).getOnlineStatus().equals(OnlineStatus.ONLINE)){
//                onlineList = onlineList + ", " + event.getJDA().getTextChannelById(channelStr).getMembers().get(i).getEffectiveName();
//            }
//        }
        //event.getJDA().getTextChannelById(channelStr).getMembers().forEach(member -> member.getPermissions().remove(Permission.MESSAGE_ADD_REACTION));Удаляем права
        String acceptListString = "";
        System.out.println(acceptUsersId.size());
        for (int i = 0; i < event.getJDA().getTextChannelById(channelStr).getMembers().size(); i++) {
            for (int b = 0; b < acceptUsersId.size(); b++) {
                if (event.getJDA().getTextChannelById(channelStr).getMembers().get(i).getId().equalsIgnoreCase(acceptUsersId.get(b))) {
                    acceptListString = acceptListString + " " + event.getJDA().getTextChannelById(channelStr).getMembers().get(i).getEffectiveName();
                }
            }
        }

        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Викторина началсь!" + "\n" + "Приняли участие:" + acceptListString + "\n" + "```")).complete();
        //event.getJDA().getTextChannelById(channelStr).sendMessage("Не успели принять участие: " + onlineList).queue();

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
        System.out.println("questionNumber: " + questionStep);
        String trueUser = "";
        String falseUser = "";
        boolean isDuplicated = false;
        int[] a1 = {2, 4, 5, 7};
        int[] a2 = {2, 4, 5};
        ArrayList a3 = new ArrayList();
        for (int i = 0; i < acceptUsersId.size(); i++) {
            System.out.println(acceptUsersId.get(i) + " acceptUsersId ");
        }
        for (int j = 0; j < reactionUserId.size(); j++) {
            System.out.println(reactionEmojiId.get(j) + " reactionUserId ");
        }

        isDuplicated = false;
        for (int i = 0; i < acceptUsersId.size(); i++) {
            isDuplicated = false;
            for (int j = 0; j < reactionUserId.size(); j++) {
                if (acceptUsersId.get(i).equals(reactionUserId.get(j))) {
                    isDuplicated = true;
                }
            }
            if (!isDuplicated) {
                nonActiveList.add(acceptUsersId.get(i));
                for (int sucs = 0; sucs < event.getJDA().getTextChannelById(channelStr).getMembers().size(); sucs++) {
                    for (int usersuc = 0; usersuc < acceptUsersId.size(); usersuc++) {
                        if (event.getJDA().getTextChannelById(channelStr).getMembers().get(sucs).getId().equalsIgnoreCase(acceptUsersId.get(usersuc))) {
                            falseUser = falseUser + " " + event.getJDA().getTextChannelById(channelStr).getMembers().get(sucs).getEffectiveName();
                        }
                    }
                }
                acceptUsersId.remove(i);

            }
            for (int react = 0; react < reactionUserId.size(); react++) {
                if (reactionEmojiId.get(react).equalsIgnoreCase(answerAndQuestion[1][questionStep])) {
                    System.out.println("Успех!" + reactionEmojiId.get(react));
                    for (int sucs = 0; sucs < event.getJDA().getTextChannelById(channelStr).getMembers().size(); sucs++) {
                        for (int usersuc = 0; usersuc < reactionUserId.size(); usersuc++) {
                            if (event.getJDA().getTextChannelById(channelStr).getMembers().get(sucs).getId().equalsIgnoreCase(reactionUserId.get(usersuc))) {
                                trueUser = trueUser + " " + event.getJDA().getTextChannelById(channelStr).getMembers().get(sucs).getEffectiveName();
                            }
                        }
                    }

                } else {
                    System.out.println("Ошибка!(remove)" + reactionEmojiId.get(react));
                    for (int sucs = 0; sucs < event.getJDA().getTextChannelById(channelStr).getMembers().size(); sucs++) {
                        for (int usersuc = 0; usersuc < reactionUserId.size(); usersuc++) {
                            if (event.getJDA().getTextChannelById(channelStr).getMembers().get(sucs).getId().equalsIgnoreCase(reactionUserId.get(usersuc))) {
                                falseUser = falseUser + " " + event.getJDA().getTextChannelById(channelStr).getMembers().get(sucs).getEffectiveName();
                            }
                        }
                    }
                    acceptUsersId.remove(i);
                }
            }
        }
        for (int i = 0; i < nonActiveList.size(); i++) {
            System.out.println(nonActiveList.get(i) + "clearForDiff");
        }
        event.getJDA().getTextChannelById(channelStr).sendMessage(("```" + "\n" + "Время истекло!" + "\n" + "Правильно ответили:" + trueUser + "\n" + "Выбыли:" + falseUser + "\n" + "```")).queue();
        reactionUserId.clear();
        reactionEmojiId.clear();
        nonActiveList.clear();
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if (event.getReaction().getReactionEmote().getId().equalsIgnoreCase("729000468730085426") && !event.getUser().isBot() && onStartBool) {
            for (int i = 0; i < acceptUsersId.size(); i++) {
                if (event.getMember().getId().equalsIgnoreCase(acceptUsersId.get(i))) {
                    System.out.println(acceptUsersId.get(i) + " уже есть  массиве!");
                    return;
                }
            }
            String id = event.getMember().getId();
            for (UsersMassInformation usersMassInformation : updateAcceptUserId) {
                if (!usersMassInformation.userId.equalsIgnoreCase(id)) {

                }
            }
            UsersMassInformation usersMassInformation = new UsersMassInformation(id);
            updateAcceptUserId.add(usersMassInformation);

            acceptUsersId.add(event.getMember().getId());
        }
        if (!event.getUser().isBot() && onQuestionTime && event.getMessageId().equalsIgnoreCase(globalMessage.getId())) {
            for (int i = 0; i < acceptUsersId.size(); i++) {
                for (int b = 0; b < reactionUserId.size(); b++) {
                    if (acceptUsersId.get(i).equalsIgnoreCase(reactionUserId.get(b))) {
                        reactionEmojiId.set(b, event.getReactionEmote().getId());
//                        System.out.println(event.getReactionEmote().getId() + " эмодзи был успешно изменен!");
//                        System.out.println(event.getMember().getId() + " игрок уже есть в массиве!");
                        return;
                    }
                }
                reactionEmojiId.add(event.getReactionEmote().getId());
//                System.out.println(event.getReactionEmote().getId() + " эмодзи был учпешно добавлен!");
                System.out.println(i);

                reactionUserId.add(event.getMember().getId());
//                System.out.println(event.getMember().getId() + " игрок был учпешно добавлен!");
                System.out.println(i);
            }
            String memberReactionId = event.getMember().getId();
            System.out.println("GiftBot::onMessageReactionAdd(); --Thread.currentThread(): " + Thread.currentThread());

            for (UsersMassInformation usersMassInformation : updateAcceptUserId) {
                System.out.println("GiftBot::onMessageReactionAdd(); --usersMassInformation: " + usersMassInformation);
                if (usersMassInformation.userId.equalsIgnoreCase(memberReactionId)) {
                    usersMassInformation.userInfo.put(questionStep, event.getMember().getId());
                    System.out.println("GiftBot::onMessageReactionAdd(); --userId: " + usersMassInformation.userId);
                    System.out.println("GiftBot::onMessageReactionAdd(); --userInfo: " + usersMassInformation.userInfo);
                    System.out.println("GiftBot::onMessageReactionAdd(); --questionStep: " + questionStep);
                    System.out.println("GiftBot::onMessageReactionAdd(); --reactionId: " + event.getReaction().getReactionEmote().getId());

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
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        giftBot.startVictorIn(event);
        giftBot.onStartBool = false;

        for (int i = 0; i < giftBot.answerAndQuestion.length; i++) {
            giftBot.onNextQuestion(event);
            giftBot.onQuestionTime = true;
            try {
                Thread.sleep(15000);
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