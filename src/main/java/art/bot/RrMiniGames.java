//package art.bot;
//
//import net.dv8tion.jda.api.entities.Message;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//
//import java.util.concurrent.TimeUnit;
//
//public class RrMiniGames extends ListenerAdapter {
//
//    int randomIntFirst;
//    int randomIntSecond;
//    boolean isGame;
//
//    public void onMessageReceived(MessageReceivedEvent event) {
//
//        String[] message = event.getMessage().getContentRaw().split(" ");
//        // System.out.println(message.length);
//        if (!isGame && !event.getAuthor().isBot()) {
//            if (event.getChannel().getId().equalsIgnoreCase("731254314907074681") && message[0].equalsIgnoreCase("!roll") && message.length == 3) {
//                if (message[2].matches("[-+]?\\d+")) {
//                    isGame = true;
//                    float interval = 0.5f;
//                    float intervalSpeed = 0.5f;
//                    new Thread(this::sleepMethod).start();
//                    String rouletteString = "";
//                    if (message[1].matches("[-+]?\\d+")) {
//                        new Thread(this::sleepMethod).start();
//                        //код в случаае успеха
//                    } else {
//                        event.getChannel().sendMessage("В качестве аргумента[1] могут быть использованы только цифры").complete();
//                    }
//                } else {
//                    event.getChannel().sendMessage("В качестве аргумента[2] могут быть использованы только цифры").complete();
//                }
//
//            } else if (event.getChannel().getId().equalsIgnoreCase("731254314907074681") && message[0].equalsIgnoreCase("!roll") && message.length < 3) {
//                event.getChannel().sendMessage("Ошибка! Правильное использование команды: !roll ставка(1 - 25) цифра(1 - 6)").complete();
//            }
//        } else if (!event.getAuthor().isBot()) {
//            event.getChannel().sendMessage("Ошибка! Игрок " + "Unkown" + " уже играет, попробуйте позже!").complete();
//        }
//
//        if (event.getTextChannel().getId().equals("721691783314014208") && !event.getAuthor().isBot() && isGame) {
//            event.getMessage().delete().complete();
//            System.out.println("Delete message!");
//        }
//
////        if(event.getTextChannel().getId().equals("721691783314014208") && event.getMessage().getContentDisplay().equals("!clear") || event.getTextChannel().getId().equals("721695653083742249") && event.getMessage().getContentDisplay().equals("!clear")) {
////            System.out.println("Clear all messages!");
////            for (Message mes : event.getChannel().getIterableHistory()) {
////                if(!mes.getId().equals("721691938037825578") || !mes.getId().equals("721696655476391966")) {
////                    event.getChannel().deleteMessageById(mes.getId()).queue();
////                }
////            }
////        }
//    }
//
//    public void sleepMethod() {
//        try {
//            Thread.sleep(25000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        isGame = false;
//        System.out.println(isGame);
//    }
//}
