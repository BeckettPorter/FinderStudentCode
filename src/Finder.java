import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Beckett Porter
 * Completed on: October 26th 2024
 **/

public class Finder
{
    // Variables.
    private static final String INVALID = "INVALID KEY";
    private static final int PRIME = 999983;
    private static final int RADIX = 256;
    private ArrayList<KeyValueEntry>[] ar = new ArrayList[PRIME];

    // Constructor.
    public Finder()
    {

    }

    // Build table method which initializes the ar array with the correct KeyValueEntries.
    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException
    {
        String currentRow;

        // While there are still lines to be read in the file:
        while ((currentRow = br.readLine()) != null)
        {
            // Separate the current row by commas.
            String[] columns = currentRow.split(",");

            // Get the currentKeys and currentVals at the keyCol and valCol.
            String currentKey = columns[keyCol];
            String currentVal = columns[valCol];

            int hashedIndex = hashSingleString(currentKey);

            if (ar[hashedIndex] == null)
            {
                ar[hashedIndex] = new ArrayList<>();
            }

            // Add a new KeyValueEntry at the corresponding key ArrayList
            ar[hashedIndex].add(new KeyValueEntry(currentKey, currentVal));
        }
        br.close();
    }

    // Query method which takes in a key and returns the corresponding value.
    public String query(String key) throws IOException
    {
        // Get the value of the key as a hash.
        int hashedKey = hashSingleString(key);

        // Variable for the current location in the array (an ArrayList).
        ArrayList<KeyValueEntry> currentArLocation = ar[hashedKey];

        // If the location at the index hashedString in the array is empty, this means the key is not valid.
        if (currentArLocation == null)
        {
            return INVALID;
        }

        // If the current ArrayList has more than one entry (i.e. there was a collision), go and manually check.
        if (currentArLocation.size() > 1)
        {
            // Go through each KeyValueEntry in the current ArrayList.
            for (KeyValueEntry currentEntry : currentArLocation)
            {
                // If the currentEntry's key is equal to out desired key, return the corresponding value.
                if (currentEntry.getKey().equals(key))
                {
                    return currentEntry.getValue();
                }
            }
        }
        // Else if the ArrayList only has one entry, just return the first KeyValueEntry's value.
        else if (!currentArLocation.isEmpty())
        {
            return currentArLocation.getFirst().getValue();
        }

        // If no entries were in the ArrayList, return INVALID. (ie. the key that was passed in
        // doesn't exist in the file).
        return INVALID;
    }

    // Helper method I made that hashes a single string using the Rabin-Karp algorithm.
    private static int hashSingleString(String str)
    {
        int hash = 0;

        for (int i = 0; i < str.length(); i++)
        {
            hash = (hash * RADIX + str.charAt(i)) % PRIME;
        }
        return hash;
    }
}