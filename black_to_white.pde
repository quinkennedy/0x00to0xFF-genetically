import java.math.BigInteger;
import java.util.ArrayList;

GeneticAlg g;
int m_nPopulation = 50;
ArrayList<int[]> currGen;
int m_nBest = 0;
int[] barrAnswer;
boolean bSuccess = false;

public void setup(){
  size(200, 200);
  //size(100, 99);
  barrAnswer = new int[width*height/4];
  java.util.Arrays.fill(barrAnswer, 1);
  g = new GeneticAlg(GeneticAlg.MatingType.Hermaphrodite, GeneticAlg.LifeCycle.Gametic, 2, m_nPopulation);
  int[] first = new int[width*height/4];
  first[(int)(Math.random()*first.length)] = 1;
  g.AddOrganism(first, BigInteger.valueOf(2));
}

private void setColor(int i, color c){
  //pixels[i] = c;
  //return;
  for(int j = 0; j < 4; j++){
    pixels[i*4+j] = c;
  }
}

private void drawChild(int[] child){
  color white = color(255);
  color black = color(0);
  loadPixels();
  int i = 0;
  for(; i < child.length && i < barrAnswer.length && i < pixels.length/4; i++){
    if (child[i] == barrAnswer[i]){
      setColor(i, white);
    } else {
      setColor(i, black);
    }
  }
  for(; i < pixels.length/4; i++){
    setColor(i, black);
  }
  updatePixels();
}

public void draw(){
  if (!bSuccess){
    int bestChildIndex = 0;
    ArrayList<int[]> children = g.GetChildren(m_nPopulation);
                BigInteger[] fitnesses = new BigInteger[children.size()];
                BigInteger nMaxFitness = BigInteger.ONE;
                for(int i = 0; i<children.size() && !bSuccess; i++){
                    int[] child = children.get(i);
                    BigInteger nFitness = BigInteger.ONE;
                    bSuccess = child.length >= barrAnswer.length;
                    for(int j = 0; j < child.length && j < barrAnswer.length; j++){
                      bSuccess &= (child[j] == barrAnswer[j]);
                        if (child[j] == barrAnswer[j]){
                            nFitness = nFitness.multiply(BigInteger.valueOf(2));
                        }
                    }
                    nFitness = nFitness.add(BigInteger.valueOf(barrAnswer.length * 1000/(Math.abs(child.length - barrAnswer.length)+1)));
                    if (bSuccess){
                      println("success!");
                    }
                    fitnesses[i] = nFitness;
                    if (nFitness.compareTo(nMaxFitness) == 1){
                      nMaxFitness = nFitness;
                      bestChildIndex = i;
                    }
                    if (nFitness.compareTo(BigInteger.ZERO) == 0){
                      println("zero?!?");
                    }
                }
                if (!bSuccess){
                    g.SetChildrenFitness(fitnesses);
                }
                drawChild(children.get(bestChildIndex));
  }
  
}