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
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";
    private static final int PRIME = 999983;
    private static final int RADIX = 256;
    private ArrayList<KeyValueEntry>[] ar = new ArrayList[1000000];

    public Finder()
    {
        // Initialize ar with arrayLists.
        for (int i = 0; i < ar.length; i++) {
            ar[i] = new ArrayList<>();
        }
    }

    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException
    {
        String entireString;

        while ((entireString = br.readLine()) != null)
        {
            String[] columns = entireString.split(",");

            String currentKey = columns[keyCol];
            String currentVal = columns[valCol];

            ar[hashSingleString(currentKey)].add(new KeyValueEntry(currentKey, currentVal));
        }
        br.close();
    }

    public String query(String key) throws IOException
    {
        int hashedKey = hashSingleString(key);

        ArrayList<KeyValueEntry> currentArLocation = ar[hashedKey];

        if (ar[hashedKey].size() > 1)
        {
            for (int i = 0; i < ar[hashedKey].size(); i++)
            {
                KeyValueEntry currentEntry = currentArLocation.get(i);

                if (currentEntry.getKey().equals(key))
                {
                    return currentEntry.getValue();
                }
            }
        }
        else if (!currentArLocation.isEmpty())
        {
            return currentArLocation.getFirst().getValue();
        }


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