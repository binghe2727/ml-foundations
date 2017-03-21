package linearLogi;
//attention static final parameter should be setted big character, 
//pay attention to it.

//keep the logic and the functino reuses.

//we can use the static parameter to set the whole structure.
import java.io.*;
import java.util.*;
import java.math.*;
public class linLog {
	private static final int DIM=20+1;//not forget the one at the first time
	private static final double RATE=0.001;
	private static final int SIZE=1000;//for data size
	private static final int LOOP=2000;//loop times in logistic regression
	private class in{
		double[] x=new double[DIM];
		int y=0;
	}
	// parameter setting
	private ArrayList<in> inData;
	private ArrayList<in> testData;
	private double[] weight;
	//read data from the file
	linLog(String pathName, String testPath)throws FileNotFoundException{
		inData=new ArrayList<in>();
		testData=new ArrayList<in>();
		weight=new double[DIM];
		inData=readFile(pathName);
		testData=readFile(testPath);	
	}
	// seperate the individul function
	public ArrayList<in> readFile(String pathName)throws FileNotFoundException{//if multiple use,better use as a funciton
		// and better as a strong input/output function stype.
		ArrayList<in> input=new ArrayList<in>();
		Scanner sc=new Scanner(new File(pathName));
		while(sc.hasNext()){
			in data=new in();
			data.x[0]=1;
			for(int i=1;i<DIM;i++){
				data.x[i]=sc.nextDouble();
			}
			data.y=sc.nextInt();
			input.add(data);
		}
		sc.close();
		return input;
	}
	
	//sigmoid function
	public double sigmoid(double x){
		return 1/(1+Math.exp(-x));
	}
	//inner product
	public double innerPro(double[] w, double[] x){
		double sum=0;
		for(int i=0;i<w.length;i++){
			sum+=w[i]*x[i];
		}
		return sum;
	}
	//number multiply array
	public double[] numArray(double[] x, double w){
		double[] y=new double[x.length];
		for(int i=0;i<x.length;i++){
			y[i]=w*x[i];
		}
		return y;
	}
	//array plus array
	public double[] arrayPlus(double[] x, double[] w){
		double[] y=new double[x.length];
		for(int i=0;i<x.length;i++){
			y[i]=x[i]+w[i];
		}
		return y;
	}
	//logistic regression
	public void lr(){
		for(int j=0;j<LOOP;j++){
			double[] sum=new double[DIM];
			for(in data:inData){
				sum=arrayPlus(numArray(data.x,(-1)*data.y*sigmoid((-1)*data.y*innerPro(weight,data.x))),sum);
			}
			weight=arrayPlus(weight,numArray(sum,(double) RATE/SIZE));
		}
	}
	//tast// 
	public double test(){
		int err=0;
		for(in data:testData){
			if(sign(innerPro(weight,data.x))!=data.y){
				err++;
			}
		}
		return (double) err/testData.size();
	}
	public int sign(double x){
		return x>=0?+1:-1;
	}
	public static void main(String[] args)throws FileNotFoundException{
		String dataPath="./src/trainingData.txt";
		String testPath="./src/testData.txt";
		linLog a=new linLog(dataPath,testPath);
		a.lr();
		System.out.println(a.test());	
	}
}

