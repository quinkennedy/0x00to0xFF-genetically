public class GameteSorter implements ISorter {

	public GameteSorter() {
	}

	public double ComparedTo(Object a_Data, Object a_CompareAgainst) {
		return sComparedTo(a_Data, a_CompareAgainst);
	}
	
	public static double sComparedTo(Object a_Data, Object a_CompareAgainst) {
		return ((Gamete) a_Data).GetFitness()
			.compareTo(((Gamete) a_CompareAgainst).GetFitness());
	}
}