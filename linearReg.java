package hw3;
//attention xtest also by random.then, the pass value equal transmition should be paid
//attention.
import Jama.Matrix;
import java.math.*;
import java.io.*;
import java.util.*;
public class linearReg {
	private static final int N=1000;// smaple number
	private static final int M=3;//feature number
	private static final int Mtrans=6;//transfered z space feature number
	private Matrix x;//input x
	private Matrix z;//input y
	private Matrix w1;//weight
	private Matrix w2;//weight
	private Matrix y1;
	private Matrix y2;
	linearReg(){
		x=new Matrix(N,3);
		z=new Matrix(N,6);
		w1=new Matrix(M,1);
		w2=new Matrix(Mtrans,1);
		y1=new Matrix(N,1);
		y2=new Matrix(N,1);
		for(int i=0;i<N;i++){
			x.set(i,0,1);
			x.set(i, 1, Math.random()*2-1);
			x.set(i, 2, Math.random()*2-1);
			y1.set(i, 0, sign(x.get(i, 1)*x.get(i, 1)+x.get(i, 2)*x.get(i, 2)-0.6));
		}
	}
	
	public void getNoise(){// in: y1
		for(int i=0;i<N;i++){
			if(Math.random()<0.2){
				y1.set(i, 0,-y1.get(i, 0));
			}
		}
	}
	
	//feature transform
	public void featureTR(){//in:x,out:z
		for(int i=0;i<N;i++){
			z.set(i, 0, 1);
			z.set(i, 1, x.get(i, 1));
			z.set(i, 2, x.get(i, 2));
			z.set(i, 3,	x.get(i, 1)*x.get(i, 2));
			z.set(i, 4, x.get(i, 1)*x.get(i, 1));
			z.set(i, 5, x.get(i, 2)*x.get(i, 2));
		}
	}

	//sign function
	public int sign(double x){
		return x>=0?1:-1;
	}
	//formula computation for weights
	public void wComp1(Matrix in,Matrix label, Matrix outW){
		outW=(in.transpose().times(in)).inverse().times(in.transpose()).times(label);
	}
	
	//formula computation for weights
	public void wComp2(Matrix in,Matrix label, Matrix outW){
		outW=(in.transpose().times(in)).inverse().times(in.transpose()).times(label);
	}
	
	public double errComp(Matrix in,Matrix w){
		Matrix label=in.times(w);
		int errN=0;
		for(int i=0;i<N;i++){
			if (sign((double)label.get(i, 0))!=y1.get(i, 0))
				errN++;
		}
		return (double) errN/N;
	}
	public static void main(String[] args){
		linearReg a=new linearReg();
		a.featureTR();
		a.getNoise();
		a.wComp1(a.x, a.y1, a.w1);
		a.wComp2(a.z, a.y1, a.w2);
		
		System.out.println(a.errComp(a.z, a.w2));
		System.out.println(a.errComp(a.x, a.w1));
		System.out.println(a.w2.toString());
	}
	
}
