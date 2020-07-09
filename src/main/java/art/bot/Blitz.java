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
import java.util.concurrent.TimeUnit;

public class Blitz extends ListenerAdapter {
    private MyRunnableBlitz myRunnableBlitz;
    private Thread t;
    String[] words = {"Пивасик", "java", "wow"};
    boolean[] wordsBool;
    String textChannelString = "719317467117256794";
    TextChannel txtChannel;
    int wordStep = -1;
    private Message globalMessage;
    int starCount = 0;
    String word = "";
    String url = "jdbc:mysql://46.173.221.33:3306/user1002711_unionsecond?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "unionsecond";
    String password = "";


    public void onConnectionBd(Event event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INT NOT NULL AUTO_INCREMENT,\n" +
                    "                        userId VARCHAR(32),\n" +
                    "                        money INT,\n" +
                    "                        PRIMARY KEY(id)\n" +
                    "                        );");
//            for (Member member : txtChannel.getMembers()) {
//                ResultSet rs = statement.executeQuery("SELECT * FROM `users` WHERE userId = '" + member.getId() + "';");
//                int columns = rs.getMetaData().getColumnCount();
//                while (rs.next()) {
//                    for (int i = 2; i <= columns - 2; i++) {
//                        if (rs.getString(i).equalsIgnoreCase(member.getId())) {
//                            System.out.println("Ошибка! Юзер существует: " + rs.getString(i));
//                        } else {
//                            System.out.println("Юзера не существует: " + rs.getString(i) + ". Добавляем!");
//                            statement.executeUpdate("INSERT INTO users (userId, userName, money) VALUES('" + member.getId() + "','" + getUserName(member.getId(), event) + "','0');");
//                        }
//                    }
//                }
//            }
           // statement.executeUpdate("ALTER TABLE users CONVERT TO CHARACTER SET utf8mb4;");

//            for (Member member : txtChannel.getMembers()) {
//                ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE userId = '" + member.getId() + "';");
//                int columns = rs.getMetaData().getColumnCount();
//
//                if (rs.next()) {
//                    for (int i = 2; i <= columns - 2; i++) {
//                        System.out.println("Ошибка! Юзер существует: " + rs.getString(i));
//                    }
//                } else {
//                    System.out.println("Юзера не существует: " + ". Добавляем!");
//                    statement.executeUpdate("INSERT INTO users (userId, money) VALUES('" + member.getId() + "','0');");
//                }
//            }

//            if(rs == null){
//                System.out.println("Записи не существует");
//                System.out.println(rs.toString());
//            }else {
//                System.out.println("Запись существует");
//                System.out.println(rs.toString());
//            }
            System.out.println("Sucsess!");

            statement.close();
            connection.close();


        } catch (
                SQLException e) {
            e.printStackTrace();
        }

    }
    public void onAddScore(String id, int score){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INT NOT NULL AUTO_INCREMENT,\n" +
                    "                        userId VARCHAR(32),\n" +
                    "                        money INT,\n" +
                    "                        PRIMARY KEY(id)\n" +
                    "                        );");

            System.out.println("Sucsess!");
//            for (Member member : txtChannel.getMembers()) {
//                ResultSet rs = statement.executeQuery("SELECT * FROM `users` WHERE userId = '" + member.getId() + "';");
//                int columns = rs.getMetaData().getColumnCount();
//                while (rs.next()) {
//                    for (int i = 2; i <= columns - 2; i++) {
//                        if (rs.getString(i).equalsIgnoreCase(member.getId())) {
//                            System.out.println("Ошибка! Юзер существует: " + rs.getString(i));
//                        } else {
//                            System.out.println("Юзера не существует: " + rs.getString(i) + ". Добавляем!");
//                            statement.executeUpdate("INSERT INTO users (userId, userName, money) VALUES('" + member.getId() + "','" + getUserName(member.getId(), event) + "','0');");
//                        }
//                    }
//                }
//            }
            // statement.executeUpdate("ALTER TABLE users CONVERT TO CHARACTER SET utf8mb4;");

            ResultSet rsMoney = statement.executeQuery("SELECT money FROM users WHERE userId = '" + id + "';");
            int money = 0;
            if(rsMoney.next()){
                System.out.println(rsMoney.getString(1) + " Значение в бд на данный момент");
                money = Integer.parseInt(rsMoney.getString(1));
                money += score;
            }

            rsMoney.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE userId = '" + id + "';");

            if (rs.next()) {
                    statement.executeUpdate("UPDATE users SET money = '" + money + "' WHERE userId = " + id);
            }else {
                System.out.println("Юзера не существует: " + id + ". Добавляем!");
                statement.executeUpdate("INSERT INTO users (userId, money) VALUES('" + id + "','0');");
            }

//            if(rs == null){
//                System.out.println("Записи не существует");
//                System.out.println(rs.toString());
//            }else {
//                System.out.println("Запись существует");
//                System.out.println(rs.toString());
//            }
//            System.out.println("Sucsess!");
            System.out.println("Close   !");
            rs.close();
            connection.close();
            statement.close();


        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {

        txtChannel = event.getJDA().getTextChannelById(textChannelString);
//        onConnectionBd(event);
        wordsBool = new boolean[words.length];
        myRunnableBlitz = new MyRunnableBlitz(event, this);
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
                    onAddScore(event.getMember().getId(), score);
                } else {
                    txtChannel.sendMessage(event.getMember().getEffectiveName() + " , Такое слово уже отгадали!").complete();
                }
        }
    }
}
