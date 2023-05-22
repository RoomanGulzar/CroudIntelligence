package com.google.croudintelligence.api;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.croudintelligence.FileUtil;
import com.google.croudintelligence.Models.Answer;
import com.google.croudintelligence.Models.Questions;
import com.google.croudintelligence.Models.Rating;
import com.google.croudintelligence.Models.Topic;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.ViewModel.Question;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    public static String
            BASE_URL = "http://192.168.43.122/ProjectApi/api/project/";
    public static String
            IMAGE_URL = "http://192.168.43.122/ProjectApi/images/";


    /*
      var user_name = HttpContext.Current.Request.Form["user_name"];
                var email = HttpContext.Current.Request.Form["email"];
                var contact = HttpContext.Current.Request.Form["contact"];
                var password = HttpContext.Current.Request.Form["password"];
                var pic = HttpContext.Current.Request.Files;
    );

    */


    @Multipart
    @POST("RegisterUser")
    public Call<User> RegisterUser(
            @Part("user_name") RequestBody user_name,
            @Part("email") RequestBody email,
            @Part("contact") RequestBody contact,
            @Part("type") RequestBody type,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("ModifyUser")
    public Call<User> ModifyUser(
            @Part("user_name") RequestBody user_name,
            @Part("email") RequestBody email,
            @Part("contact") RequestBody contact,
            @Part("type") RequestBody type,
            @Part("password") RequestBody password,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part file
    );

    @GET("LoginUser")
    public Call<User> Login(@Query("email") String email, @Query("password") String pass);


    //POST ModifyUser(user user)
    @POST("ModifyUser")
    public Call<String> UpdateUser(@Body User user);


    //Topic
    @GET("AllTopics")
    public Call<ArrayList<Topic>> getAllTopics();

    //POST InsertTopic([FromBody] topic topic)
    @POST("InsertTopic")
    public Call<String> InsertTopic(Topic topic);

    //GET DeleteTopic(int id)
    @GET("DeleteTopic")
    public Call<String> DeleteTopic(@Query("id") int id);


    //InsertFavouriteTopics(List<int> topicIds, int userId)
    @POST("InsertFavouriteTopics")
    public Call<String> InsertFavouriteTopics(
            @Query("ids") String ids,
            @Query("userId") int userId
    );

    //getUserTopics(int user_id)
    @GET("getUserTopics")
    public Call<ArrayList<Topic>> getUserTopics(@Query("user_id") int user_id);

    //QUESTIONS
    //GET  AllQuestions()
    @GET("AllQuestions")
    public Call<ArrayList<Question>> getAllQuestion();

    //GET DeleteQuestion(int id)
    @GET("DeleteQuestion")
    public Call<String> DeleteQuestion(@Query("id") int id);

    //POST InsertQuestions([FromBody] Question question)
    @POST("InsertQuestion")
    public Call<String> InsertQuestion(@Body Questions question);


    //Answer
    //GET AllAnswers(int questionId)
    @GET("AllAnswers")
    public Call<ArrayList<Answer>> getAllAnswer(@Query("questionId") int questionId);

    //GET DeleteAnswer(int id)
    @GET("DeleteAnswer")
    public Call<String> DeleteAnswer(@Query("id") int id);


    //POST InsertAnswer([FromBody] Answer answer)
    @Deprecated
    @POST("InsertAnswer")
    public Call<String> InsertAnswer(@Body Answer answer);


    /*
     var userId = int.Parse(HttpContext.Current.Request.Form["user_id"]);
           var questionId = int.Parse(HttpContext.Current.Request.Form["question_id"]);
           var answerText = HttpContext.Current.Request.Form["answer_text"];
           var answer_image = HttpContext.Current.Request.Files;

   * */
    //AddAnswer()
    @Multipart
    @POST("AddAnswer")
    public Call<String> AddAnswer(
            @Part("user_id") RequestBody user_id,
            @Part("question_id") RequestBody question_id,
            @Part("answer_text") RequestBody answer_text,
            @Part MultipartBody.Part file
    );


    //RATING
    //GET AllRatings()
    @GET("AllRatings")
    public Call<ArrayList<Rating>> getAllRating();

    //POST InsertRating([FromBody] Rating rating)
    @POST("InsertRating")
    public Call<String> InsertRating(@Body Rating rating);


    @NonNull
    public default MultipartBody.Part prepareFilePart(String partName, Uri fileUri, Context context) throws IOException {
        File file = FileUtil.from(context, fileUri);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(context.getContentResolver().getType(fileUri)),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public default RequestBody createPartFromString(String descriptionString) {
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        return description;

    }
}









