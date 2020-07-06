package art.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


public class Bot {

    public static void main(String[] args) throws Exception {
        String active = "BSTeam - discord.gg/xp2KrvD";
        JDA jda = new JDABuilder(".XuU5KA.tSEpuIXzUaEJMN84gpwo_NIK5fI")
                .addEventListeners(new MessageHandler())
               // .addEventListeners(new RR())
                .addEventListeners(new DyntaxCode())
               // .addEventListeners(new CreateChannel())
                .addEventListeners(new GiftBot())
                .setActivity(Activity.playing(active))
                .build();


        System.out.println("BetmanSmallBot successful started");

        while(true){
            //doSomething
            Thread.sleep(60000);
        }
    }
}
