import java.util.*;
public class LinkedHashMapInterface<K,V> {
	public LinkedHashMap<K, V> linkedhashmap;
	public LinkedHashMapInterface(LinkedHashMap<K,V> linkedhashmap) throws NullPointerException {
		if(linkedhashmap == null)
			throw new NullPointerException("LinkedHashMapInterface contructor parameter is null");
		this.linkedhashmap = linkedhashmap;
	}
	
	public void Key(K key) {
		// This is to override if you want to use this feature.
	}
	
	public void Value(V value) {
		// This is to override if you want to use this feature.
	}
	
	public void KeyAndValue(K key,V value) {
		// Use inheritance if you want to use this method.
	}
	
	public void iterate() {
		Set<K> keys= linkedhashmap.keySet();
		for(K key:keys) {
			Key(key);
			V value=linkedhashmap.get(key);
			Value(value);
			KeyAndValue(key,value);
		}
	}
	
	public V getFirstValue() {
		Set<K> keys= linkedhashmap.keySet();
		if(keys == null)
			return null;
		for(K key:keys) {
			V value=linkedhashmap.get(key);
			return value;
		}
		return null;
	}
}
		