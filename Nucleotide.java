public class Nucleotide {

	byte m_Data;

	public Nucleotide(byte a_Data) {
		if (a_Data < 0)
			m_Data = 0;
		else if (a_Data > 3)
			m_Data = 3;
		else
			m_Data = a_Data;
	}

	public Nucleotide Copy() {
		return new Nucleotide(m_Data);
	}

	public byte GetData() {
		return m_Data;
	}
}