import java.math.BigInteger;
import java.util.ArrayList;

//do I want this to implement java.util.List<T> ?
public class FitnessSortedList {

	class Node {
		Gamete Data;
		Node Before;
		Node After;
		int BeforeBranchCount;
		BigInteger BeforeBranchFitness;

		public Node(Gamete a_Data) {
			Data = a_Data;
			BeforeBranchCount = 0;
			BeforeBranchFitness = BigInteger.ZERO;
		}
	}

	int m_nLength;
	BigInteger m_nTotalFitness;
	Node m_Root;

	public FitnessSortedList() {
		m_nLength = 0;
		m_nTotalFitness = BigInteger.ZERO;
	}
	
	public BigInteger TotalFitness(){
		return m_nTotalFitness;
	}

	public int Length() {
		return m_nLength;
	}

	public Gamete[] GetList() {
		ArrayList<Gamete> output = new ArrayList<Gamete>(m_nLength);
		if (m_Root == null) {
			return output.toArray(new Gamete[output.size()]);
		}
		PopulateList(output, m_Root, 0);
		return output.toArray(new Gamete[output.size()]);
	}

	private int PopulateList(ArrayList<Gamete> ao_arrData, Node a_Root, int a_nCurrIndex) {
		if (a_Root.Before != null) {
			a_nCurrIndex = PopulateList(ao_arrData, a_Root.Before, a_nCurrIndex);
		}
		ao_arrData.add(a_Root.Data);
		a_nCurrIndex++;
		if (a_Root.After != null) {
			a_nCurrIndex = PopulateList(ao_arrData, a_Root.After, a_nCurrIndex);
		}
		return a_nCurrIndex;
	}
	
	public Gamete GetByFitness(BigInteger i) throws IndexOutOfBoundsException
	{
		if (i.compareTo(BigInteger.ZERO) < 0 || i.compareTo(m_nTotalFitness) >= 0){
                  System.out.println("total fitness:" + m_nTotalFitness);
                  System.out.println("length:" + m_nLength);
			throw new IndexOutOfBoundsException();
		}
		Node currRoot = m_Root;
		while (currRoot != null){
			//System.out.println("GetByFitness:"+i+", Before:"+currRoot.BeforeBranchFitness+", My:"+currRoot.Data.GetFitness());
			if (currRoot.BeforeBranchFitness.compareTo(i) <= 0 && (currRoot.BeforeBranchFitness.add(currRoot.Data.GetFitness())).compareTo(i) > 0){
				return currRoot.Data;
			}
			else if(currRoot.BeforeBranchFitness.compareTo(i) < 0){
				i = i.subtract(currRoot.BeforeBranchFitness.add(currRoot.Data.GetFitness()));
				currRoot = currRoot.After;
			}
			else{
				currRoot = currRoot.Before;
			}
		}
		//we should never reach here
		return null;
	}
	
	
	public Gamete GetByIndex(int i) throws IndexOutOfBoundsException
	{
		if (i < 0 || i >= m_nLength){
			throw new IndexOutOfBoundsException();
		}
		Node currRoot = m_Root;
		while (currRoot != null){
			if (currRoot.BeforeBranchCount == i){
				return currRoot.Data;
			}
			else if(currRoot.BeforeBranchCount < i){
				i -= (currRoot.BeforeBranchCount + 1);
				currRoot = currRoot.After;
			}
			else{
				currRoot = currRoot.Before;
			}
		}
		//we should never reach here
		return null;
	}
	
	public Gamete RemoveByIndex(int i) throws IndexOutOfBoundsException
	{
		if (i < 0 || i >= m_nLength){
			throw new IndexOutOfBoundsException();
		}
		
		return RemoveByIndex(i, null, true);
	}
	
	private Gamete RemoveByIndex(int i, Node parent, boolean a_bBefore){
		Gamete output;
		Node root = (parent == null ? m_Root : (a_bBefore ? parent.Before : parent.After));
		if(root.BeforeBranchCount == i){
			Node subtreeRoot;
			output = root.Data;
			if (root.Before == null){
				subtreeRoot = root.After;
			}
			else{
				subtreeRoot = root.Before;
				Node last = subtreeRoot;
				while (last.After != null){
					last = last.After;
				}
				last.After = root.After;
			}
			if (parent == null){
				m_Root = subtreeRoot;
			}
			else if(a_bBefore){
				parent.Before = subtreeRoot;
			}
			else{
				parent.After = subtreeRoot;
			}
			m_nLength--;
			m_nTotalFitness = m_nTotalFitness.subtract(output.GetFitness());
			if (m_nTotalFitness.compareTo(BigInteger.ZERO) < 0){
				m_nTotalFitness = BigInteger.ZERO;
			}
			return output;
		}
		boolean bBefore = (root.BeforeBranchCount > i);
		if (!bBefore){
			i -= (root.BeforeBranchCount + 1);
		}
		output = RemoveByIndex(i, root, bBefore);
		if (output != null && bBefore){
			root.BeforeBranchCount--;
			root.BeforeBranchFitness = root.BeforeBranchFitness.subtract(root.Data.GetFitness());
			if (root.BeforeBranchFitness.compareTo(BigInteger.ZERO) < 0){
				root.BeforeBranchFitness = BigInteger.ZERO;
			}
		}
		return output;
	}
	
