import java.util.ArrayList;

public class Chromatid {

	private Nucleotide[] m_Data;

	public Chromatid(Nucleotide[] a_Data) {
		m_Data = a_Data;
	}

	public Chromatid(String a_sData) {
		if (a_sData == null)
		{
			a_sData = "";
		}
		m_Data = new Nucleotide[a_sData.length() * 4];
		for (int i = 0; i < a_sData.length(); i++) {
			char c = a_sData.charAt(i);
			for (int j = 0; j < 4; j++) {
				m_Data[i * 4 + j] = new Nucleotide(
						(byte) (((int) (c) / Math.pow(2, (j * 2))) % 4));
			}
		}
	}

	public Chromatid(int a_nData) {
		if (a_nData < 0) {
			a_nData = 0;
		}
		int nBits = (int) Math.ceil(Math.log(a_nData + 1) / Math.log(2));
		m_Data = new Nucleotide[(int) Math.ceil(nBits / 2.0)];
		for (int i = 0; a_nData > 0 && i < Math.ceil(nBits / 2.0); i++) {
			m_Data[i] = new Nucleotide((byte) (a_nData % 4));
			a_nData /= 4;
		}
	}
	
	public Chromatid(byte[] a_narrData){
		if (a_narrData == null)
		{
			m_Data = new Nucleotide[0];
			return;
		}
		//8 bits per byte
		m_Data = new Nucleotide[a_narrData.length * 4];
		for(int i = 0; i < m_Data.length; i++) {
			m_Data[i] = new Nucleotide((byte)((a_narrData[i/4]/(4*i))%4));
		}
	}

	public Chromatid Copy() {
		Nucleotide[] outputData = new Nucleotide[m_Data.length];
		for (int i = 0; i < m_Data.length; i++) {
			outputData[i] = m_Data[i].Copy();
		}
		return new Chromatid(outputData);
	}

	public void Crossover(Chromatid a_Chromatid) {
		int start = (int) (Math.random() * (Math.min(m_Data.length,
				a_Chromatid.GetData().length)));
		int end = (int) (Math.random() * (Math.max(m_Data.length,
				a_Chromatid.GetData().length)));
		if (end >= start) {
			if (end >= m_Data.length || end >= a_Chromatid.GetData().length) {
				end = start - 1;
				start = 0;
			}
			Nucleotide temp;
			for (int i = start; i <= end; i++) {
				temp = m_Data[i];
				m_Data[i] = a_Chromatid.GetData()[i];
				a_Chromatid.GetData()[i] = temp;
			}
		}
	}

	private char GetNextChar(int i) {
		int output = 0;
		for (int j = 0; j < 4 && j + i < m_Data.length; j++) {
			output += Math.pow(4, j) * (int) (m_Data[i + j].GetData());
		}
		return (char) output;
	}

	public String GetDataAsString() {
		String output = "";
		for (int i = 0; i < m_Data.length; i += 4) {
			output += GetNextChar(i);
		}
		return output;
	}

	public int GetDataAsInt() {
		int output = 0;
		for (int i = 0; i < m_Data.length; i++) {
			output += Math.pow(4, i) * m_Data[i].GetData();
		}
		return output;
	}

	public Nucleotide[] GetData() {
		return m_Data;
	}

	public byte[] GetDataAsBytes() {
		ArrayList<Byte> lstBytes = new ArrayList<Byte>();
		byte nCurr = 0;
		int i = 0;
		for(; i < m_Data.length; i++)
		{
			nCurr += Math.pow(4, i%4) * m_Data[i].GetData();
			if (i%4 == 3)//8 bits per byte
			{
				lstBytes.add(nCurr);
				nCurr = 0;
			}
		}
		if (i%4 != 0)
		{
			lstBytes.add(nCurr);
		}
		byte[] output = new byte[lstBytes.size()];
		for(i = 0; i<output.length; i++)
		{
			output[i] = lstBytes.get(i);
		}
		return output;
	}
}