import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Fitness {

	String[] m_sarrWords;
	static Fitness s_Fitness;

	private Fitness() {
		ArrayList<String> sarrWords = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("enable1.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sLine = "";
		while(true){
			try {
				sLine = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (sLine == null){
				break;
			}
			sarrWords.add(sLine);
		}
		m_sarrWords = sarrWords.toArray(new String[sarrWords.size()]);
		//m_sarrWords = a_Applet.loadStrings("enable1.txt");
	}

	public static Fitness CreateFitness() {
		if (s_Fitness == null)
			s_Fitness = new Fitness();
		return s_Fitness;
	}

	// will return null if there is no previously-created fitness object
	public static Fitness GetCreatedFitness() {
		return s_Fitness;
	}

	public BigInteger GetFitness(Zygote a_Entity) {
		return BigInteger.ONE;
	}

	public BigInteger GetFitness(char[] a_carrEntity) {
		return GetFitness(a_carrEntity, false);
	}

	public BigInteger GetFitness(char[] a_carrEntity, boolean bVerbose) {
		return BigInteger.ZERO;
//		String[] sarrOutput = GeneticAlgDev.decode(a_carrEntity);
//		int nOutput = sarrOutput[0].length()*4;
//		
//		if (InList(sarrOutput[1])) {
//			if (bVerbose) {
//				System.out.print("in list");
//			}
//			nOutput += Math.pow(sarrOutput[1].length(), 1.7);
//		} else if (bVerbose) {
//			System.out.print("not in list");
//		}
//		if (bVerbose) {
//			System.out.print(nOutput);
//		}
//		return nOutput;
	}

	private boolean IsBefore(String a_sWord, String a_sCompareAgainst) {
		if (a_sWord.equals(a_sCompareAgainst)) {
			return false;
		}
		char cWord, cCompareAgainst;
		for (int i = 0; i < a_sWord.length() && i < a_sCompareAgainst.length(); i++) {
			cWord = a_sWord.charAt(i);
			cCompareAgainst = a_sCompareAgainst.charAt(i);
			if (cWord < cCompareAgainst) {
				return true;
			}
			if (cWord > cCompareAgainst) {
				return false;
			}
		}
		return a_sWord.length() < a_sCompareAgainst.length();
	}

	private boolean InList(String a_sWord) {
		if (m_sarrWords == null || a_sWord == null || a_sWord.length() == 0) {
			return false;
		}

		a_sWord = a_sWord.toLowerCase();
		int nStart = 0;
		int nEnd = m_sarrWords.length;
		int nCurr;
		String sCurr;
		while (nStart < nEnd) {
			nCurr = (nEnd - nStart) / 2 + nStart;
			sCurr = m_sarrWords[nCurr];
			if (sCurr.equals(a_sWord)) {
				return true;
			}
			if (IsBefore(a_sWord, sCurr)) {
				nEnd = nCurr;
			} else {
				nStart = nCurr + 1;
			}
		}
		return false;
	}
}