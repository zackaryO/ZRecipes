// java/com/tracks/zrecipes/db/Recipe.java
package com.tracks.zrecipes.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Recipe")
public class Recipe {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "usedIngredientCount")
    private int usedIngredientCount;

    @ColumnInfo(name = "missedIngredientCount")
    private int missedIngredientCount;

    @ColumnInfo(name = "likes")
    private int likes;

    // Default constructor
    public Recipe() {}

    // Constructor with all fields
    public Recipe(int id, String title, String image, int usedIngredientCount, int missedIngredientCount, int likes) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.usedIngredientCount = usedIngredientCount;
        this.missedIngredientCount = missedIngredientCount;
        this.likes = likes;
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public void setUsedIngredientCount(int usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(int missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Recipe{" +
//                "_id=" + _id +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", usedIngredientCount='" + usedIngredientCount + '\'' +
                '}';
    }


}


//
//@Entity
//public class Recipe {
//
//    //@Ignore
//    public Recipe(int id, String title, String image, String usedIngredientCount) {
//        this.id = id;
//        this.title = title;
//        this.image = image;
//        this.usedIngredientCount = usedIngredientCount;
//
//    }
//
////    public Recipe(int _id, int id, String title, String image, String usedIngredientCount) {
////        this._id = _id;
////        this.id = id;
////        this.title = title;
////        this.image = image;
////        this.usedIngredientCount = usedIngredientCount;
////    }
//
////    public String getCourse_code() {
////        return course_code;
////    }
//
////    public void setCourse_code(String course_code) {
////        this.course_code = course_code;
////    }
//
//
////    @PrimaryKey(autoGenerate = true)
////    private int _id;
//    @PrimaryKey
//    @ColumnInfo(name = "recipe_id")
//    private int id;
//    @ColumnInfo(name = "recipe_name")
//    private String title;
//    @ColumnInfo(name = "image")
//    private String image;
//    @ColumnInfo(name = "usedIngredientCount")
//    private String usedIngredientCount;
//
////    public int get_id() {
////        return _id;
////    }
////
////    public void set_id(int _id) {
////        this._id = _id;
////    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getUsedIngredientCount() {
//        return usedIngredientCount;
//    }
//
//    public void setUsedIngredientCount(String usedIngredientCount) {
//        this.usedIngredientCount = usedIngredientCount;
//    }
//
//    @Override
//    public String toString() {
//        return "Recipe{" +
////                "_id=" + _id +
//                ", id='" + id + '\'' +
//                ", title='" + title + '\'' +
//                ", image='" + image + '\'' +
//                ", usedIngredientCount='" + usedIngredientCount + '\'' +
//                '}';
//    }
//}
//
