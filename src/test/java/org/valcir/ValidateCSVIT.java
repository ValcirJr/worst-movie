package org.valcir;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ValidateCSVIT {

    @Test
    void shouldMatchExpectedCsvHeader() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/Movielist.csv");
        assertNotNull(inputStream, "CSV file not found at /Movielist.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String header = reader.readLine();
            assertEquals("year;title;studios;producers;winner", header.trim(),
                    "CSV header does not match expected format");
        }
    }

    @Test
    void allRowsShouldHaveRequiredFields() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/Movielist.csv");
        assertNotNull(inputStream, "CSV file not found at /Movielist.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(";", -1);
                if (line.trim().isEmpty()) continue;
                boolean hasAllRequired = IntStream.range(0, 4).allMatch(i ->
                        i < parts.length && parts[i] != null && !parts[i].trim().isEmpty()
                );
                assertTrue(hasAllRequired,
                        "Line " + lineNumber + " is missing required fields: " + line);
            }
        }
    }

    @Test
    void allYearsShouldBeValidIntegersInAcceptedRange() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/Movielist.csv");
        assertNotNull(inputStream, "CSV file not found at /Movielist.csv");

        int currentYear = Year.now().getValue();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 || line.trim().isEmpty()) continue;
                String[] parts = line.split(";", -1);
                String yearField = parts.length > 0 ? parts[0].trim() : "";
                try {
                    int year = Integer.parseInt(yearField);
                    assertTrue(year >= 1800 && year <= currentYear,
                            "Invalid year on line " + lineNumber + ": " + year);
                } catch (NumberFormatException e) {
                    fail("Non-integer year on line " + lineNumber + ": " + yearField);
                }
            }
        }
    }

    @Test
    void shouldNotContainDuplicateLines() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/Movielist.csv");
        assertNotNull(inputStream, "CSV file not found at /Movielist.csv");

        Set<String> uniqueLines = new HashSet<>();
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 || line.trim().isEmpty()) continue;
                boolean added = uniqueLines.add(line.trim());
                assertTrue(added, "Duplicate line detected at line " + lineNumber + ": " + line);
            }
        }
    }

}