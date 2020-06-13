package in.thelosergeek.community_app.firebasemessagingservice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA0LturKA:APA91bEDJgvQnYVROOgHheHCDBaEH45NmepmAHM7KWjj6mqqkxiqDq7KWHs6VERajlbxERn_0eNL_zCMUzHhWRzLujA_6kDJjqDFw-YieF6tdc9wPCRFyMMvXn4qkSmklOnOwuwqOgDq"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}


//20:12 16
