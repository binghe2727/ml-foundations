package test;
//q18.java
//
import java.io.*;
import java.util.*;
import java.math.*;//bigDecimal computation
//import pla.*;
//import pla.pla.record;

public class q18 {
	//parameter
	public int dimension=5;// for 5 not easy to control on the constant parameter=1
	//public double[] weight=new double[dimension];
	//public double[] weightPocket=new double[dimension];
	//public int updatingTime=0;
	//public String pathName="./src/train.txt";
	public ArrayList<record> trainData=new ArrayList<record>();
	public ArrayList<record> testData=new ArrayList<record>();
	public class record{
		int output;
		//double x0=1.0d;  
		double[] input=new double[dimension];
	}
	
	//method
	//readfile
	public void readFile(String pathName, ArrayList<record> data) throws FileNotFoundException{
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
			data.add(add);
		}
		scanner.close();
	}

	//plaPocket
	public double[] plaPocket(ArrayList<record> data){
		double[] weight=new double[dimension]; 
		double[] weightPocket=new double[dimension];
		int updateTime=0;
		if(updateTime<=100){
			for(record recordElement:data){
				if(recordElement.output!=signFunction(dotProduct(recordElement.input,weight))){
					weight=updateWeight(weight,recordElement.input,recordElement.output);
					updateTime++;//where to put it
					//save in pocket
					if(errorRate(weight,data)<errorRate(weightPocket,data)){
						for(int i=0;i<weight.length;i++){
							weightPocket[i]=weight[i];
						}						
					}
				}
			}
		}
		return weightPocket;
	}	
	
	//errerRateCompute and performance computing
	public double errorRate(double[] weight, ArrayList<record> data){
		int error=0;
		for(record recordElement:data){
			if(recordElement.output!=signFunction(dotProduct(recordElement.input,weight))){
				error++;
			}
		}
		return (double) error/data.size();		
	}
	//updating weight
	public double[] updateWeight(double[] weight,double[] x,int y){
		for(int i=0;i<weight.length;i++){
			weight[i]=weight[i]+x[i]*y;
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
	//sign function
	public int signFunction(double x){
		return x>0?1:-1;//three c
	}	
	
	public static void main(String[] args)throws FileNotFoundException{
		q18 a=new q18();
		String train="./src/training_data.txt";
		String test="./src/test_data.txt";
		a.readFile(train, a.trainData);
		a.readFile(test, a.testData);
		double sumError=0.0d;
		for(int i=0;i<2000;i++){
			Collections.shuffle(a.trainData);
			sumError=sumError+a.errorRate(a.plaPocket(a.trainData), a.testData);
		}	
		System.out.println(sumError/2000.0d);
	}
}
