/**
 * Created by Severin Wünsch on 25.05.2017.
 */
public class outputClassifier {
    public static void main(String[] args) {
        XCS xcs = new XCS();
        xcs.loadXCS(XCS.fileName);
        xcs.printClassifiers();
    }
}
