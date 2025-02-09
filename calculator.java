import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringCalculator {
    public static int add(String numbers) {
        // If the input is empty, return 0
        if (numbers.isEmpty()) {
            return 0;
        }
        
        String delimiter = ",|\n"; // Default delimiters: comma and newline
        
        // Check if a custom delimiter is defined at the start
        if (numbers.startsWith("//")) {
            int delimiterEndIndex = numbers.indexOf("\n");
            delimiter = Pattern.quote(numbers.substring(2, delimiterEndIndex)); // Properly escape special characters
            numbers = numbers.substring(delimiterEndIndex + 1); // Remove the delimiter declaration
        }
        
        String[] numArray = numbers.split(delimiter); // Split the numbers using the delimiter
        List<Integer> negatives = new ArrayList<>(); // List to track negative numbers
        int sum = 0;
        
        for (String num : numArray) {
            if (!num.isEmpty()) {
                int value = Integer.parseInt(num);
                
                if (value < 0) {
                    negatives.add(value); // Collect negative numbers
                }
                
                sum += value; // Add the number to the sum
            }
        }
        
        // If any negative numbers were found, throw an exception listing them
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negative numbers not allowed: " + negatives);
        }
        
        return sum; // Return the final sum
    }

    @Test
    public void testEmptyString() {
        assertEquals(0, add(""));
    }

    @Test
    public void testSingleNumber() {
        assertEquals(1, add("1"));
    }

    @Test
    public void testTwoNumbers() {
        assertEquals(6, add("1,5"));
    }

    @Test
    public void testMultipleNumbers() {
        assertEquals(6, add("1\n2,3"));
    }

    @Test
    public void testCustomDelimiter() {
        assertEquals(3, add("//;\n1;2"));
        assertEquals(6, add("//|\n1|2|3"));
    }

    @Test
    public void testNegativeNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> add("1,-2,3,-4"));
        assertEquals("Negative numbers not allowed: [-2, -4]", exception.getMessage());
    }
}
