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

import com.google.croudintelligence.HelpingClass.UserDetails;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.ActivitySignUpBinding;

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

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    Context context;
    File file;
    final int camera_req_code = 1;
    private static final String TAG = "SignUpActivity";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.textViewLogin.setOnClickListener(v -> {
            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
        binding.imageViewProfile.setOnClickListener(v -> {
            addImage();
        });
        Api api = RetrofitClient.getInstance().getMyApi();
        binding.btnSignUp.setOnClickListener(v -> {

            try {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String pass = binding.editTextPassword.getText().toString();
                String cPass = binding.editTextConfirmPassword.getText().toString();
                String contact = binding.editTextContact.getText().toString();
                if (name.equals("") || email.equals("") || pass.equals("") || cPass.equals("") || contact.equals("")) {
                    Toast.makeText(SignUpActivity.this, "FullFil Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    MultipartBody.Part imagePart = null;
                    if (file != null) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

                        imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                    }
                    api.RegisterUser(
                            RequestBody.create(MediaType.parse("text/plain"), name),
                            RequestBody.create(MediaType.parse("text/plain"), email),
                            RequestBody.create(MediaType.parse("text/plain"), contact),
                            RequestBody.create(MediaType.parse("text/plain"), "user"),
                            RequestBody.create(MediaType.parse("text/plain"), pass),
                            imagePart
                    ).enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            if (response.isSuccessful()) {
                                new UserDetails().setUser(editor, response.body());

                                Intent i = new Intent(SignUpActivity.this, SelectSubjectActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Toast.makeText(SignUpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception ex) {
                Log.d(TAG, ex.toString());
            }
        });
    }

    private final ActivityResultLauncher<Intent> someActivityResultLauncher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    try {
                        assert data != null;
                        file = FileUtil.from(context, data.getData());
                        binding.imageViewProfile.setImageURI(data.getData());
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }
            }
    );

    private void addImage() {
        try {
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
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            if (requestCode == camera_req_code) {
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    Bundle bundle = data.getExtras();
                    Bitmap bmpImage = (Bitmap) bundle.get("data");
                    ByteArrayOutputStream outputsStream = new ByteArrayOutputStream();
                    bmpImage.compress(Bitmap.CompressFormat.PNG, 100, outputsStream);
                    binding.imageViewProfile.setImageBitmap(bmpImage);
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
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }
}