package edu.ucsd.cse110.team13.bof.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/* Read CSV lines, field values must not contain comma - ","
 *
 * Returns Optional.empty() if one of the following is true
 *   - column < 1
 *   - data is null
 *   - data contains leading or trailing lines & whitespace
 *   - any line read of data fails the requirement of parseLineSimple()
 */
public class CSVUtil {
    public static Optional<ArrayList<ArrayList<String>>> parse(String data, int column) {
        if(column < 1 || data == null) { return Optional.empty(); }

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        String[] lines = data.split("[\\r\\n]+",-1);
        for(String line : lines) {
            Optional<ArrayList<String>> lineReadOp = parseLineSimple(line, column);
            if(lineReadOp.isPresent()) { result.add(lineReadOp.get()); }
            else { return Optional.empty(); }
        }

        return Optional.of(result);
    }

    /* Read a CSV line, field values must not contain comma - ","
     *
     * Returns Optional.empty() if one of the following is true
     *   - column < 1
     *   - line is empty or null
     *   - line read does not match column
     */
    public static Optional<ArrayList<String>> parseLineSimple(String line, int column) {
        if(column < 1 || line == null || line.length() == 0) { return Optional.empty(); }

        String[] fields = line.split(",",-1);

        if(fields.length != column) { return Optional.empty(); }
        else { return Optional.of(new ArrayList<>(Arrays.asList(fields))); }
    }

    public static String toCSVSimple(ArrayList<ArrayList<String>> table) {
        if(table == null || table.size() == 0) { return ""; }

        final int COLUMN = table.get(0).size();
        StringBuilder stringBuilder = new StringBuilder();

        for(int row = 0; row < table.size(); row++) {
            if(table.get(row).size() != COLUMN) { return ""; }

            for(int col = 0; col < COLUMN; col++) {
                stringBuilder.append(table.get(row).get(col)).append(',');
            }

            // remove the trailing comma
            stringBuilder.setLength(stringBuilder.length()-1);

            stringBuilder.append('\n');
        }

        // remove the trailing new line
        stringBuilder.setLength(stringBuilder.length()-1);

        return stringBuilder.toString();
    }
}
