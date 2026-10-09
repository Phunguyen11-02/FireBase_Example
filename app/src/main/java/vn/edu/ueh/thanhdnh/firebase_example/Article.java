package vn.edu.ueh.thanhdnh.firebase_example;

import com.google.firebase.firestore.PropertyName;

public class Article {
  private String id;
  private String title;
  private String content;
  @PropertyName("image_url")
  private String image_url;

  public Article() {
    this.id = "";
    this.title = "";
    this.content = "";
    this.image_url = "";
  }

  public Article(String title, String content, String image_url) {
    this.id = "";
    this.title = title;
    this.content = content;
    this.image_url = image_url;
  }

  public Article(String id, String title, String content, String image_url) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.image_url = image_url;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @PropertyName("image_url")
  public String getImage_url() {
    return image_url;
  }

  @PropertyName("image_url")
  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }

  @Override
  public String toString() {
    return "Article{" +
      "id='" + id + '\'' +
      ", title='" + title + '\'' +
      ", content='" + content + '\'' +
      ", image_url='" + image_url + '\'' +
      '}';
  }
}
