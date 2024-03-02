package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Shibam Mukhopadhyay
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The capacity of the associative array (space allocated for the array)
   */
  int capacity = DEFAULT_CAPACITY;
  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> copyArray = new AssociativeArray<K, V>();
    copyArray.size = 0;
    for (int i = 0; i < this.size; i++){
      if(copyArray.size >= copyArray.capacity){
        copyArray.expand();
        copyArray.capacity = 2 * copyArray.capacity;
      }//if
      copyArray.pairs[i] = new KVPair<K, V>(this.pairs[i].key, this.pairs[i].value);
      copyArray.size++;
    }//for
    return copyArray;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    if (this.size() == 0){
      return "{}";
    }//if empty
    String arrayTotal = this.pairs[0].key + ": " + this.pairs[0].value; //putting index 0 here so that for loop can add "," to string
    for (int i = 1; i < size; i++) {
      arrayTotal = arrayTotal + ", " + this.pairs[i].key + ": " + this.pairs[i].value;
    }//for
    return "{ " + arrayTotal + " }";  
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null){
      throw new NullKeyException();
    } //if key is null
    if (this.size >= this.capacity){
      this.expand();
      this.capacity = 2 * this.capacity;
    } //if we need bigger array
    try {
      int i = find(key);
      this.pairs[i].value = value;
    } //try if key exists, we set previous value to given value 
    catch (KeyNotFoundException e){
      this.pairs[size] = new KVPair<K, V>(key, value);
      size++;
    } //create new KVPair and add that to the end of the array 
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    int i = find(key);
    return this.pairs[i].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    if (key == null){
      return false;
    }
    try{    
      find(key);
      return true;
    }
    catch(KeyNotFoundException e){
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int i = find(key); 
      this.pairs[i] = this.pairs[size-1];
      this.pairs[size-1] = new KVPair<>(); 
      size--;
    } // try
    catch(KeyNotFoundException e) {
      //do nothing
    } // catch
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < this.size(); i++){
      if (this.pairs[i].key == null) {
        throw new KeyNotFoundException(); 
      }//if null key 
       if (this.pairs[i].key.equals(key)) {
         return i; 
       } //if key in array equals key
     } //for
     throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray
