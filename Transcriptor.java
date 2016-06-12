public class Transcriptor {

	public Transcriptor() {
	}

	public Chromatid Transcribe(Chromatid a_Chromatid) {
		Nucleotide[] newData = null;
		float pMutation = 1.0f / 5.0f;
		int nMutationSize = (int) ((Math.pow(Math.random(), 2) * 8)) + 1;// mutation
																			// size
																			// of
																			// 1-4
		float pActual = (float) Math.random();
		if (pActual < pMutation)// deletion
		{
			int nSkip = (int) (Math.random() * a_Chromatid.GetData().length);
			if (a_Chromatid.GetData().length - nMutationSize < nSkip) {
				nMutationSize = a_Chromatid.GetData().length - nSkip;
			}
			int nNewSize = Math.max(0, a_Chromatid.GetData().length
					- nMutationSize);
			newData = new Nucleotide[nNewSize];
			for (int i = 0; i < a_Chromatid.GetData().length; i++) {
				if (i < nSkip || i >= nSkip + nMutationSize) {
					newData[i < nSkip ? i : i - nMutationSize] = a_Chromatid
							.GetData()[i];
				}
			}
		} else if (pActual < pMutation * 2)// addition
		{
			newData = new Nucleotide[a_Chromatid.GetData().length
					+ nMutationSize];
			int nAdd = (int) (Math.random() * (a_Chromatid.GetData().length + 1));
			for (int i = 0; i < newData.length; i++) {
				if (i >= nAdd && i < nAdd + nMutationSize) {
					newData[i] = new Nucleotide((byte) (Math.random() * 4));
				} else {
					newData[i] = a_Chromatid.GetData()[i < nAdd ? i : i
							- nMutationSize];
				}
			}
		} else if (pActual < pMutation * 3)// inversion
		{
			if (a_Chromatid.GetData().length > 1) {
				if (nMutationSize < 2) {
					nMutationSize = 2;
				}
				if (a_Chromatid.GetData().length < nMutationSize) {
					nMutationSize = a_Chromatid.GetData().length;
				}
				newData = new Nucleotide[a_Chromatid.GetData().length];
				int nStart = (int) (Math.random() * (a_Chromatid.GetData().length - (nMutationSize - 1)));
				int nReversalOffset = nMutationSize - 1;
				for (int i = 0; i < newData.length; i++) {
					if (i < nStart || i >= nStart + nMutationSize) {
						newData[i] = a_Chromatid.GetData()[i];
					} else {
						newData[i] = a_Chromatid.GetData()[i + nReversalOffset];
						nReversalOffset -= 2;
					}
				}
			}
		} else if (pActual < pMutation * 4)// substitution
		{
			if (a_Chromatid.GetData().length > 0) {
				if (nMutationSize > a_Chromatid.GetData().length) {
					nMutationSize = a_Chromatid.GetData().length;
				}
				newData = new Nucleotide[a_Chromatid.GetData().length];
				int nSub = (int) (Math.random() * (newData.length - (nMutationSize - 1)));
				for (int i = 0; i < newData.length; i++) {
					if (i >= nSub && i < nSub + nMutationSize) {
						newData[i] = new Nucleotide((byte) (Math.random() * 4));
					} else {
						newData[i] = a_Chromatid.GetData()[i];
					}
				}
			}
		}
		if (newData == null)// else
		{
			newData = new Nucleotide[a_Chromatid.GetData().length];
			for (int i = 0; i < newData.length; i++) {
				newData[i] = a_Chromatid.GetData()[i];
			}
		}
		return new Chromatid(newData);
	}
}