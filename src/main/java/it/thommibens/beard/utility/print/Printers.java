package it.thommibens.beard.utility.print;

public class Printers {
	
	public static void print(String s) {
		System.out.print(s);
	}
	
	public static void print(int i) {
		System.out.print(String.valueOf(i));
	}
	
	public static void print(long i) {
		System.out.print(String.valueOf(i));
	}
	
	public static void print(char c) {
		System.out.print(String.valueOf(c));
	}
	
	public static void print(char[] c) {
		System.out.print(String.valueOf(c));
	}
	
	public static void print(Object o) {
		System.out.print(String.valueOf(o));
	}
	
	public static void print(float i) {
		System.out.print(String.valueOf(i));
	}

	public static void print(String template,Object... params) {
		System.out.print(String.format(template, params));
	}
	
	
	public static void println(String s) {
		System.out.print(s);
		println();
	}
	
	public static void println(int i) {
		System.out.print(String.valueOf(i));
		println();
	}
	
	public static void println(long i) {
		System.out.print(String.valueOf(i));
		println();
	}
	
	public static void println(char c) {
		System.out.print(String.valueOf(c));
		println();
	}
	
	public static void println(char[] c) {
		System.out.print(String.valueOf(c));
		println();
	}
	
	public static void println(Object o) {
		System.out.print(String.valueOf(o));
		println();
	}
	
	public static void println(float i) {
		System.out.print(String.valueOf(i));
		println();
	}

	public static void println(String template, Object... params) {
		System.out.print(String.format(template, params));
		println();
	}
	
	public static void println() {
		System.out.println();
	}

}
