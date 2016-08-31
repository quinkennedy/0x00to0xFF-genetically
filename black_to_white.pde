import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

String quote = "We are a nation of communities... a brilliant diversity spread like stars, like a thousand points of light in a broad and peaceful sky.";//George H.W. Bush

GeneticAlg g;
int m_nPopulation = 100;
ArrayList<int[]> currGen;
int m_nBest = 0;
boolean bSuccess = false;
boolean bSave = false;
int maxLength;

public void setup(){
  size(200, 200);
  //size(100, 99);
  g = new GeneticAlg(
    GeneticAlg.MatingType.Hermaphrodite, 
    GeneticAlg.LifeCycle.Gametic, 
    4, 
    m_nPopulation);
  maxLength = width*height/4;
  int[] first = new int[maxLength];
  Arrays.fill(first, 1);
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
  if (child.length < maxLength){
    int offset = (maxLength - child.length) / 2;
    for(; i < child.length; i++){
      if (child[i] != 0){
        setColor(i+offset, white);
      } else {
        setColor(i+offset, black);
      }
    }
    i += offset;
    for(; i < maxLength; i++){
      setColor(i, black);
    }
    for(i = 0; i < offset; i++){
      setColor(i, black);
    }
  } else {
    for(; i < maxLength; i++){
      if (child[i] == 1){
        setColor(i, white);
      } else {
        setColor(i, black);
      }
    }
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
                    BigInteger nFitness = BigInteger.valueOf(
                    (int)map(min(child.length, maxLength), 0, maxLength, maxLength*1000, 2));
                    bSuccess = (child.length == 0);
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
                if (bSave){
                  saveFrame("by_hundred_weight_white/frame-#########.png");
                }
  }  
  
}