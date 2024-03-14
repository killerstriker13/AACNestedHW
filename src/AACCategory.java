import structures.AssociativeArray;
import structures.KVPair;
import structures.KeyNotFoundException;
import structures.NullKeyException;

/**
 * Creates a mapping using AssociativeArrays between image locations and their descriptions
 * 
 * @author Catie Baker
 * @author Shibam Mukhopadhyay
 */

public class AACCategory {
  String name;

  AssociativeArray<String,String> category;

  public AACCategory (String name) {
    this.name = name;
    this.category = new AssociativeArray<String,String>();
  }

  public void addItem(String imageLoc, String text){
    try{
      this.category.set(imageLoc, text);
    }
    catch(NullKeyException e){
    }
  }

  public String getCategory(){
    return this.name;
  }

  public String[] getImages(){
    String[] arr = new String[this.category.size()];
    int i = 0;
    for(KVPair<String, String> pair : this.category){
      arr[i++] = pair.getKey();
    }
    return arr;
  }

  public String getText(String imageLoc)throws KeyNotFoundException{
    try{
      return category.get(imageLoc);
    } catch (KeyNotFoundException e){
            throw new KeyNotFoundException();
    }
  }

  public boolean hasImage(String imageLoc){
    return this.category.hasKey(imageLoc);
  }

  public String toString() {
    String result = "";
    for (KVPair<String, String> pair : this.category) {
      result = result + ">" + pair.getKey() + " " + pair.getVal() + "\n";
    }
    return result;
  }
}