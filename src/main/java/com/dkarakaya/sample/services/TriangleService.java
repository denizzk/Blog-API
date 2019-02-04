package com.dkarakaya.sample.services;

public class TriangleService {

	public String checkTriangle(int a, int b, int c) {
		
		if(a==b && b==c)
            return "Equilateral";

        else if(a >= (b+c) || c >= (b+a) || b >= (a+c) )
            return "Not a triangle";

        else if ((a==b && b!=c ) || (a!=b && c==a) || (c==b && c!=a))
            return "Isosceles";

        else 
            return "Scalene";
	}

}
