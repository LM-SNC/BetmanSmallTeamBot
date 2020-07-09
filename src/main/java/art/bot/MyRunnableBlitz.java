package art.bot;

import net.dv8tion.jda.api.events.Event;

class MyRunnableBlitz implements Runnable {

    Event event;
    Blitz blitz;

    public MyRunnableBlitz(Event event, Blitz blitz) {
        this.event = event;
        this.blitz = blitz;
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < blitz.words.length; i++) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blitz.wordStep++;
            blitz.onNextWord(event);

            System.out.println("blitz.words[blitz.wordStep]: " + blitz.words[blitz.wordStep].length());
            for(int b = 0; b < blitz.words[blitz.wordStep].length(); b++) {
                if(blitz.wordsBool[i]){
                    break;
                }
                blitz.onOpenWord();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blitz.onExtremeOpenWord();
        }
    }
}
