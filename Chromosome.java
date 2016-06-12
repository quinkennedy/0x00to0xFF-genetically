public class Chromosome {

	Chromatid m_Chromatid1;
	Chromatid m_Chromatid2;

	public Chromosome(Chromatid a_Chromatid) {
		m_Chromatid1 = a_Chromatid;
	}

	public Chromosome Copy() {
		Chromosome output = new Chromosome(m_Chromatid1.Copy());
		if (m_Chromatid2 != null) {
			output.m_Chromatid2 = m_Chromatid2.Copy();
		}
		return output;
	}

	public void Replicate() {
		Transcriptor t = new Transcriptor();
		m_Chromatid2 = t.Transcribe(m_Chromatid1);
	}

	public void Crossover(Chromosome a_Chromosome) {
		m_Chromatid1.Crossover(a_Chromosome.m_Chromatid1);
		m_Chromatid1.Crossover(a_Chromosome.m_Chromatid2);
		m_Chromatid2.Crossover(a_Chromosome.m_Chromatid1);
		m_Chromatid2.Crossover(a_Chromosome.m_Chromatid2);
	}
	
	public byte[] GetDataAsBytes() {
		return m_Chromatid1.GetDataAsBytes();
	}

	public int GetDataAsInt() {
		return m_Chromatid1.GetDataAsInt();
	}

	public String GetDataAsString() {
		return m_Chromatid1.GetDataAsString();
	}

	public Nucleotide[] GetData() {
		return m_Chromatid1.GetData();
	}

	public Chromosome Split() {
		if (m_Chromatid2 == null) {
			return null;
		}
		Chromatid temp;
		if (Math.random() < 0.5) {
			temp = m_Chromatid1;
			m_Chromatid1 = m_Chromatid2;
		} else {
			temp = m_Chromatid2;
		}
		m_Chromatid2 = null;
		return new Chromosome(temp);
	}
}