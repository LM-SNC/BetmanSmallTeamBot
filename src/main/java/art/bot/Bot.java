package art.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


public class Bot {

    public static void main(String[] args) throws Exception {
        String active = "BSTeam - discord.gg/xp2KrvD";
        JDA jda = new JDABuilder(".XwWUBA.72Sgcz6ALcFL94pnhvyOhe7EmK4")
                .addEventListeners(new MessageHandler())
               // .addEventListeners(new RR())
                .addEventListeners(new DyntaxCode())
               // .addEventListeners(new CreateChannel())
                .addEventListeners(new Blitz())
                .setActivity(Activity.playing(active))
                .build();


        System.out.println("BetmanSmallBot successful started");

        while(true){
            //doSomething
            Thread.sleep(60000);
        }
    }
}
