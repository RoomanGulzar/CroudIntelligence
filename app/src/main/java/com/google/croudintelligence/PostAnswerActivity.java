package com.google.croudintelligence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.croudintelligence.HelpingClass.Permission;
import com.google.croudintelligence.HelpingClass.UserDetails;
import com.google.croudintelligence.Models.Answer;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.ViewModel.Question;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.ActivityPostAnswerBinding;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAnswerActivity extends AppCompatActivity {

    private static final String TAG = "PostAnswerActivity";
    ActivityPostAnswerBinding binding;
    Api api;
    Context context;
    File file;
    final int camera_req_code = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    User user;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAnswerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = RetrofitClient.getInstance().getMyApi();
        context = this;
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = new UserDetails().getUser(sharedPreferences);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Question q = new Gson().fromJson(bundle.getString("quest"), Question.class);
            if (q == null) {
                Toast.makeText(this, "Null QUEST", Toast.LENGTH_SHORT).show();
                onBackPressed();

            } else {
                binding.imageViewAnswer.setOnClickListener(v -> {
                    if (CheckPermission()) {
                        addImage();
                    }
                });


                binding.textViewAskedQuestion.setText(q.getQuestion_text());
                binding.btnPostYourAnswer.setOnClickListener(v -> {
                    String ans = binding.edittextAnswerHere.getText().toString();
                    if (ans.equals("")) {
                        Toast.makeText(PostAnswerActivity.this, "EMPTY ANSWER!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Answer a = new Answer();
                    a.setUser_id(user.getUser_id());
                    a.setQuestion_id(q.getQuestion_id());
                    MultipartBody.Part imagePart = null;
                    a.setAnswer_text(ans);
                    if (file != null) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

                        imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                    }
                    api.AddAnswer(
                            RequestBody.create(MediaType.parse("text/plain"), a.getUser_id() + ""),
                            RequestBody.create(MediaType.parse("text/plain"), a.getQuestion_id() + ""),
                            RequestBody.create(MediaType.parse("text/plain"), a.getAnswer_text()),
                            imagePart
                    ).enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(PostAnswerActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PostAnswerActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                        }
                    });
                });

            }
        } else {
            onBackPressed();
            Toast.makeText(this, "This question has no answer", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean CheckPermission() {
        try {
            Permission p = new Permission();
            return p.seekGalleryPermission(context) && p.seekCameraPermission(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private final ActivityResultLauncher<Intent> someActivityResultLauncher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    try {
                        if (data != null) {
                            file = FileUtil.from(context, data.getData());
                            binding.imageViewAnswer.setImageURI(data.getData());
                        }
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }
            }
    );

    private void addImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select one Option");
        String[] choiceArr = {"Camera ", "Gallery"};
        builder.setItems(choiceArr, (dialog, which) -> {
            if (which == 0) {
                //Camera...
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, camera_req_code);
            } else {
                //Gallery
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == camera_req_code) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Bundle bundle = data.getExtras();
                Bitmap bmpImage = (Bitmap) bundle.get("data");
                ByteArrayOutputStream outputsStream = new ByteArrayOutputStream();
                bmpImage.compress(Bitmap.CompressFormat.PNG, 100, outputsStream);
                binding.imageViewAnswer.setImageBitmap(bmpImage);
                file = new File(getCacheDir(), "image.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmpImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}