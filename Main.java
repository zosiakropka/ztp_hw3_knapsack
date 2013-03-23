
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

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("usage: java Main <input_file> <square_size>");
		}
		inputFile = args[0];
		n = Integer.parseInt(args[1]);
		try {
			Knapsack knapsack = new Knapsack(n, inputFile);
			knapsack.pack();
		} catch (Knapsack.Element.Reader.InputFileUnevenNumbersCountException | IOException ex) {
			
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}


	}
}