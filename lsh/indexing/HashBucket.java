package indexing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This will identify the bucket used to store an object that has been hashed by multiple hash functions
 *
 */
public class HashBucket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private List<Integer> objectHashBucket;
	private int hashCode;
	
	public HashBucket(List<Integer> objectHashBucket) {
		this.objectHashBucket = objectHashBucket;
		this.hashCode = computeHashCode();
	}
	
	private int computeHashCode() {
		
		int hashCode = 0, binaryMultiplier = 1;
		for (int slotCounter = 0; slotCounter < this.objectHashBucket.size(); ++ slotCounter) {
			hashCode += this.objectHashBucket.get(slotCounter).intValue() * binaryMultiplier;
			binaryMultiplier *= 2;
		}
		
		return hashCode;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Override equals so that a HashBucket object can be uniquely identified
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		
		if (other instanceof HashBucket) {
			HashBucket otherHashBucket = (HashBucket) other;
			if (this.objectHashBucket.size() == otherHashBucket.objectHashBucket.size()) {
				for (int hashFunctionCounter = 0; hashFunctionCounter < this.objectHashBucket.size(); ++ hashFunctionCounter) {
					if (this.objectHashBucket.get(hashFunctionCounter).intValue() != otherHashBucket.objectHashBucket.get(hashFunctionCounter).intValue()) {
						return false;
					}
				}
				return true;
			} 
		}
		
		return false;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.hashCode;
	}
	
	/**
	 * @param perturbation
	 * @return a neighboring bucket after applying a perturbation
	 */
	public HashBucket getNeighboringBucket(List<Integer> perturbation) {
		
		assert this.objectHashBucket.size() == perturbation.size() :
			"Purturbation vector should have the same dimensions";
		
		List<Integer> neighboringBucket = new ArrayList<Integer>(this.objectHashBucket.size());
		for (int hashFunctionCounter = 0; hashFunctionCounter < this.objectHashBucket.size(); ++ hashFunctionCounter) {
			this.objectHashBucket.set(hashFunctionCounter, Integer.valueOf(this.objectHashBucket.get(hashFunctionCounter).intValue() + perturbation.get(hashFunctionCounter).intValue()));
		}
		
		return new HashBucket(neighboringBucket);
		
	}

}
