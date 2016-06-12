public class Zygote {

	private Chromosome[] m_arrMaternal;
	private Chromosome[] m_arrPaternal;

	public Zygote(Chromosome[] a_arrMaternal, Chromosome[] a_arrPaternal) {
		m_arrMaternal = a_arrMaternal;
		m_arrPaternal = a_arrPaternal;
	}

	private void Replicate() {
		for (int i = 0; i < m_arrMaternal.length; i++) {
			m_arrMaternal[i].Replicate();
			m_arrPaternal[i].Replicate();
		}
	}

	private void Crossover() {
		for (int i = 0; i < m_arrMaternal.length; i++) {
			m_arrMaternal[i].Crossover(m_arrPaternal[i]);
		}
	}

	private void IntermediateSplit(Chromosome[] ao_Intermediate1,
			Chromosome[] ao_Intermediate2) {
		for (int i = 0; i < m_arrMaternal.length; i++) {
			if (Math.random() < 0.5) {
				ao_Intermediate1[i] = m_arrMaternal[i];
				ao_Intermediate2[i] = m_arrPaternal[i];
			} else {
				ao_Intermediate2[i] = m_arrMaternal[i];
				ao_Intermediate1[i] = m_arrPaternal[i];
			}
		}
	}

	private void FinalSplit(Chromosome[] ao_Intermediate1,
			Chromosome[] ao_Intermediate2, Chromosome[] ao_Intermediate3,
			Chromosome[] ao_Intermediate4) {
		for (int i = 0; i < ao_Intermediate1.length; i++) {
			ao_Intermediate3[i] = ao_Intermediate1[i].Split();
			ao_Intermediate4[i] = ao_Intermediate2[i].Split();
		}
	}

	public Gamete[] Split() {
		Replicate();
		Crossover();
		Chromosome[] Intermediate1 = new Chromosome[m_arrMaternal.length];
		Chromosome[] Intermediate2 = new Chromosome[m_arrMaternal.length];
		IntermediateSplit(Intermediate1, Intermediate2);
		Chromosome[] Intermediate3 = new Chromosome[m_arrMaternal.length];
		Chromosome[] Intermediate4 = new Chromosome[m_arrMaternal.length];
		FinalSplit(Intermediate1, Intermediate2, Intermediate3, Intermediate4);
		Gamete[] output = new Gamete[] { new Gamete(Intermediate1),
				new Gamete(Intermediate2), new Gamete(Intermediate3),
				new Gamete(Intermediate4) };
		return output;
	}
}