import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

//public class RR  extends ListenerAdapter {
//
//    int randomIntFirst;
//    int randomIntSecond;
//    boolean isGame;
//
//    @Override
//    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
//        if(event.getTextChannel().getId().equals("721691783314014208") && event.getMessage().getContentDisplay().equals("!roulette") && !isGame || event.getTextChannel().getId().equals("721695653083742249") && event.getMessage().getContentDisplay().equals("!roulette") && !isGame){
//
//            isGame = true;
//            System.out.println("isGame = " + isGame);
//            System.out.println("Started randoming!");
//            randomIntFirst = (int) (Math.random() * 7);
//            event.getTextChannel().sendMessage(event.getMember().getEffectiveName() + ", Если выпадает число " + randomIntFirst + ", то вы будете убиты!").queueAfter(2,TimeUnit.SECONDS);
//            randomIntSecond = (int) (Math.random() * 7);
//            event.getTextChannel().sendMessage( "```basic" + "\n" + randomIntSecond + "︱Раскручиваем барабан, подносим к виску" + "\n" + "```").queueAfter(4,TimeUnit.SECONDS);
//            randomIntSecond = (int) (Math.random() * 7);
//            event.getTextChannel().sendMessage( "```basic" + "\n" + randomIntSecond + "︱Спускаем курок" +  "\n" + "```").queueAfter(6,TimeUnit.SECONDS);
//            randomIntSecond = (int) (Math.random() * 7);
//            if(randomIntSecond == randomIntFirst){
//                event.getTextChannel().sendMessage( "```basic" + "\n" + randomIntSecond + "︱Бах!" + "\n" + "```").queueAfter(8,TimeUnit.SECONDS);
//                isGame = false;
//            }else{
//                event.getTextChannel().sendMessage( "```basic" + "\n" + randomIntSecond + "︱Слышен щелчок, похоже вам везёт!" + "\n" + "```").queueAfter(10,TimeUnit.SECONDS);
//                isGame = false;
//            }
//        }
//        if (event.getTextChannel().getId().equals("721691783314014208") && !event.getAuthor().isBot() && isGame){
//            event.getMessage().delete().complete();
//            System.out.println("Delete message!");
//        }
//
//        if(event.getTextChannel().getId().equals("721691783314014208") && event.getMessage().getContentDisplay().equals("!clear") || event.getTextChannel().getId().equals("721695653083742249") && event.getMessage().getContentDisplay().equals("!clear")) {
//            System.out.println("Clear all messages!");
//            for (Message mes : event.getChannel().getIterableHistory()) {
//                if(!mes.getId().equals("721691938037825578") || !mes.getId().equals("721696655476391966")) {
//                    event.getChannel().deleteMessageById(mes.getId()).queue();
//                }
//            }
//        }
//    }
//
//    public void falseGame(){
//        isGame = false;
//    }
//
//    @Override
//    public void onReady(@Nonnull ReadyEvent event) {
//    }
//
//
//}
