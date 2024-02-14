/**
 * Author: Calvin C
 * Date: November 6, 2023
 */

package com.example.workshop6;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BookingNumberGenerator {
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "1234567890";
    Random random = new Random();
    Set<String> generatedBookingNo = new HashSet<>();
    //method to generate a bookingNo
    public String generateBookNo(){
        String bookingNo;
        //loop a method to generate 3 letters and digits
        do {
            bookingNo = generateRandomCharacters(LETTERS, 3) + generateRandomCharacters(DIGITS, 3);
        } while (!generatedBookingNo.add(bookingNo)); //if its unique then return the generated value
        return bookingNo;
    }

    //method to generate characters
    private String generateRandomCharacters(String source, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(source.charAt(random.nextInt(source.length())));
        }
        return sb.toString();
    }

}
