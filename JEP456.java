
import java.util.stream.*;

sealed interface Transformer {}

  record Autobot(String name, boolean titan) implements Transformer {}
  record Decepticon(String name, boolean combiner) implements Transformer {}


String battlecry(Transformer transformer) {
  return switch (transformer) {
    case Autobot(var name, _) when name.equals("Grimlock") -> "Me Grimlock have no like for Megatron!";
    case Autobot(var name, var titan) when titan -> "%s protects Cybertron from Decepticons!".formatted(name);
    case Decepticon(var name, _) when name.equals("Shockwave") -> "%s command: Let none of Autobots escape!".formatted(name);
    case Decepticon(var name, var combiner) when combiner -> "To destroy the Autobots we form %s!".formatted(name);
    default -> "Rollout!";
  };
}

void main() {
  Stream.<Transformer>of(
    new Autobot("Grimlock", false),
    new Autobot("Omega Superior", true),
    new Decepticon("Shockwave", false),
    new Decepticon("Bruticus", true)
  )
  .peek(it -> System.out.println(battlecry(it)))
  .filter(it -> it instanceof Autobot)
  .map(it -> it instanceof Autobot(var name, _) ? name : "")
  .forEach(it -> System.out.format("%s wins!\n", it));
}
