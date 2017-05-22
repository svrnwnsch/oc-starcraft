import java.util.regex.Pattern;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Condition {
    private Pattern conditionPattern;

    public Condition(String regex) {
        conditionPattern = Pattern.compile(regex);
    }

    public boolean matches(String environment) {
        return conditionPattern.matcher(environment).matches();
    }
}
