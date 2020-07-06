package art.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class DyntaxCode extends ListenerAdapter {
    boolean isChange;
    String memberIdIsChange;
    String comm;
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        /*
        String[] message = event.getMessage().getContentRaw().split("");
        String code = "";
        String code1 = "";
        if(event.getChannel().getId().equals("721702362195951626") && !event.getAuthor().isBot() && message[0].equalsIgnoreCase("!с++")) {
            System.out.println("StartedReplaceWithSyntax");
                if (message[0].equalsIgnoreCase("!smws") && message.length == 1) {
                    System.out.println("Ошибка! Для использования введите: !smws [syntax type] [code]");
                }else if(message[0].equalsIgnoreCase("!smws") && message[1].equalsIgnoreCase("css")){
                    for(int i = 2; i < message.length; i++) {
                        code = code + message[i];
                    }
                    System.out.println(code);
                    event.getChannel().sendMessage(code).complete();
                }
        }
        */
        String[] message = event.getMessage().getContentRaw().split(" ");
        if(event.getChannel().getId().equalsIgnoreCase("608044503575560202") && message[0].equalsIgnoreCase("!synt") && message.length != 1){
            memberIdIsChange = event.getMember().getId();;
            isChange = true;
            comm = message[1];
            event.getChannel().sendMessage("Отлично! Теперь напишите ваш код!").complete();
        }else if(event.getChannel().getId().equalsIgnoreCase("608044503575560202") && event.getMember().getId().equalsIgnoreCase(memberIdIsChange) && isChange){
            event.getChannel().sendMessage("Message with syntax by " + event.getMember().getEffectiveName() + "\n" + "```" + comm.replace("!","") + "\n" + event.getMessage().getContentDisplay() + "\n" + "```").complete();
            isChange = false;
            System.out.println(message[0].replace("!",""));
        }else if(event.getChannel().getId().equalsIgnoreCase("608044503575560202") && message[0].equalsIgnoreCase("!synt") && message.length == 1){
            event.getChannel().sendMessage("Ошибка! Правильное использование команды: !synt [язык]").complete();
        }
        //event.getChannel().sendMessage("```java" + "\n" + event.getMessage().getContentDisplay() + "\n" + "```").complete();
    }
}
