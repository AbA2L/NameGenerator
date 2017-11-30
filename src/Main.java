/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main {

	static String alpha = "*abcdefghijklmnopqrstuvwxyz'$";
	static int ln = alpha.length();
	static int[][] t = new int[ln][ln];
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Name generator coded by aba2l");
		initTab();
		readListe("words");
		System.out.print("Getting markov chaines...	");
		String[] ch = convertToChain();
		System.out.println("done");
		while (true){
			String s = "*";
			int n;
			while (s.charAt(s.length()-1)!='$'){
				n = convertToInt(s.charAt(s.length()-1)+"")[0];
				s=s+ch[n].charAt(new Random().nextInt((ch[n].length()-1 - 0) + 1) + 0);
			}
			System.out.println("	"+s.replace("*", "").replace("$", ""));
			Thread.sleep(1000);
		}
	}
	
	public static void readListe(String f){
		System.out.print("Reading liste...	");
		BufferedReader br = null;
        String strLine = "";
        try {
            br = new BufferedReader( new FileReader(f));
            while((strLine =br.readLine()) != null){
            	if (strLine == ""){
            		strLine = "''''";
            	}
            	strLine= alpha.charAt(0)+strLine.replace("*", "").replace("$", "")+alpha.charAt(ln-1);
            	addToTheTable(convertToInt(strLine));
            	//System.out.println(strLine);
            }
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: "+f);
        } catch (IOException e) {
            System.err.println("Unable to read the file: "+f);
        }
	}
	
	public static int[] convertToInt(String w){
		w=w.toLowerCase();
		int l = w.length();
		int[] W = new int[l];
		boolean b;
		char r;
		for (int i=0; i<l; i++){
			r = w.charAt(i);
			for (int j=0; j<ln; j++){
				b= false;
				if (alpha.charAt(j)==r){
					W[i]=j;
					j=ln;
					b= true;
				}
				if (b == false && j==ln-1){
					W[i]=ln-2;
				}
			}
		}
		return W;
	}
	
	public static String[] convertToChain(){
		String[] ch = new String[ln];
		for (int x=0; x<ln ; x++){
			ch[x]="";
			for (int y=0; y<ln ; y++){
				for (int i=0; i<t[x][y]; i++){
					ch[x]=ch[x]+alpha.charAt(y);
				}
			}
		}
		return ch;
	}
	
	public static void convertTable(){
		int T;
		for (int x=0; x<ln; x++){
			T=0;
			for (int y=0; y<ln; y++){
				T=T+t[x][y];
			}
			if (T!=0){
				for (int y=0; y<ln; y++){
					t[x][y]=t[x][y]*100/T;
				}
			}
		}
	}
	
	public static void addToTheTable(int[] tb){
		for (int i=0; i<(tb.length-1); i++){
			t[tb[i]][tb[i+1]]=t[tb[i]][tb[i+1]]+1;
		}
	}
	
	public static void initTab(){
		System.out.print("Initialising table...	");
		for (int x=0; x<ln; x++){
			for (int y=0; y<ln; y++){
				t[x][y]=0;
			}
		}
		System.out.println("done");
	}
	
	public static void displayTable(){
		System.out.print("	");
		for (int i=0; i<ln; i++){
			System.out.print(alpha.charAt(i)+"	");
		}
		System.out.println("\n");
		for (int x=0; x<ln; x++){
			System.out.print(alpha.charAt(x)+"	");
			for (int y=0; y<ln; y++){
				System.out.print(t[x][y]+"	");
			}
			System.out.println("\n");
		}
	}
}
