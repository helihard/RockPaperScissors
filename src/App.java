import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Will human outsmart computer in a game of");
        System.out.println("ROCK : PAPER : SCISSORS\n");
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        game.play();
        scanner.close();
    }
}
