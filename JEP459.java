import java.util.regex.Pattern;

import static java.lang.StringTemplate.RAW;

void main() {
  var socraten = "Socraten";
  System.out.println();
  String hello = STR."==> Hello, \{socraten}!\n";
  System.out.println(hello);

  StringTemplate template = RAW."==> Template for \{socraten}\n";
  System.out.println(template.interpolate());

  String json = Json.string."""
    {
      "name": "\{socraten}"
    }
    """;

  System.out.println(STR."\{json}\n");

  String wrong = Json.string."""
      "event": \{socraten}
    """;
}

enum Json implements StringTemplate.Processor<String, IllegalArgumentException> {
  string;
  private static final Pattern REGEX = Pattern.compile("\\{.*\\}");

  @Override
  public String process(StringTemplate template) throws IllegalArgumentException {
    return switch (template.interpolate()) {
      case String interpolated when REGEX.matcher(interpolated.replace('\n', ' ').trim()).matches() -> interpolated.strip();
      default -> throw new IllegalArgumentException(STR."Invalid JSON template: \{template}");
    };
  }
}
