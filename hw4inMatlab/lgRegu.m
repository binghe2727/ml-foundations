function w = lgRegu( x,y,lambda )
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here
    [m,n]=size(x);
    %w=zeros(n);not necessary
    w=(inv(x'*x+lambda*eye(n)))*x'*y;
    
end

