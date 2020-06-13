import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.util.concurrent.TimeUnit;

public class Bot {
    public static void main(String[] args) throws Exception{
        String active = "BSTeam - discord.gg/xp2KrvD";
        JDA jda = new JDABuilder("NzIwNzUyNjIyODI2NDIyNDAz.XuPsBg.jbcItYSfgkZYpIS3itFKPRD4gYw")
                .addEventListeners(new MessageHandler())
                .setActivity(Activity.playing(active))
                .build();


        System.out.println("BetmanSmallBot successful started");
    }
}
