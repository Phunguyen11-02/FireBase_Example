package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  FirebaseFirestore db;
  Button btAdd, btShow, btPasteImage;
  EditText etTitle, etContent, etImageUrl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();
    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    btPasteImage = findViewById(R.id.btPasteImage);
    etTitle = findViewById(R.id.etTitle);
    etContent = findViewById(R.id.etContent);
    etImageUrl = findViewById(R.id.etImageUrl);
    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
    if (btPasteImage != null) {
      btPasteImage.setOnClickListener(this);
    }
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btAdd) {
      String title = etTitle.getText().toString();
      String content = etContent.getText().toString();
      String imageUrl = etImageUrl.getText().toString();

      Map<String, Object> articleData = new HashMap<>();
      articleData.put("title", title);
      articleData.put("content", content);
      articleData.put("image_url", imageUrl);

      db.collection("articles").add(articleData);
      etTitle.setText("");
      etContent.setText("");
      etImageUrl.setText("");
      Toast.makeText(this, "Article added successfully!", Toast.LENGTH_SHORT).show();
    } else if (view.getId() == R.id.btShow) {
      Intent intent = new Intent(getBaseContext(), ShowDataActivity.class);
      startActivity(intent);
    } else if (view.getId() == R.id.btPasteImage) {
      ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
      if (clipboard != null && clipboard.hasPrimaryClip()) {
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
          ClipData.Item item = clip.getItemAt(0);
          CharSequence pasteData = item.getText();
          if (pasteData != null) {
            etImageUrl.setText(pasteData.toString().trim());
            Toast.makeText(this, "Pasted URL from clipboard!", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(this, "Clipboard text is empty", Toast.LENGTH_SHORT).show();
          }
        } else {
          Toast.makeText(this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }
      } else {
        Toast.makeText(this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
