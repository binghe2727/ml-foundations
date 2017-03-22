function y=sign(x)%sign function
    [m,n]=size(x);
    y=zeros(m,n);
    for i=1:m
        for j=1:n
            if(x(m,n)>0)
                y(m,n)=1;
            else
                y(m,n)=-1;
            end
        end
    end
end