package backend.DateTimeConverter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// DateTimeConverter.DateTimeConverter class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Provides helper functions to convert ZonedDateTime.toString to ZonedDateTime, format ZonedDateTime,
// convert LocalDateTime.toString to LocalDateTime, and format LocalDateTime.
//
public class DateTimeConverter {
    // Pattern to accept all variations of ZonedDateTime.now() formatting
    private static final Pattern ZONED_DATE_TIME_PATTERN = Pattern.compile(
            "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d{1,9})?)([-+]\\d{2}:\\d{2})\\[(.+)\\]");

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // parseZonedDateTimeFromString
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Takes in a String ZonedDateTime.toString and converts it back into and returns a ZonedDateTime.
    //
    public static ZonedDateTime parseZonedDateTimeFromString(String zonedDateTimeString) {
        Matcher matcher = ZONED_DATE_TIME_PATTERN.matcher(zonedDateTimeString);
        if (matcher.matches()) {
            String dateTimePart = matcher.group(1);
            String offsetPart = matcher.group(2);
            String zoneIdPart = matcher.group(3);

            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimePart + offsetPart);
            zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(zoneIdPart));

            return zonedDateTime;
        } else {
            throw new DateTimeParseException("Invalid format", zonedDateTimeString, 0);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // formatZonedDateTime
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Takes in a ZonedDateTime, formats it into the format "Day, Month, Year, Time, AM/PM", and returns
    // a formatted String ZonedDateTime.
    //
    public static String formatZonedDateTime(ZonedDateTime zonedDateTime) {
        // Convert the ZonedDateTime to the system's current time zone
        ZonedDateTime systemTimeZoneZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());

        // Create a DateTimeFormatter with the specified pattern including 'a' for AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss" + " a");

        // Format the converted ZonedDateTime using the formatter
        return systemTimeZoneZonedDateTime.format(formatter);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // formatZonedDateTimeWithSystemTimeZone
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Takes in a ZonedDateTime, formats it into the format "Day, Month, Year, Time, AM/PM, TimeZone", and
    // returns a formatted String ZonedDateTime.
    //
    public static String formatZonedDateTimeWithSystemTimeZone(ZonedDateTime zonedDateTime) {
        // Convert the ZonedDateTime to the system's current time zone
        ZonedDateTime systemTimeZoneZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());

        // Create a DateTimeFormatter with the specified pattern including 'a' for AM/PM and 'z' for timeZone
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss" + " a z");

        // Format the converted ZonedDateTime using the formatter
        return systemTimeZoneZonedDateTime.format(formatter);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // parseLocalDateTimeFromString
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Takes in a String LocalDateTime.toString and converts it back into and returns a LocalDateTime.
    //
    public static LocalDateTime parseLocalDateTimeFromString(String localDateTimeString) {
        // Define the pattern that matches the LocalDateTime.toString format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS][.SSSSSSS][.SSSSSS][.SSSSS][.SSSS][.SSS][.SS][.S]");

        // Parse the input string using the formatter
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeString, formatter);

        return localDateTime;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // formatLocalDateTime
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Takes in a LocalDateTime and formats it into the format "Day, Month, Year, Time, AM/PM" and returns
    // a formatted String LocalDateTime.
    //
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        // Create a DateTimeFormatter with the specified pattern including 'a' for AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy hh:mm:ss" + " a");

        // Format the LocalDateTime object using the formatter
        return localDateTime.format(formatter);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // formatZonedDateTime
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Example usage for functions.
    //
    public static void main(String[] args) {
        // Example usage for converting ZonedDateTime.toString back into ZonedDateTime
        String zonedDateTimeString = ZonedDateTime.now().toString();
        ZonedDateTime zonedDateTime = parseZonedDateTimeFromString(zonedDateTimeString);
        System.out.println("Parsed ZonedDateTime: " + zonedDateTime);
        System.out.println();

        // Example usage for formatting ZonedDateTime
        zonedDateTime = ZonedDateTime.now();
        String formattedZonedDateTime = formatZonedDateTime(zonedDateTime);
        System.out.println("Formatted ZonedDateTime: " + formattedZonedDateTime);
        System.out.println();

        // Example usage for formatting ZonedDateTime with system's time zone
        zonedDateTime = ZonedDateTime.now();
        String formattedZonedDateTimeWithSystemTimeZone = formatZonedDateTimeWithSystemTimeZone(zonedDateTime);
        System.out.println("Formatted ZonedDateTime with System Time Zone: " + formattedZonedDateTimeWithSystemTimeZone);
        System.out.println();

        // Example usage for converting LocalDateTime.toString back into LocalDateTime
        String localDateTimeString = LocalDateTime.now().toString();
        LocalDateTime localDateTime = parseLocalDateTimeFromString(localDateTimeString);
        System.out.println("Parsed LocalDateTime: " + localDateTime);
        System.out.println();

        // Example usage for formatting LocalDateTime with AM/PM
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        String formattedDate = formatLocalDateTime(currentLocalDateTime);
        System.out.println("Formatted LocalDateTime: " + formattedDate);
        System.out.println();
    }
}