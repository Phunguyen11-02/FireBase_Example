package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
  private LayoutInflater mInflater;
  private List<Article> articleList;

  public ArticleViewAdapter(Context context, List<Article> articleList) {
    this.mInflater = LayoutInflater.from(context);
    this.articleList = articleList;
  }

  public void update(List<Article> articleList) {
    this.articleList = articleList;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View customView = mInflater.inflate(R.layout.article_item, parent, false);
    return new ArticleViewHolder(customView);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
    Article currentArticle = articleList.get(position);
    holder.getTxtTitle().setText(currentArticle.getTitle());
    holder.getTxtContent().setText(currentArticle.getContent());

    String imageUrl = currentArticle.getImage_url();
    Context context = holder.itemView.getContext();

    if (imageUrl != null && !imageUrl.trim().isEmpty()) {
      ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;

      if (networkInfo != null && networkInfo.isConnected()) {
        new DownloadImageTask(holder.getImgArticle()).execute(imageUrl.trim());
      } else {
        Toast.makeText(context, "No network connection available.", Toast.LENGTH_SHORT).show();
        holder.getImgArticle().setImageResource(R.drawable.ic_launcher_foreground);
      }
    } else {
      holder.getImgArticle().setImageResource(R.drawable.ic_launcher_foreground);
    }
  }

  @Override
  public int getItemCount() {
    return articleList.size();
  }

  private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private final ImageView imageView;

    public DownloadImageTask(ImageView imageView) {
      this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
      String urlString = urls[0];
      InputStream is = null;
      HttpURLConnection conn = null;
      try {
        URL url = new URL(urlString);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        is = conn.getInputStream();
        return BitmapFactory.decodeStream(is);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException ignored) {}
        }
        if (conn != null) {
          conn.disconnect();
        }
      }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
      if (result != null) {
        imageView.setImageBitmap(result);
      } else {
        imageView.setImageResource(R.drawable.ic_launcher_background);
      }
    }
  }
}
