package pla;
//pla.java
//implement pla algorithm
import java.io.*;
import java.util.*;
import java.math.*;//bigDecimal computation

public class pla {
	public int dimension=5;// for 5 not easy to control on the constant parameter=1
	public double[] weight=new double[dimension];
	public int updatingTime=0;
	public String pathName="./src/train.txt";
	public ArrayList<record> trainData=new ArrayList<record>();
	public class record{
		int output;
		//double x0=1.0d;  
		double[] input=new double[dimension];
	}
	//public boolean flag=false;
	//read data from file
	public void readFile() throws FileNotFoundException{
		Scanner scanner=new Scanner(new File(pathName));
		while(scanner.hasNext()){
			record add=new record();//construct add/node in the array list
//			for(double inputElement:add.input){
//				inputElement=scanner.nextDouble();
//
//				
//			}
			add.input[0]=1.0d;
			for(int i=1;i<5;i++){
				add.input[i]=scanner.nextDouble();
			}
			add.output=scanner.nextInt();
			//System.out.print(add.input[0]);
			trainData.add(add);
		}
		scanner.close();
	}
	//print data
	public void printDataArrayList(){
		StringBuilder string0=new StringBuilder();
		for(record recordElement:trainData){
			//string0.append(recordElement.x0);
			//string0.append(" ");
			for(double inputElement: recordElement.input){
				string0.append(inputElement);
				string0.append(" ");
			}
			string0.append(recordElement.output);
			string0.append("\n");
		}
		System.out.println(string0.toString());
	}
	//pla computing
	public void plaComputing(){
		for(record recordElement:trainData){
			if(recordElement.output!=signFunction(dotProduct(recordElement.input,weight))){
				weight=updateWeight(weight,recordElement.input,recordElement.output);
				
			}
		}
	}
	//pla computing big decimal
	public boolean plaComputingBigDecimal(){
		boolean flag=false;
		for(record recordElement:trainData){
			if(recordElement.output!=signFunction(dotProductBigDecimal(recordElement.input,weight))){
				weight=updateWeightBigDecimal(weight,recordElement.input,recordElement.output);
				flag=true;
				updatingTime++;
			}
		}
		return flag;
	}
	//updating weight
	public double[] updateWeight(double[] weight,double[] x,int y){
		for(int i=0;i<weight.length;i++){
			weight[i]=weight[i]+x[i]*y;
		}
		return weight;
	}
	//updating weight bigdecimal
	public double[] updateWeightBigDecimal(double[] weight, double[] x, int y){
		for(int i=0;i<weight.length;i++){
			weight[i]=(BigDecimal.valueOf(weight[i]).add(BigDecimal.valueOf(x[i]).
					multiply(BigDecimal.valueOf(y)))).doubleValue();
		}
		return weight;
	}
	//dot product
	public double dotProduct(double[] a,double[] b){
		double sum=0;
		for(int i=0;i<a.length;i++){
			sum=sum+a[i]*b[i];
		}
		return sum;
	}
	//dot product big decimal
	public double dotProductBigDecimal(double[] a, double[] b){
		BigDecimal sum=BigDecimal.valueOf(0.0d);
		for(int i=0;i<a.length;i++){
			sum=sum.add(BigDecimal.valueOf(a[i]).multiply(BigDecimal.valueOf(b[i])));
		}
		return sum.doubleValue();
	}
	//sign function
	public int signFunction(double x){
		return x>0?1:-1;//three c
	}
	//main function
	public static void main(String[] args){
//		double a=1.0d;
//		System.out.println(a);
		
		pla pla0=new pla();
		try{
		pla0.readFile();
		while(pla0.plaComputingBigDecimal()){
			//pla0.updatingTime++;
		}
		
		System.out.println(pla0.updatingTime);
		}catch(Exception e){
			System.err.println("file not found");
			e.printStackTrace();
		}
		//pla0.printDataArrayList();
	}

	
	
}
