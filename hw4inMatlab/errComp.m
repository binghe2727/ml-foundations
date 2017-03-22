function error=errComp(x,y,w)
    [m,n]=size(x);
    error=1-sum(sign(x*w)==y)/m;
end