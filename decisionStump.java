package decisionStump;
import java.io.*;
import java.util.*;
import java.math.*;
public class decisionStump {
	public static final int dataSize=20;// data size
	public class inputData{//data structure
		double x;
		int y;
	}
	//first time I¡¡have not created the hypothesis parameter, 
	//by reading the file, we should construct it
	public class hypo{
		int s;
		double theta;
	}
	//sign function
	public int sign(double x){
		return x>=0?1:0;
	}
	//attention this parameter. in practice will it be OK to set in the 
	//global class parameter or change it into the instance parameter.???think it 
	//and check and summarize it.----I think it should be the instance parameter or 
	// by reading Zhou's code. I get the idea, using the newly defined parameter or
	// the complex code creater like List<xxx>like the blog soluion in C++£¬
	//so think more about your data/ your code for further development.
	//but, I suddenly remember that,if the function's parameter and the global class
	//parameter has the same name, then, the function will mainly use the local function
	//parameter, without using the global class parameter. but we can use it
	//by assigning the this.parameter.
	public ArrayList<inputData> dataColl=new ArrayList<inputData>();//list to store,not a very good
	public hypo hypo1=new hypo();
	//habit to initilize it too early.
	
	decisionStump(){
		for(int i=0;i<dataSize;i++){
			inputData id=new inputData();
			id.x=Math.random()*2.0-1.0;
			id.y=sign(id.x);
			dataColl.add(id);
		}
	}
	// to sort by using the sorting api
	// ref http://www.cnblogs.com/happy200318/archive/2011/07/01/2095312.html
	//first implements the comparator
	class myCompare implements Comparator<inputData>{//???must add <inputData>
		public int compare(inputData a, inputData b){
			double diff=a.x-b.x;
			if (diff<0)
				return -1;
			else 
				if (diff>0)
					return +1;
				else
					return 0;
				
		}
	}
	//second sort the arraylist
	public void sortInputData(){
		Collections.sort(dataColl, new myCompare());//arrays.sort not successful at firsttime 
		//by changing it into the Collections.sort
	}
	//get noise
	public void getNoise(){
		for(inputData i:dataColl){
			if(Math.random()<0.2){
				i.y=-1*i.y;
			}	
		}
	}
	//error computing for one hypothesis
	public double errorCompute(hypo hypo){
		int error=0;
		for(inputData i:dataColl){
			if(hypo.s*sign(i.x-hypo.theta)!=i.y)
				error++;
		}
		return ((double) error/dataSize);
	}
	//main  process for processing.
	//by compare with the blog solution, it use the &pointer 
	//to update the parameter, learn it and check it whether java do this things.by checking
	//Zhou's code.
	public double eIn(){//input:arraylist,output hypoparameter
		double error=1.0d;
		//sortInputData();
		hypo h0=new hypo();
		
		for(int i=0;i<dataSize;i++){
			h0.s=1;
			if(i==0)
				h0.theta=dataColl.get(i).x-1.0;
			else 
				if(i==(dataSize-1))
					h0.theta=dataColl.get(i).x+1.0;
				else
					h0.theta=(dataColl.get(i).x+dataColl.get(i+1).x)/2;
			
			if(errorCompute(h0)<error){
				hypo1=h0;
				error=errorCompute(h0);
			}
		}
		
		for(int i=0;i<dataSize;i++){
			h0.s=-1;
			if(i==0)
				h0.theta=dataColl.get(i).x-1.0;
			else 
				if(i==(dataSize-1))
					h0.theta=dataColl.get(i).x+1.0;
				else
					h0.theta=(dataColl.get(i).x+dataColl.get(i+1).x)/2;
			
			if(errorCompute(h0)<error){
				hypo1=h0;
				error=errorCompute(h0);
			}
		}
		return error;
	}
	//eOut compute
	public double eOut(){
		return 0.5 + 0.3*(double)(hypo1.s)*(double)(Math.abs(hypo1.theta) - 1.0);  
	}
	public static void main(String[] args){
		double eInTot=0;
		double eOutTot=0;
		for(int i=0;i<5000;i++){
			decisionStump ds=new decisionStump();
			ds.getNoise();;
			ds.sortInputData();
			eInTot+=ds.eIn();//first I wrong when I use the =+-----=+is the align sentences, haha,I am sb
			eOutTot+=ds.eOut();
		}
		System.out.printf("%f\n",(float)eInTot/5000.0d);
		System.out.printf("%f",(float)eOutTot/5000.0d);
		

	}
}
