import java.util.Random;
import java.util.Scanner;

public class App {

    // helper method: get valid playername to initialise game
    private static String getValidPlayerName(Scanner scanner) {
        String playerName = "";
        boolean isValidInput = false;

        while (!isValidInput) {
            playerName = scanner.nextLine().trim();

            if (playerName.isEmpty()) {
                System.out.println("You need to state a name. Try again.");
            } else {
                isValidInput = true;
            }
        }

        return playerName;
    }

    // helper method: get valid number of rounds to initialise game
    private static int getValidNumberOfRounds(Scanner scanner) {
        int numberOfRounds = -1;
        boolean isValidInput = false;

        System.out.println("\nTime to chose number of rounds:");

        System.out.println("M: I choose");
        System.out.println("3");
        System.out.println("C: Computer chooses");

        while (!isValidInput) {
            //System.out.println("You must make a choice between M, 3 or C. Try again.");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("m")) {
                isValidInput = true;
                System.out.println("Choose between 1, 3 or 5 rounds.");
                while (numberOfRounds != 1 && numberOfRounds != 3 && numberOfRounds != 5) {
                    try { 
                        System.out.println("Enter 1, 3 or 5.");
                        numberOfRounds = Integer.parseInt(scanner.nextLine().trim());

                        if (numberOfRounds != 1 && numberOfRounds != 3 && numberOfRounds != 5) {
                            System.out.println("Invalid choice. Enter either 1, 3 or 5.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Enter a number.");
                    }
                }
                if (numberOfRounds == 1) {
                    System.out.println("You choose. You play against the computer for best of 1 round.\n");
                } else {
                    System.out.println(String.format("You choose. You play against the computer for best of %s rounds.\n", numberOfRounds));
                }
            } else if (input.equals("3")) {
                isValidInput = true;
                numberOfRounds = 3;
                System.out.println(String.format("You play against the computer for best of %s rounds.\n", numberOfRounds));
            } else if (input.equalsIgnoreCase("c")) {
                isValidInput = true;
                int oddRounds[] = new int[] {1, 3, 5};
                numberOfRounds = oddRounds[new Random().nextInt(oddRounds.length)];
                if (numberOfRounds == 1) {
                    System.out.println("Computer chooses. You play against the computer for best of 1 round.\n");
                } else {
                    System.out.println(String.format("Computer chooses. You play against the computer for best of %s rounds.\n", numberOfRounds));
                }
            } else {
                System.out.println("Invalid input. You must make a choice between M, 3 or C.");
            }
        }
        return numberOfRounds;
    }

    // helper method
    private static String getValidShot(Scanner scanner) {
        String shot = "";
        boolean isValidInput = false;

        while (!isValidInput) {
            shot = scanner.nextLine().trim();

            if (shot.isEmpty()) {
                System.out.println("You need to make a choice between R (rock), P (paper) or S (scissors). Try again.");
            } else if (!shot.equalsIgnoreCase("r") && !shot.equalsIgnoreCase("p") && !shot.equalsIgnoreCase("s")) {
                System.out.println("Invalid input. Choose between R (rock), P (paper) or S (scissors).");
            } else {
                isValidInput = true;
            }
        }
        if (shot.equalsIgnoreCase("r")) {
            shot = "rock";
        } else if (shot.equalsIgnoreCase("p")) {
            shot = "paper";
        } else if (shot.equalsIgnoreCase("s")) {
            shot = "scissors";
        }
        return shot;
    }

    private static void play(Scanner scanner) {
        System.out.println("Human, state your name.");
        String humanName = getValidPlayerName(scanner);
        Human human = new Human(humanName);

        Computer computer = new Computer();
        
        int numberOfRounds = getValidNumberOfRounds(scanner);

        playRound(scanner, human, computer, numberOfRounds);

        getWinner(human, computer);
    }

    private static void playRound(Scanner scanner, Human human, Computer computer, int numberOfRounds) {
        
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("----- ROUND : " + (i + 1) + " -----");
            System.out.println(human.getName() + ", throw hand:");
            System.out.println("R: rock");
            System.out.println("P: paper");
            System.out.println("S: scissors");
            String humanShot = getValidShot(scanner);
            System.out.println("You throw " + humanShot);

            String computerShot = computer.shoot();
            System.out.println("Computer throws " + computerShot + ":");

            while (humanShot.equalsIgnoreCase(computerShot)) {
                System.out.println(String.format("Same throw. %s, try again.", human.getName()));
                humanShot = getValidShot(scanner);
                System.out.println("You throw " + humanShot);
                computerShot = computer.shoot();
                System.out.println(String.format("Computer throws %s:", computerShot));
            }

            if (humanShot.equals("rock") && computerShot.equals("scissors") || 
            humanShot.equals("scissors") && computerShot.equals("paper") ||
            humanShot.equals("paper") && computerShot.equals("rock")) {
                human.increaseScore();
                System.out.println(human.getName() + " wins this round.");
            } else if (humanShot.equals("rock") && computerShot.equals("paper") ||
            humanShot.equals("scissors") && computerShot.equals("rock") ||
            humanShot.equals("paper") && computerShot.equals("scissors")) {
                computer.increaseScore();
                System.out.println(computer.getName() + " wins this round.");
            }
  
            if (i < numberOfRounds - 1) {
            System.out.println(String.format("Human–Computer: %s-%s\n", human.getScore(), computer.getScore()));
            }
        }
    }

    public static void getWinner(Human human, Computer computer) {
        System.out.println("\n----- G A M E : O V E R -----");
        if (human.getScore() > computer.getScore()) {
            System.out.println("Human wins this game.");
        } else {
            System.out.println("Computer wins this game.");
        }
        System.out.println(String.format("Final score: Human–Computer: %s-%s", human.getScore(), computer.getScore()));
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Will human outsmart computer in a game of");
        System.out.println("ROCK : PAPER : SCISSORS\n");

        play(scanner);
        scanner.close();
    }
}
