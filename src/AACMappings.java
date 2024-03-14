import structures.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * Creates mapping of all the AACCategories
 * 
 * @author Catie Baker
 * @author Shibam Mukhopadhyay
 */

public class AACMappings {
  private String categoryName;
  private AACCategory category;
  private AACCategory homeCategory;
  private AssociativeArray<String, AACCategory> allCategories;
  private AssociativeArray<String, String> imageCategoryMap;

  public AACMappings(String fileInput) throws Exception {
    this.homeCategory = new AACCategory("homeCategory");
    this.allCategories = new AssociativeArray<String, AACCategory>();
    this.imageCategoryMap = new AssociativeArray<String, String>();

    File f = new File(fileInput);
    Scanner eyes = new Scanner(f);
    String input = eyes.nextLine();

    while (input != null) {
      if (input.startsWith(">")) {
        String[] segs = input.substring(1).trim().split(" ", 2);
        String imageLoc = segs[0];
        String tts = segs[1];

        this.category.addItem(imageLoc, tts);
        allCategories.set(this.categoryName, this.category);
        
      } else {
        String[] segs = input.trim().split(" ", 2);
        String imageLoc = segs[0];
        String currCategory = segs[1];

        this.imageCategoryMap.set(imageLoc, currCategory);
        this.categoryName = currCategory;
        this.homeCategory.addItem(imageLoc, currCategory);
        this.category = new AACCategory(currCategory);
        this.allCategories.set(categoryName, this.category);
      } 
      try {
        input = eyes.nextLine();
      } catch (Exception e) {
        input = null;
      }
    }
    eyes.close();
    this.categoryName = "";
    this.category = this.homeCategory;
  }



  public void add(String imageLoc, String text) throws Exception {
    if (this.categoryName.equals("")){
      this.imageCategoryMap.set(imageLoc, text);
      this.homeCategory.addItem(imageLoc, text);
      this.category = new AACCategory(text);
      this.allCategories.set(text, this.category);

    } else {
      this.category.addItem(imageLoc, text);
      allCategories.set(this.categoryName, this.category);
    }
  }


  public String getCurrentCategory() {
    return this.categoryName;
  }

  public String[] getImageLocs() {
    if (this.categoryName.equals("")) {
      return this.homeCategory.getImages();
    }
    return this.category.getImages();
  }

  public String getText(String imageLoc) throws Exception {
    if (this.categoryName.equals("")) {
      this.categoryName = this.imageCategoryMap.get(imageLoc);
      this.category = this.allCategories.get(this.categoryName);
      return this.categoryName;
    }
    return this.category.getText(imageLoc);
  }

  public boolean isCategory(String imageLoc) {
    return this.imageCategoryMap.hasKey(imageLoc);
  }


  public void reset() {
    this.categoryName = "";
    this.category = this.homeCategory;
  }

  public void writeToFile(String filename) throws IOException {
    String categoryLoc, loc, name;
    String mappingsInCat = "";
    FileWriter hands = new FileWriter(filename);
    for (KVPair<String, String> pair : this.imageCategoryMap) {
       loc = pair.getKey();
       name = pair.getVal();
       categoryLoc = loc + " " + name + "\n";
       try {
        mappingsInCat = allCategories.get(name).toString();
      } catch (KeyNotFoundException e) {
        System.err.println("KeyNotFoundException");
      }
       hands.write(categoryLoc); 
       hands.write(mappingsInCat);
    }
    hands.close();
  }
}