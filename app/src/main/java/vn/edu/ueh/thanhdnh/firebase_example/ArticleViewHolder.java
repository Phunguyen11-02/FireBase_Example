package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private TextView txtTitle, txtContent;
  private ImageView imgArticle;

  public ArticleViewHolder(@NonNull View itemView) {
    super(itemView);
    txtTitle = itemView.findViewById(R.id.txt_title);
    txtContent = itemView.findViewById(R.id.txt_content);
    imgArticle = itemView.findViewById(R.id.img_article);
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public TextView getTxtContent() {
    return txtContent;
  }

  public ImageView getImgArticle() {
    return imgArticle;
  }
}
