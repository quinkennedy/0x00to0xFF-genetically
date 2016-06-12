import java.math.BigInteger;

public class Gamete {
	private Chromosome[] m_arrChromosomes;
	private BigInteger m_nFitness;

	public Gamete(Chromosome[] a_arrChromosomes) {
		m_arrChromosomes = a_arrChromosomes;
//		Fitness fitnessFunction = Fitness.GetCreatedFitness();
//		if (fitnessFunction != null)
//			m_nFitness = fitnessFunction.GetFitness(this);
	}

	public String ToString() {
		return m_arrChromosomes[0].GetDataAsInt() + " : "
				+ m_arrChromosomes[1].GetDataAsString() + " : " + m_nFitness.toString();
	}

	public BigInteger GetFitness() {
		return m_nFitness;
	}
	
	public void SetFitness(BigInteger a_nFitness){
		m_nFitness = a_nFitness;
	}

	public Zygote Fertilize(Gamete m_Gamete) {
		return new Zygote(m_arrChromosomes, m_Gamete.GetChromosomes());
	}

	public Chromosome[] GetChromosomes() {
		return m_arrChromosomes;
	}

	public Gamete Copy() {
		Chromosome[] duplicate = new Chromosome[m_arrChromosomes.length];
		for (int i = 0; i < m_arrChromosomes.length; i++) {
			duplicate[i] = m_arrChromosomes[i].Copy();
		}
		return new Gamete(duplicate);
	}
}