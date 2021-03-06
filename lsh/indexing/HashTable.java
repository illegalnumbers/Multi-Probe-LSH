package indexing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class HashTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private List<HashFunction> hashFunctionTable;
	private int numberOfHashFunctions;
	private Map<HashBucket, SearchableObject> objectIndex;
	
	public HashTable(int numberOfHashFunctions, int numberOfDimensions, double slotWidthW, Random randomNumberGenerator) {
		
		this.hashFunctionTable = new ArrayList<HashFunction>(numberOfHashFunctions);
		for (int hashFunctionCounter = 0; hashFunctionCounter < numberOfHashFunctions; ++hashFunctionCounter) {
			this.hashFunctionTable.add(new HashFunction(numberOfDimensions, slotWidthW, randomNumberGenerator));
		}
		
		this.numberOfHashFunctions = numberOfHashFunctions;
		this.objectIndex = new HashMap<HashBucket, SearchableObject>();
	}

	public HashTable(int numberOfHashFunctions, int numberOfDimensions, double slotWidthW, double[][] eigenVectors) {
		this.hashFunctionTable = new ArrayList<HashFunction>(numberOfHashFunctions);

		this.numberOfHashFunctions = numberOfHashFunctions;

        for (int hashFunctionCounter = 0; hashFunctionCounter < numberOfHashFunctions; ++hashFunctionCounter) {
            this.hashFunctionTable.add(new HashFunction(numberOfDimensions, slotWidthW, eigenVectors[hashFunctionCounter]));
        }

		this.objectIndex = new HashMap<HashBucket, SearchableObject>();
	}

	/**
	 * @param searchableObject
	 * @return the bucket corresponding to the object
	 */
	public HashBucket getHashBucket(SearchableObject searchableObject) {
		
		List<Double> objectFeatures = searchableObject.getObjectFeatures();
		
		List<Integer> objectHashBucket = new ArrayList<Integer>(this.numberOfHashFunctions);
		for (int hashFunctionCounter = 0; hashFunctionCounter < this.numberOfHashFunctions; ++hashFunctionCounter) {
			objectHashBucket.add(this.hashFunctionTable.get(hashFunctionCounter).getSlotNumber(objectFeatures));
		}
		
		return new HashBucket(objectHashBucket);
		
	}
	
	/**
	 * @param searchableObject
	 * @return true if object is added to the index
	 */
	public boolean add(SearchableObject searchableObject) {
		
		HashBucket hashBucket = getHashBucket(searchableObject);
		if (this.objectIndex.containsKey(hashBucket)) {
			return false;
		}
		
		this.objectIndex.put(hashBucket, searchableObject);
		return true;
		
	}
	
	/**
	 * @param hashBucket
	 * @return a list of objects in the bucket
	 */
	public List<SearchableObject> getObjects(HashBucket hashBucket) {
		Set<Map.Entry<HashBucket, SearchableObject>> entrySet = this.objectIndex.entrySet();
		
		List<SearchableObject> objectsInBucket = new ArrayList<SearchableObject>();
		for (Map.Entry<HashBucket, SearchableObject> entry : entrySet) {
			if (entry.getKey().equals(hashBucket)) {
				objectsInBucket.add(entry.getValue());
			}
		}
		return objectsInBucket;
		
	}

	public List<SearchableObject> getObjects(List<Integer> hashcode) {
		Set<Map.Entry<HashBucket, SearchableObject>> entrySet = this.objectIndex.entrySet();
		HashBucket probeBucket = new HashBucket(hashcode);

		List<SearchableObject> objectsInBucket = new ArrayList<SearchableObject>();
		for (Map.Entry<HashBucket, SearchableObject> entry : entrySet) {
			if (entry.getKey().hashCode() == probeBucket.hashCode()) {
				objectsInBucket.add(entry.getValue());
			}
		}
		return objectsInBucket;

	}

	public List<SearchableObject> getAllObjects()
	{
		List<SearchableObject> retList = new ArrayList<>();
		for (HashBucket bucket : this.objectIndex.keySet())
		{
			retList.addAll(this.getObjects(bucket));
		}
		return retList;
	}

	public List<HashFunction> getHashFunctions() { return this.hashFunctionTable; }

}
