package com.example.survey;

public class Matrices {

        public static void main(String args[]){
            int a[][] = {{2,1,4},{3,2,1},{5,7,2}};
            int b[][] = {{5,5,5},{3,2,1},{3,5,7}};
            int c[][] = new int[3][3];
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    c[i][j]=0;
                    for(int k=0; k<3; k++){
                        c[i][j] += a[i][k]*b[k][j];
                    }
                    System.out.print(c[i][j] + " ");
                }
            }

        }}

