import java.util.Scanner;
import java.util.Random;

public class Game {
  private final Scanner scanner;
  private final Human human;
  private final Computer computer;

  // constructor
  public Game(Scanner scanner) {
    this.scanner = scanner;
    System.out.println("Human, state your name.");
    String playerName = getValidPlayerName();
    this.human = new Human(playerName);
    this.computer = new Computer();
  }
  
  // get valid human name (error handling)
  private String getValidPlayerName() {
    String playerName = "";

    while (playerName.isEmpty()) {
      playerName = scanner.nextLine().trim();
      if (playerName.isEmpty()) {
        System.out.println("You need to state a name. Try again.");
      } 
    }
    return playerName;
  }

  public void play() {
    int numberOfRounds = getValidNumberOfRounds();
    playRounds(numberOfRounds);
    displayWinner();
  }

  // get number of rounds (with error handling)
  private int getValidNumberOfRounds() {
    int numberOfRounds = -1;

    System.out.println("\nTime to choose number of rounds:");
    System.out.println("M: I choose a number");
    System.out.println("3: A classic");
    System.out.println("C: Computer chooses a number");

    while (numberOfRounds == -1) {
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("m")) {
        numberOfRounds = chosenNumberOfRounds();
      } else if (input.equals("3")) {
        numberOfRounds = 3;
      } else if (input.equalsIgnoreCase("c")) {
        numberOfRounds = getRandomNumberOfRounds();
      } else {
        System.out.println("Invalid input. You must make a choice between M, 3 or C.");
      }
    }

    if (numberOfRounds == 1) {
      System.out.println("You play against the computer for best of 1 round.\n");
    } else {
      System.out.println(String.format("You play against the computer for best of %s rounds.\n", numberOfRounds));
    }
    return numberOfRounds;
  }

  // get valid number of rounds from option "M" (with error handling)
  private int chosenNumberOfRounds() {
    int numberOfRounds = -1;

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
    return numberOfRounds;           
  }

  // get random number of rounds from option "C"
  private int getRandomNumberOfRounds() {
    final int oddRounds[] = new int[] {1, 3, 5};
    int numberOfRounds = oddRounds[new Random().nextInt(oddRounds.length)];
    return numberOfRounds;
  }

  private void playRounds(int numberOfRounds) {
    for (int i = 0; i < numberOfRounds; i++) {
      System.out.println("----- ROUND : " + (i + 1) + " -----");
      System.out.println(human.getName() + ", throw hand:");
      System.out.println("R: rock");
      System.out.println("P: paper");
      System.out.println("S: scissors");
      
      String humanShot = getValidShot();
      String computerShot = computer.shoot();

      while (humanShot.equals(computerShot)) {
        displayShots(humanShot, computerShot);
        System.out.println(String.format("Same throw. %s, try again.", human.getName()));
        humanShot = getValidShot();
        computerShot = computer.shoot();
      }

      displayShots(humanShot, computerShot);

      if (isHumanRoundWinner(humanShot, computerShot)) {
        human.increaseScore();
        System.out.println(human.getName() + " wins this round.");
      } else {
        computer.increaseScore();
        System.out.println(computer.getName() + " wins this round.");
      }

      if (i < numberOfRounds - 1) {
        displayRoundScore(); 
      }
    }
  }

  // get valid human shot (error handling)
  private String getValidShot() {
    String shot = "";
      
    while ((!shot.equalsIgnoreCase("r") && !shot.equalsIgnoreCase("p") && !shot.equalsIgnoreCase("s"))) {
      shot = scanner.nextLine().trim();
      
      if (!shot.equalsIgnoreCase("r") && !shot.equalsIgnoreCase("p") && !shot.equalsIgnoreCase("s")) {
        System.out.println("Invalid input. Choose between R (rock), P (paper) or S (scissors).");
      } 
    }

    shot = shot.toLowerCase();
    switch (shot) {
      case "r": 
        return "rock";
      case "p": 
        return "paper";
      case "s": 
        return "scissors";
      default: return "";
    }
  }

  private void displayShots(String humanShot, String computerShot) {
    System.out.println("You throw " + humanShot);
    System.out.println("Computer throws " + computerShot + ":");
  }

  private boolean isHumanRoundWinner(String humanShot, String computerShot) {
    return (humanShot.equals("rock") && computerShot.equals("scissors") || 
            humanShot.equals("scissors") && computerShot.equals("paper") ||
            humanShot.equals("paper") && computerShot.equals("rock"));
  }

  private void displayRoundScore() {
    System.out.println(String.format("Human–Computer: %s-%s\n", human.getScore(), computer.getScore()));
  }

  private void displayWinner() {
    System.out.println("\n----- G A M E : O V E R -----");
    if (human.getScore() > computer.getScore()) {
      System.out.println("Human wins this game.");
    } else {
      System.out.println("Computer wins this game.");
    }
    System.out.printf("Final score: Human–Computer: %s-%s", human.getScore(), computer.getScore());
  }
}
