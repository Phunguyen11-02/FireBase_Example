package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {
  FirebaseFirestore db;
  RecyclerView recyclerView;
  List<Article> articleList = new ArrayList<>();
  ArticleViewAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_show_data);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    FirebaseApp.initializeApp(this);

    recyclerView = findViewById(R.id.recyclerview);
    adapter = new ArticleViewAdapter(this, articleList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);

    db = FirebaseFirestore.getInstance();
    db.collection("articles").addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
        if (snapshots != null) {
          articleList.clear();
          for (QueryDocumentSnapshot q : snapshots) {
            String docId = q.getId();
            String title = q.getString("title");
            String content = q.getString("content");
            String imageUrl = q.getString("image_url");

            Article article = new Article(
                docId,
                title != null ? title : "",
                content != null ? content : "",
                imageUrl != null ? imageUrl : ""
            );
            articleList.add(article);
          }
          adapter.update(articleList);
          adapter.notifyDataSetChanged();
        }
      }
    });
  }
}
