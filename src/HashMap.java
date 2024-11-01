import java.util.ArrayList;

public class HashMap
{
    private static final int DEFAULT_TABLE_SIZE = 5;
    private static final int RADIX = 256;
    private static final String INVALID = "INVALID KEY";
    private int tableSize;
    private int totalFilledSlots;
    private KeyValueEntry[] hashMap;
    private final ArrayList<KeyValueEntry> previouslyHashed = new ArrayList<>();

    public HashMap()
    {
        hashMap = new KeyValueEntry[DEFAULT_TABLE_SIZE];
        tableSize = DEFAULT_TABLE_SIZE;
        totalFilledSlots = 0;
    }

    public String findValue(String key)
    {
        int currentIndex = hashSingleString(key);


        while (hashMap[currentIndex] != null && !hashMap[currentIndex].getKey().equals(key))
        {
            currentIndex = (currentIndex + 1) % tableSize;
        }
        if (hashMap[currentIndex] == null)
        {
            return INVALID;
        }
        return hashMap[currentIndex].getValue();
    }

    public void addPair(String key, String value)
    {
        // Check if the table needs to be increased in size.
        if (++totalFilledSlots > (tableSize / 2))
        {
            resizeTable(tableSize * 2);
        }

        int currentProbeIndex = hashSingleString(key);
        // While the location isn't empty, keep checking for an open spot.
        while (hashMap[currentProbeIndex] != null)
        {
            currentProbeIndex = (currentProbeIndex + 1) % tableSize;
        }

        KeyValueEntry keyToAdd = new KeyValueEntry(key, value);
        hashMap[currentProbeIndex] = keyToAdd;
        previouslyHashed.add(keyToAdd);
    }

    private void resizeTable(int newSize)
    {
        hashMap = new KeyValueEntry[newSize];
        tableSize = newSize;

        for (KeyValueEntry pair : previouslyHashed)
        {
            hashMap[hashSingleString(pair.getKey())] = pair;
        }
    }

    // Helper method that hashes a single string using the Rabin-Karp algorithm.
    private int hashSingleString(String str)
    {
        int hash = 0;

        for (int i = 0; i < str.length(); i++)
        {
            hash = (hash * RADIX + str.charAt(i)) % tableSize;
        }
        return hash;
    }
}
