%see the question carefully
%1.log 10
%can minux loop
%main function
%matlab looop parameter not successful
%https://zhidao.baidu.com/question/146910567.html---by testing changed i
%baidu not correct
%find the reason lambda and labmda---attention to use the coding tool
%software to avoid this mistakes so that your progress can be speeded up.so
%
clc;
trainData=load('trainingData.txt');
testData=load('testData.txt');
Xtrain=trainData(:,[1,2]);
[m,n]=size(Xtrain);
Xtrain=[ones(m,1),Xtrain];
ytrain=trainData(:,3);

Xtest=testData(:,[1,2]);
[m,n]=size(Xtest);%%%%duplicate values can it influence the performance
Xtest=[ones(m,1),Xtest];
ytest=testData(:,3);

% lambda=10^-3;
% errComp(Xtrain,ytrain,lgRegu(Xtrain,ytrain,lambda))%ein
% errComp(Xtest,ytest,lgRegu(Xtrain,ytrain,lambda))%eout

eIn=[];
eOut=[];
j=-10;
for i= -10:1:2
    i;
    lambda=10^(i);
    errComp(Xtrain,ytrain,lgRegu(Xtrain,ytrain,lambda));
    errComp(Xtest,ytest,lgRegu(Xtrain,ytrain,lambda));
    eIn=[eIn,errComp(Xtrain,ytrain,lgRegu(Xtrain,ytrain,lambda))]
    eOut=[eOut,errComp(Xtest,ytest,lgRegu(Xtrain,ytrain,lambda))]
    j=j+1;
end


errComp(Xtrain,ytrain,lgRegu(Xtrain,ytrain,10^1));
errComp(Xtrain,ytrain,lgRegu(Xtrain,ytrain,10^2));%by this debugging it can change


    %%errComp(Xtest,ytest,lgRegu(Xtrain,ytrain,lambda))
[m,i]=min(eIn);
m
eOut(i)
i-11


[m,i]=min(eOut);
m
eIn(i)
i-11

%16-17  
%lambda = 10^-3;  
%Wreg = LGwithRegularization(Xtrain(1:120,:),ytrain(1:120,:),lambda);  
%Etrain = Error01(Xtrain(1:120,:),ytrain(1:120,:),Wreg)  
%Eval = Error01(Xtrain(121:200,:),ytrain(121:200,:),Wreg)  
%Eout = Error01(Xtest,ytest,Wreg) 

%18  
%lambda = 10^0;  
%Wreg = LGwithRegularization(Xtrain,ytrain,lambda);  
%Ein = Error01(Xtrain,ytrain,Wreg)  
%Eout = Error01(Xtest,ytest,Wreg)  
  
%19  
%lambda = 10^-6  
%Ecv = 0;  
%v = 5;  
%per = m / v;  
%for i = 1:v,  
%    Xtemp = Xtrain;  
%    ytemp = ytrain;  
%    Xtemp(1+(i-1)*per:i*per,:) = [];%出去用于求交叉验证的样本  
%    ytemp(1+(i-1)*per:i*per,:) = [];  
%    Wreg = LGwithRegularization(Xtemp,ytemp,lambda);  
%    Error01(Xtrain(1+(i-1)*per:i*per,:),ytrain(1+(i-1)*per:i*per,:),Wreg)%利用交叉验证的样本求Ecv  
%    Ecv = Ecv + Error01(Xtrain(1+(i-1)*per:i*per,:),ytrain(1+(i-1)*per:i*per,:),Wreg);  
%end  
%Ecv = Ecv / v  
  
%20  
%lambda = 10^-8;  
%Wreg = LGwithRegularization(Xtrain,ytrain,lambda);  
%Ein = Error01(Xtrain,ytrain,Wreg)  
%Eout = Error01(Xtest,ytest,Wreg)  