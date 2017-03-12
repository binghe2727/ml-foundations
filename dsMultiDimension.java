

package decisionStump;
import java.io.*;
import java.util.*;
import java.math.*;
public class dsMultiDimension {
	//public static final int dataSize=20;// data size
	public static final int dataDim=9;
	public class inputDataBig{
		double[] x=new double[dataDim];
		int y;
	}
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
	
	public ArrayList<inputData> dataColl;//list to store,not a very good
	public ArrayList<inputDataBig> dataCollBig;
	public hypo hypo1;
	public double errorTot;
	public int dimSelect;
	
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

	//habit to initilize it too early.
	
	dsMultiDimension(String filepath)throws FileNotFoundException{
		dataCollBig=new ArrayList<inputDataBig>();
		dataColl=new ArrayList<inputData>();
		hypo1=new hypo();
		errorTot=1.0d;
		dimSelect=9;
		Scanner sc=new Scanner(new File(filepath));
		while(sc.hasNext()){
			inputDataBig in=new inputDataBig();
			for(int i=0;i<dataDim;i++){
				in.x[i]=sc.nextDouble();
			}
			in.y=sc.nextInt();
			dataCollBig.add(in);
		}
		sc.close();
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
	public void sortInputData(){//input dataColl;output dataColl
		Collections.sort(dataColl, new myCompare());//arrays.sort not successful at firsttime 
		//by changing it into the Collections.sort
	}
	//get noise
	public void getNoise(){//input dataColl;output dataColl
		for(inputData i:dataColl){
			if(Math.random()<0.2){
				i.y=-1*i.y;
			}	
		}
	}
	//error computing for one hypothesis
	public double errorCompute(hypo hypo){//input dataColl hypo
		int error=0;
		for(inputData i:dataColl){
			if(hypo.s*sign(i.x-hypo.theta)!=i.y)
				error++;
		}
		return ((double) error/dataColl.size());
	}
	//dimension data move
	public void dimChange(int i){//input:dataBig,int i;output:dataColl
		dataColl=new ArrayList<inputData>();
		for(inputDataBig j:dataCollBig){
			inputData in=new inputData();
			in.x=j.x[i];
			in.y=j.y;
			dataColl.add(in);
		}
	}
	//main  process for processing.
	//by compare with the blog solution, it use the &pointer 
	//to update the parameter, learn it and check it whether java do this things.by checking
	//Zhou's code.
	public void eIn(int dimS){//input:dataColl
		dimChange(dimS);
		//getNoise();
		sortInputData();
		double error=1.0d;
		//sortInputData();
		
		hypo h0=new hypo();
		hypo hypo11=new hypo();
		for(int i=0;i<dataColl.size();i++){
			h0.s=1;
			if(i==0)
				h0.theta=dataColl.get(i).x-1.0;
			else 
				if(i==(dataColl.size()-1))
					h0.theta=dataColl.get(i).x+1.0;
				else
					h0.theta=(dataColl.get(i).x+dataColl.get(i+1).x)/2;
			
			if(errorCompute(h0)<error){
				hypo11.s=h0.s;
				hypo11.theta=h0.theta;
				error=errorCompute(h0);
			}
		}
		
		for(int i=0;i<dataColl.size();i++){
			h0.s=-1;
			if(i==0)
				h0.theta=dataColl.get(i).x-1.0;
			else 
				if(i==(dataColl.size()-1))
					h0.theta=dataColl.get(i).x+1.0;
				else
					h0.theta=(dataColl.get(i).x+dataColl.get(i+1).x)/2;
			
			if(errorCompute(h0)<error){
				hypo11.s=h0.s;
				hypo11.theta=h0.theta;
				error=errorCompute(h0);
			}
		}
		//return error;
		updata(error,hypo11,dimS);
	}
	//updata the global parameter
	public void updata(double error, hypo hy,int dimS){
		if(error<errorTot){
			errorTot=error;
			hypo1.s=hy.s;
			hypo1.theta=hy.theta;
			dimSelect=dimS;
		}
	}
	//to String
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(inputDataBig i:dataCollBig){
			for(int j=0;j<dataDim;j++){
				sb.append(i.x[j]);
				sb.append(" ");
			}
			sb.append(i.y);
			sb.append(" ");
			sb.append("\n");
		}
		return sb.toString();
	}
	public String toString2(){
		StringBuilder sb=new StringBuilder();
		for(inputData i:dataColl){
			sb.append(i.x+" ");
			sb.append(i.y+" "+"\n");
		}
		return sb.toString();
	}
	//eOut new compute
	public double eOut(String filePath)throws FileNotFoundException{
		dataColl=new ArrayList<inputData>();
		Scanner sc=new Scanner(new File(filePath));
		while(sc.hasNext()){
			for(int j=0;j<dimSelect;j++){
				sc.nextDouble();
			}
			inputData in=new inputData();
			in.x=sc.nextDouble();
			for(int j=dimSelect+1;j<dataDim;j++){
				sc.nextDouble();
			}
			in.y=sc.nextInt();
			dataColl.add(in);
		}
		return errorCompute(hypo1);		
	}
	//eOut compute
//	public double eOut(){
//		return 0.5 + 0.3*(double)(hypo1.s)*(double)(Math.abs(hypo1.theta) - 1.0);  
//	}
	public static void main(String[] args)throws FileNotFoundException{
//		double eInTot=0;
//		double eOutTot=0;
//		for(int i=0;i<5000;i++){
//			decisionStump ds=new decisionStump();
//			ds.getNoise();;
//			ds.sortInputData();
//			eInTot+=ds.eIn();//first I wrong when I use the =+-----=+is the align sentences, haha,I am sb
//			eOutTot+=ds.eOut();
//		}
//		System.out.printf("%f\n",(float)eInTot/5000.0d);
//		System.out.printf("%f",(float)eOutTot/5000.0d);
		String inString="./src/training_data.txt";
		String tstString="./src/test_data.txt";
		dsMultiDimension dsmd=new dsMultiDimension(inString);
		for(int i=0;i<dsmd.dataDim;i++){
			dsmd.eIn(i);
		}
//		for(int i=8;i<9;i++){
//			dsmd.eIn(i);
//		}
//		for(int i=2;i<2;i++){
//			dsmd.eIn(i);
//		}
		System.out.printf("%f,%d\n", dsmd.errorTot,dsmd.dimSelect);
		System.out.printf("%f\n", dsmd.eOut(tstString));
		//System.out.println(dsmd.toString2());

	}
}
