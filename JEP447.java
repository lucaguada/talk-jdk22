
import java.util.*;

abstract class Person {
  protected String firstName;
  protected String lastName;

  protected Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}

class VIP extends Person {
  private String stageName;

  public VIP(String firstName, String lastName, String stageName) {
    if (stageName == null) {
      System.err.format("Can't init VIP %s %s, stage-name is null!\n", firstName, lastName);
      throw new IllegalArgumentException("Can't init VIP %s %s, stage-name is null!".format(firstName, lastName));
    }
    // this.stageName = stageName;
    super(firstName, lastName);
    this.stageName = stageName;
  }
}

void main() {
  var nic = new VIP("Nicolas", "Coppola", "Nicolas Cage");
  var sup = new VIP("Henry", "Cavill", null);
}
