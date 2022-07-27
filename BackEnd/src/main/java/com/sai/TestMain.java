//package com.sai;
//
//import java.util.Random;
//
//public class TestMain {
//
//	public static void main(String[] args) {
//		createRandomFamilyId();
//	}
//
//	private static String createRandomFamilyId() {
//		int leftLimit = 48; // numeral '0'
////		int rightLimit = 90; // letter 'Z'
//		int rightLimit = 57; // letter 'Z'
//		int targetStringLength = 6;
//		Random random = new Random();
//
//		String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65))
//				.limit(targetStringLength)
//				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//
//		System.out.println(generatedString);
//		return generatedString;
//	}
//}
