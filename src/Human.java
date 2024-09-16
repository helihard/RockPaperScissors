import java.util.Random;

public class Human {
  private String name;
  private int score;

  // default constructor
  public Human() {
    this("");
  }

  // constructor with name argument
  public Human(String name) {
    this.name = name;
    this.score = 0;
  } 

  // name getter
  public String getName() {
    return name;
  }

  // name setter
  public void setName(String name) {
    this.name = name;
  }

  // score getter
  public int getScore() {
    return score;
  }

  public void increaseScore() {
    this.score += 1;
  }
}

class Computer extends Human {
  private Random randomIntPicker;
  private final String[] hands = {"rock", "paper", "scissors"};

  public Computer() {
    super("Computer");
    this.randomIntPicker = new Random();
  }

  // selects a "shot"/hand throw randomly 
  public String shoot() {
    int index = randomIntPicker.nextInt(hands.length);
    String shot = hands[index];
    return shot;
  }
}
