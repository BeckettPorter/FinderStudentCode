import java.util.ArrayList;

public class HashMap
{
    private static final int DEFAULT_TABLE_SIZE = 100000;
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

    // Search through the array to find a given key, return INVALID if not found.
    public String findValue(String key)
    {
        int keyLocation = findKeyInTable(key);

        if (keyLocation == -1)
        {
            return INVALID;
        }
        return hashMap[keyLocation].getValue();
    }

    // Find the index of a key in the table, return -1 if not found.
    private int findKeyInTable(String key)
    {
        int currentIndex = hashSingleString(key);

        while (hashMap[currentIndex] != null)
        {
            if (hashMap[currentIndex].getKey().equals(key))
            {
                return currentIndex;
            }
            currentIndex = (currentIndex + 1) % tableSize;
        }
        return -1;
    }

    // Add a key value pair to the array, adding it to the previously hashed arrayList if it wasn't already hashed.
    public void addPair(String key, String value, boolean wasPreviouslyHashed)
    {
        // Check if the table needs to be increased in size.
        if (++totalFilledSlots > (tableSize / 2))
        {
            resizeTable(tableSize * 2);
        }

        KeyValueEntry keyToAdd = new KeyValueEntry(key, value);
        hashMap[findOpenTableLocation(key)] = keyToAdd;

        if (!wasPreviouslyHashed)
        {
            previouslyHashed.add(keyToAdd);
        }
    }

    // Method to resize the table array and rehash previously added values.
    private void resizeTable(int newSize)
    {
        hashMap = new KeyValueEntry[newSize];
        tableSize = newSize;

        for (KeyValueEntry pair : previouslyHashed)
        {
            addPair(pair.getKey(), pair.getValue(), true);
        }
    }

    // Find the next open table location given a key.
    private int findOpenTableLocation(String key)
    {
        // Search until an empty spot is found to add the new hash.
        int currentProbeIndex = hashSingleString(key);

        // While the location isn't empty, keep checking for an open spot.
        while (hashMap[currentProbeIndex] != null)
        {
            currentProbeIndex = (currentProbeIndex + 1) % tableSize;
        }

        return currentProbeIndex;
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