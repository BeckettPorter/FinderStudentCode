import java.io.BufferedReader;
import java.io.IOException;

/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Beckett Porter
 * Completed on: November 5th 2024
 **/

public class Finder
{
    // Create a final HashMap variable since it will never change.
    private final HashMap hashMap = new HashMap();

    public Finder() {}

    // Build table method which initializes the hash map with the correct KeyValueEntries.
    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException
    {
        String currentRow;

        // While there are still lines to be read in the file.
        while ((currentRow = br.readLine()) != null)
        {
            // Separate the current row by commas.
            String[] columns = currentRow.split(",");

            // Get the currentKeys and currentVals at the keyCol and valCol.
            String currentKey = columns[keyCol];
            String currentVal = columns[valCol];

            // Add a key and value pair to the hashMap.
            hashMap.addPair(currentKey, currentVal, false);
        }
        br.close();
    }

    // Query method which takes in a key and returns the corresponding value, returns "INVALID KEY" if not found.
    public String query(String key) throws IOException
    {
        return hashMap.findValue(key);
    }
}