	public Gamete RemoveByFitness(BigInteger i) throws IndexOutOfBoundsException
	{
		if (i.compareTo(BigInteger.ZERO) < 0 || (i.compareTo(m_nTotalFitness) >= 0 && i.compareTo(BigInteger.ZERO) > 0)){
			System.out.println(m_nTotalFitness + ":" + i);
			throw new IndexOutOfBoundsException();
		}
		
		return RemoveByFitness(i, null, true);
	}
	
	private Gamete RemoveByFitness(BigInteger i, Node parent, boolean a_bBefore){
		Gamete output;
		Node root = (parent == null ? m_Root : (a_bBefore ? parent.Before : parent.After));
		if (root == null){
			System.out.println("before:"+a_bBefore+" i:"+i+" "+parent.BeforeBranchFitness+" "+parent.Data.GetFitness());
		}
		if(i.compareTo(BigInteger.ZERO) == 0 || (root.BeforeBranchFitness.compareTo(i) <= 0 && (root.BeforeBranchFitness.add(root.Data.GetFitness())).compareTo(i) > 0)
				|| (root.BeforeBranchFitness.compareTo(i) < 0 && root.After == null)){
			Node subtreeRoot;
			output = root.Data;
			if (root.Before == null){
				subtreeRoot = root.After;
			}
			else{
				subtreeRoot = root.Before;
				Node last = subtreeRoot;
				while (last.After != null){
					last = last.After;
				}
				last.After = root.After;
			}
			if (parent == null){
				m_Root = subtreeRoot;
			}
			else if(a_bBefore){
				parent.Before = subtreeRoot;
			}
			else{
				parent.After = subtreeRoot;
			}
			m_nLength--;
			m_nTotalFitness = m_nTotalFitness.subtract(output.GetFitness());
			if (m_nTotalFitness.compareTo(BigInteger.ZERO) < 0){
				m_nTotalFitness = BigInteger.ZERO;
			}
			return output;
		}
		boolean bBefore = (root.BeforeBranchFitness.compareTo(i) > 0);
		if (!bBefore){
			i = i.subtract(root.BeforeBranchFitness.add(root.Data.GetFitness()));
		}
		output = RemoveByFitness(i, root, bBefore);
		if (output != null && bBefore){
			root.BeforeBranchCount--;
			root.BeforeBranchFitness = root.BeforeBranchFitness.subtract(output.GetFitness());
			if (root.BeforeBranchFitness.compareTo(BigInteger.ZERO) < 0){
				root.BeforeBranchFitness = BigInteger.ZERO;
			}
		}
		return output;
	}

	private void Insert(Gamete o, Node root) {
		double nRelation = GameteSorter.sComparedTo(o, root.Data);
		if (nRelation < 0) {
			if (root.Before == null) {
				root.Before = new Node(o);
				m_nLength++;
				m_nTotalFitness = m_nTotalFitness.add(o.GetFitness());
			} else {
				Insert(o, root.Before);
			}
			root.BeforeBranchCount++;
			root.BeforeBranchFitness = root.BeforeBranchFitness.add(o.GetFitness());
		} else {
			if (root.After == null) {
				root.After = new Node(o);
				m_nLength++;
				m_nTotalFitness = m_nTotalFitness.add(o.GetFitness());
			} else {
				Insert(o, root.After);
			}
		}
	}

	public void Insert(Gamete o) {
		if (o.GetFitness().compareTo(BigInteger.ZERO) < 0){
			o.SetFitness(BigInteger.ZERO);
		}
		if (m_Root == null) {
			m_Root = new Node(o);
			m_nLength = 1;
			m_nTotalFitness = o.GetFitness();
		} else {
			Insert(o, m_Root);
		}
	}

	public void Insert(Gamete[] o) {
		if (o == null) {
			return;
		}
		for (int i = 0; i < o.length; i++) {
			Insert(o[i]);
		}
	}
}