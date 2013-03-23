
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Zofia Sobocinska
 */
public class Knapsack {

	final int N;
	Element[] elements;

	/**
	 *
	 * @param n
	 * @param inputFile
	 * @throws Knapsack.Element.Reader.InputFileUnevenNumbersCountException
	 * @throws IOException
	 */
	public Knapsack(int n, String inputFile)
			throws Element.Reader.InputFileUnevenNumbersCountException, IOException {
		this.N = n;
		elements = Element.Reader.readFromFile(inputFile);
	}

	public int pack() {
		return 0;
	}

	public static class Element {

		private int w = 0, h = 0;

		public Element(int w, int h) {
			this.w = w;
			this.h = h;
		}

		public int getW() {
			return w;
		}

		public int getH() {
			return h;
		}

		public static class Reader {

			/**
			 *
			 * @param filename
			 * @return
			 * @throws
			 * Knapsack.Element.Reader.InputFileUnevenNumbersCountException
			 * @throws IOException
			 */
			public static Element[] readFromFile(String filename)
					throws InputFileUnevenNumbersCountException, IOException {

				String input = "", s;
				String regexp = "\\D+";
				String[] tokens;
				// read from file array of numbers

				try (
						FileReader fr = new FileReader(filename);
						BufferedReader br = new BufferedReader(fr)) {
					while ((s = br.readLine()) != null) {
						input = input + s + " ";
					}
				}
				tokens = input.split(regexp);
				if (tokens.length % 2 != 0) {
					throw new InputFileUnevenNumbersCountException();
				}
				int m = tokens.length / 2;

				Element[] element = new Element[m];
				for (int i = 0; i < m; i++) {
					int u = Integer.parseInt(tokens[2 * i]);
					int v = Integer.parseInt(tokens[2 * i + 1]);
					element[i] = new Element(u, v);
				}
				Arrays.sort(element);

				return element;
			}

			/**
			 * May be thrown when count of numbers in the input file isn't even
			 */
			public static class InputFileUnevenNumbersCountException
					extends Exception {

				static final long serialVersionUID = 1L;
				static final String MSG = "Uneven count of numbers in the input file.";

				public InputFileUnevenNumbersCountException() {
					super(MSG);
				}
			}
		}
	}
}
