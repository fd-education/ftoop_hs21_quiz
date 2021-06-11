package ch.ffhs.quiz.client.ui;

public class UserInterfaceUtils {

    public static void printLetterByLetter(String text, Delay delay){
        char[] letters = text.toCharArray();

        for(char letter: letters){
            try {
                System.out.print(letter);
                Thread.sleep(delay.getDuration());
            } catch(InterruptedException iEx){
                Thread.currentThread().interrupt();
            }
        }
    }

    enum Delay{
        FAST(20L),
        MEDIUM(50L),
        SLOW(100L),
        ZERO(0L);

        public final Long delay;

        Delay(Long delay){this.delay = delay;}

        public Long getDuration(){return delay;}
    }
}
