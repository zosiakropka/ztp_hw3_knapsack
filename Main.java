
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zofia Sobocinska
 */
public class Main {

	static String inputFile;
	static int n;
	static int waste;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("usage: java Main <input_file> <square_size>");
		}
		inputFile = args[0];
		n = Integer.parseInt(args[1]);
		try {
			Knapsack knapsack = new Knapsack(n, inputFile);
			waste = knapsack.pack();
			System.out.println("Waste area: " + waste);
		} catch (Knapsack.Element.Reader.UnevenNumbersCountException | Knapsack.Element.Reader.ElementTooBigException | IOException ex) {

			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}


	}
}