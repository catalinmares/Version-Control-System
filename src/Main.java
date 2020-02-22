import utils.Context;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        Context context = Context.getInstance();

        context.init(input, output);
        context.run();
    }
}
