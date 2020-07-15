package art.bot;

import net.dv8tion.jda.api.events.Event;

import java.sql.*;

public class ConnectionManager {
    String url = "jdbc:mysql://46.173.221.33:3306/user1002711_unionsecond?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "";
    String password = "";


    public void onConnectionBd() {
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

    public void onRemoveScore(String id, int score) {
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
            if (rsMoney.next()) {
                System.out.println(rsMoney.getString(1) + " Значение в бд на данный момент");
                money = Integer.parseInt(rsMoney.getString(1));
            }

            rsMoney.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE userId = '" + id + "';");

            if (rs.next()) {
                if(money >= score){
                    money = money - score;
                    statement.executeUpdate("UPDATE users SET money = '" + money + "' WHERE userId = " + id);
                    System.out.println("Покупка совершена успешно!");
                }else {
                    System.out.println("Покупка не совершена!");
                }
            } else {
                System.out.println("Юзера не существует: " + id + ". Добавляем!");
                statement.executeUpdate("INSERT INTO users (userId, money) VALUES('" + id + "','" + 0 + "');");
            }

//            if(rs == null){
//                System.out.println("Записи не существует");
//                System.out.println(rs.toString());
//            }else {
//                System.out.println("Запись существует");
//                System.out.println(rs.toString());
//            }
//            System.out.println("Sucsess!");
            System.out.println("Close!");
            rs.close();
            connection.close();
            statement.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMoney(String id){
        int money = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet rsMoney = statement.executeQuery("SELECT money FROM users WHERE userId = '" + id + "';");
            if (rsMoney.next()) {
                System.out.println(rsMoney.getString(1) + " Значение в бд на данный момент");
                money = Integer.parseInt(rsMoney.getString(1));
            }
            rsMoney.close();

            connection.close();
            statement.close();
            System.out.println("Close!");

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return money;
    }

    public void onAddScore(String id, int score) {
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
            if (rsMoney.next()) {
                System.out.println(rsMoney.getString(1) + " Значение в бд на данный момент");
                money = Integer.parseInt(rsMoney.getString(1));
                money += score;
            }

            rsMoney.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE userId = '" + id + "';");

            if (rs.next()) {
                statement.executeUpdate("UPDATE users SET money = '" + money + "' WHERE userId = " + id);
            } else {
                System.out.println("Юзера не существует: " + id + ". Добавляем!");
                statement.executeUpdate("INSERT INTO users (userId, money) VALUES('" + id + "','" + score + "');");
            }

//            if(rs == null){
//                System.out.println("Записи не существует");
//                System.out.println(rs.toString());
//            }else {
//                System.out.println("Запись существует");
//                System.out.println(rs.toString());
//            }
//            System.out.println("Sucsess!");
            System.out.println("Close!");
            rs.close();
            connection.close();
            statement.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
