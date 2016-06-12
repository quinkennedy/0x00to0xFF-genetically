public interface ISorter {
	// return < 0 if Data is before CompareAgainst, 0 if equal, > 0 if after
	public double ComparedTo(Object a_Data, Object a_CompareAgainst);
}