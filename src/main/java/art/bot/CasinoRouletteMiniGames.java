package art.bot;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CasinoRouletteMiniGames extends ListenerAdapter {
    private ConnectionManager connectionManager;
    public boolean isGame;
    private Member memberInGame;
    private Message globalMessage;
    ArrayList<String> randomColorList = new ArrayList();
    float interval = 0.5f;
    float intervalSpeed = 0.5f;
    int winnerMoney = 0;


    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        connectionManager = new ConnectionManager();
//        new Thread(this::sleepMethod).start(); - true method
        //new Thread(() -> {code code code}).start();
        System.out.println(isGame);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
//            int score = 5;
//            connectionManager.onRemoveScore(event.getMember().getId(),score);

        String[] message = event.getMessage().getContentRaw().split(" ");
        // System.out.println(message.length);
        if (!isGame && !event.getAuthor().isBot()) {
            if (event.getChannel().getId().equalsIgnoreCase("731254314907074681") && message[0].equalsIgnoreCase("!roll") && message.length == 3) {
                if (message[1].matches("[-+]?\\d+")) {
                    if (Integer.parseInt(message[1]) <= 0) {
                        event.getChannel().sendMessage("Ошибка! Число должно быть больше нуля!").queue();
                        return;
                    }
                    if (message[2].equalsIgnoreCase("red") || message[2].equalsIgnoreCase("black") || message[2].equalsIgnoreCase("green")) {
                        int money = 0;
                        winnerMoney = Integer.parseInt(message[1]);
                        money = connectionManager.getMoney(event.getMember().getId());
                        if (money >= Integer.parseInt(message[1])) {
                            memberInGame = event.getMember();
                            isGame = true;
                            interval = 0.5f;
                            intervalSpeed = 0.5f;
                            String rouletteString = "";
                            memberInGame = event.getMember();
                            new Thread(this::sleepMethod).start();
                            randomColorList = randomList();
                            globalMessage = event.getChannel().sendMessage("Рулетка: " + "\n" + "Load" + "\n" + "Load" + "\n" + "Load").complete();
                            for (int i = 0; i < 21; i++) {
                                interval += intervalSpeed;
                                intervalSpeed += 0.05f;
                                rouletteString = "";
                                for (int c = 0; c < 11; c++) {
                                    if (c == 5) {
                                        rouletteString = rouletteString + "\n" + "︱" + randomColorList.get(c) + "◄";
                                    } else {
                                        rouletteString = rouletteString + "\n" + "︱" + randomColorList.get(c) + "︱";
                                    }
                                }
                                globalMessage.editMessage("Рулетка: " + "\n" + rouletteString + "\n").queueAfter((int) interval, TimeUnit.SECONDS);
                                if (i == 20) {
                                    break;
                                }
                                randomColorList.remove(0);
//                                System.out.println(randomColorList.get(5));
                            }
                            System.out.println(randomColorList.size());
                            if (onRacString(randomColorList.get(5)).equalsIgnoreCase(message[2])) {
                                if (message[2].equalsIgnoreCase("green")) {
                                    money += winnerMoney * 10;
                                    event.getChannel().sendMessage("Успех! Теперь у вас на балансе: " + money).queueAfter(25, TimeUnit.SECONDS);
                                    connectionManager.onAddScore(event.getMember().getId(), winnerMoney * 10);
                                } else {
                                    money += winnerMoney * 2;
                                    event.getChannel().sendMessage("Успех! Теперь у вас на балансе: " + money).queueAfter(25, TimeUnit.SECONDS);
                                    connectionManager.onAddScore(event.getMember().getId(), winnerMoney * 2);
                                }
                            } else {
                                money -= winnerMoney;
                                event.getChannel().sendMessage("К сожалению вы проиграли :( теперь у вас на балансе: " + money).queueAfter(25, TimeUnit.SECONDS);
                                connectionManager.onRemoveScore(event.getMember().getId(), winnerMoney);
                            }
                        } else {
                            event.getChannel().sendMessage("Ошибка! У вас недостаточно средств! В данный момент у вас на балансе: " + money).queue();
                        }
                    } else {
                        event.getChannel().sendMessage("Ошибка! Доступные цвета: Red, Black, Green").complete();
                    }
                } else {
                    event.getChannel().sendMessage("В качестве аргумента могут быть использованы только числа").complete();
                }

            } else if (event.getChannel().getId().equalsIgnoreCase("731254314907074681") && message[0].equalsIgnoreCase("!roll") && message.length < 3) {
                event.getChannel().sendMessage("Ошибка! Правильное использование команды: !roll ставка цвет").complete();
            }
        } else if (!event.getAuthor().isBot()) {
            event.getChannel().sendMessage("Ошибка! Игрок " + memberInGame.getEffectiveName() + " уже играет, попробуйте позже!").complete();
        }
    }

    public void sleepMethod() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isGame = false;
        System.out.println(isGame);
    }

    public String onRacString(String color) {
        String rightColor = "";

        if (color.equalsIgnoreCase(":red_square:")) {
            rightColor = "red";
        } else if (color.equalsIgnoreCase(":green_square:")) {
            rightColor = "green";
        } else {
            rightColor = "black";
        }

        return rightColor;

    }

    public ArrayList randomList() {

        int random = 0;
        int randomSecond = 0;

        Random r = new Random();
        random = r.nextInt((2 - 1) + 1) + 1;
        System.out.println(random);

        randomSecond = r.nextInt((30 - 1) + 1) + 1;
        System.out.println(randomSecond + " rs");
        //:green_square:

        ArrayList<String> randomLst = new ArrayList();
        for (int i = 0; i < 31; i++) {
            if (i == randomSecond) {
                randomLst.add(":green_square:");
            } else {

                if (random == 1) {
                    randomLst.add(":red_square:");
                    randomLst.add(":black_large_square:");
                } else {
                    randomLst.add(":black_large_square:");
                    randomLst.add(":red_square:");
                }
            }
        }

        return randomLst;
    }
}
