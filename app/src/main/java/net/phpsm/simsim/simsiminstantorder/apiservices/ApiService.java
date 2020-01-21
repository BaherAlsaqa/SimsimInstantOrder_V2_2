package net.phpsm.simsim.simsiminstantorder.apiservices;

import net.phpsm.simsim.simsiminstantorder.models.CustomerResponse;
import net.phpsm.simsim.simsiminstantorder.models.OrderLoader;
import net.phpsm.simsim.simsiminstantorder.models.responses.Avatar;
import net.phpsm.simsim.simsiminstantorder.models.responses.CheckMobile;
import net.phpsm.simsim.simsiminstantorder.models.responses.LoginResponse;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ChatObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ContactInfoObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.DeviceToken;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.HomeObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.LikeItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendRecommendedItems;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MySavedItems;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.NotificationObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.OrderObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ProviderOnOff;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.RestaurantObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.SaveItem1;
import net.phpsm.simsim.simsiminstantorder.models.responses.UpdateCustomer;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.AddProviderFavorit;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.CarBrandList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.CarColorList;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.PProfileObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.DeliveryStatusList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.FavoritesList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Messages;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyOrders;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Mypurchase;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.ProductsCategoriesList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.ProvidersCategoriesList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.ProvidersCheckin;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.QAList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveImage;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UserRecomend;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UserWallet;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Users;
import net.phpsm.simsim.simsiminstantorder.models.responses.map.ResultDistanceMatrix;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by baher on 11/26/2017.
 */

public interface ApiService {
    ///////////////////////// POST ////////////////////////
    @FormUrlEncoded
    @POST("api/v1/login")
    Call<LoginResponse> login(@Header("Accept-Language") String accept_lang,
                              @Field("grant_type") String grant_type,
                              @Field("client_id") int client_id,
                              @Field("client_secret") String client_secret,
                              @Field("username") String username,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("api/v1/checkmobile")
    Call<CheckMobile> checkmobile(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,@Field("mobile") String mobile,@Field("email") String email);

    @FormUrlEncoded
    @POST("api/v1/customer")
    Call<CustomerResponse> registerCustomer(@Header("Accept-Language") String accept_lang,@Field("client_id") int client_id,
                                            @Field("client_secret") String client_secret,
                                            @Field("grant_type") String grant_type,
                                            @Field("name") String name,
                                            @Field("email") String email,
                                            @Field("password") String password,
                                            @Field("color_id") int color,
                                            @Field("brand_id") int brand,
                                            @Field("car_type") String car_type,
                                            @Field("mobile") String mobile,
                                            @Field("address")String address,
                                            @Field("latitude")String latitude,
                                            @Field("longitude")String longitude);

    @Multipart
    @POST("api/v1/customer/avatar")
    Call<Avatar> uploadImage(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                             @Header("Authorization") String token,
                             @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/v1/customer/favorite")
    Call<AddProviderFavorit> addProviderFav(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                            @Header("Authorization") String token,
                                            @Field("provider_id") int provider_id,
                                            @Field("operation") String operation);

    @FormUrlEncoded
    @POST("oauth/token")
    Call<LoginResponse> refreshToken(@Header("Accept-Language") String accept_lang, @Header("Accept") String accept,
                                      @Field("grant_type") String grant_type,
                                      @Field("client_id") int client_id,
                                      @Field("client_secret") String client_secret,
                                      @Field("refresh_token") String refresh_token );

    @FormUrlEncoded
    @POST("api/v1/nearprovider")
    Call<ProvidersCheckin> prvidersCheckin(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
            @Header("Authorization") String token,
                                           @Field("latitude") double latitude,
                                           @Field("longitude") double longitude);

    @Headers("Content-Type: application/json")
    @POST("api/v1/order")
    Call<OrderObject> POST_ORDER2(@Header("Accept-Language") String accept_lang,
                                  @Header("Accept") String Accept,
                                  @Header("Authorization") String token,
                                  @Header("Content-Type") String Content_Type,
                                  @Body OrderLoader order
    );

    @FormUrlEncoded
    @POST("api/v1/react/recommend")
    Call<LikeItem> likeOrderItem(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                 @Header("Authorization") String token,
                                 @Field("order_item_id") int order_item_id,
                                 @Field("operation") String operation);

    @FormUrlEncoded
    @POST("api/v1/saveorderitem")
    Call<SaveItem1> saveOrderItem(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                  @Header("Authorization") String token,
                                  @Field("order_item_id") int order_item_id,
                                  @Field("affiliate_customer_id") int affiliate_customer_id,
                                  @Field("product_id") int product_id,
                                  @Field("operation") String operation);
    @FormUrlEncoded
    @POST("api/v1/recommend")
    Call<SaveItem> saveRecomendedItem(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                      @Header("Authorization") String token,
                                      @Field("order_item_id") int orderId,
                                      @Field("type_recommend") String type_recommend);

    @FormUrlEncoded
    @POST("api/v1/customer/favorite")
    Call<SaveItem> saveOrderItemToFav(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                      @Header("Authorization") String token,
                                      @Field("order_id") int orderId,
                                      @Field("provider_id") int provider_id,
                                      @Field("operation") String operation);


    @Multipart
    @POST("api/v1/addpic")
    Call<SaveImage> addImgToUserPurchase(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                         @Header("Authorization") String token,
                                         @Part MultipartBody.Part image,
                                         @Part MultipartBody.Part video,
                                         @Part("order_id") RequestBody order_id,
                                         @Part("order_item_id") RequestBody order_item_id);

    @FormUrlEncoded
    @POST("api/v1/readmessage")
    Call<SaveItem> MessageRead(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                               @Header("Authorization") String token,
                               @Field("message_id") int message_id);

    @FormUrlEncoded
    @POST("api/v1/follow")
    Call<SaveItem> FollowCustomer(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                  @Header("Authorization") String token,
                                  @Field("following_id") int message_id,
                                  @Field("operation") String operation);


    @FormUrlEncoded
    @POST("api/v1/send/contactus")
    Call<SaveItem> ContactUs(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                             @Header("Authorization") String token,
                             @Field("content") String content);

    @FormUrlEncoded
    @POST("api/v1/devicetoken")
    Call<DeviceToken> deviceToken(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                  @Header("Authorization") String token,
                                  @Field("type") String type,
                                  @Field("device_token") String device_token,
                                  @Field("device_type") String device_type);


    @FormUrlEncoded
    @POST("api/v1/saveorderitem")
    Call<SaveItem> saveOrderItem1(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                  @Header("Authorization") String token,
                                  @Field("order_item_id") int orderItemId,
                                  @Field("operation") String save);

    @FormUrlEncoded
    @POST("api/v1/providers/type/{id}")
    Call<HomeObject> getProvidersHome(@Header("Accept-Language") String accept_lang,
                                      @Header("Accept") String accept,
                                      @Header("Authorization") String token,
                                      @Path("id") int page,
                                      @Query("page") int nextpage,
                                      @Field("latitude") double latitude,
                                      @Field("longitude") double longitude);

    @FormUrlEncoded
    @POST("api/v1/customer/iamhere")
    Call<SaveItem> iamHere(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                             @Header("Authorization") String token,
                             @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST("api/v1/customer/arrival")
    Call<SaveItem> addArrival(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                              @Header("Authorization") String token,
                              @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST("api/v1/resetpassword")
    Call<SaveItem> resetPassword(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                 @Field("client_id") int client_id,
                                 @Field("client_secret") String client_secret,
                                 @Field("email") String email );

    @FormUrlEncoded
    @POST("api/v1/validatesms")
    Call<SaveItem> validateCode(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                @Header("Authorization") String token,
                                @Field("code") int code);

    @Headers("Content-Type: application/json")
    @POST("api/v1/resendsms")
    Call<SaveItem> resendCode(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                              @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/v1/sendmessage")
    Call<SaveItem> sendMessage(@Header("Accept") String accept,
                               @Header("Authorization") String token,
                               @Field("provider_id") int provider_id,
                               @Field("message") String message);

    @Multipart
    @POST("api/v1/addpic")
    Call<SaveImage> add3mediaToPurchase(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                        @Header("Authorization") String token,
                                        @Part List<MultipartBody.Part> customer_files,

                                        @Part("order_id") RequestBody order_id,
                                        @Part("order_item_id") RequestBody order_item_id);

    @FormUrlEncoded
    @POST("api/v1/addtime")
    Call<SaveItem> sendPrepareTime(@Header("Accept") String accept,
                                   @Header("Authorization") String token,
                                   @Field("order_id") int order_id,
                                   @Field("customer_track_time") int customer_track_time);
    @FormUrlEncoded
    @POST("api/v1/removepic")
    Call<SaveItem> deleteUserMedia(@Header("Accept") String accept,
                                   @Header("Authorization") String token,@Field("order_id") int order_id ,@Field("order_item_id") int order_item_id,@Field("customer_file") String customer_file);

    @FormUrlEncoded
    @POST("api/v1/order_cancel")
    Call<SaveItem> cancelOrder(@Header("Accept") String accept,
                               @Header("Authorization") String token,
                               @Field("order_id") int order_id);
    ///////////////////////// GET ////////////////////////

    @GET("api/v1/carbrands")
    Call<CarBrandList> carbrands(@Header("Accept-Language") String accept_lang);

    @GET("api/v1/carcolors")
    Call<CarColorList> carcolors(@Header("Accept-Language") String accept_lang);

    @GET("api/v1/customer")
    Call<PProfileObject> getCustomer(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                     @Header("Authorization") String token);

    @GET("api/v1/types")
    Call<ProvidersCategoriesList> getProvidersCategories(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                                         @Header("Authorization") String token);

    @GET("api/v1/productcategory/provider/{id}")
    Call<ProductsCategoriesList> getProductsCategoriesList(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                                           @Header("Authorization") String token, @Path("id") int p_id);

    @GET("api/v1/provider/delivery/{id}")
    Call<DeliveryStatusList> getDeliveryStatus(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                               @Header("Authorization") String token, @Path("id") int p_id);

    @GET("api/v1/products/{p_id}/category/{c_id}")
    Call<RestaurantObject> getProducts(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                       @Header("Authorization") String token, @Path("p_id") int p_id, @Path("c_id") int c_id, @Query("page") int nextpage);

    @GET("api/v1/products/{p_id}/branch/{b_id}/category/{c_id}")
    Call<RestaurantObject> getBrancheProducts(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                              @Header("Authorization") String token, @Path("p_id") int p_id, @Path("b_id") int b_id, @Path("c_id") int c_id, @Query("page") int nextpage);

    @GET("api/v1/customer/favorite")
    Call<FavoritesList> getProvidersFavorits(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                             @Header("Authorization") String token);

    @GET("api/v1/type/{id}")
    Call<HomeObject> getProvidersHomeId(@Header("Accept-Language") String accept_lang,@Path("id") int id,@Header("Accept") String accept, @Header("Authorization") String token);

    @GET("api/v1/myfriendrecommend")
    Call<MyFriendRecommendedItems> getFriendRecommended(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                                        @Header("Authorization") String token, @Query("page") int nextpage);

    @GET("api/v1/saveditems")
    Call<MySavedItems> getSavedItems(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                     @Header("Authorization") String token, @Query("page") int nextpage);

    @GET
    Call<Mypurchase> getUserPurchase(@Header("Accept-Language") String accept_lang,@Url String url,@Header("Accept") String accept,
                                     @Header("Authorization") String token);

    @GET
    Call<MyOrders> getUserOrders(@Header("Accept-Language") String accept_lang,@Url String url,@Header("Accept") String accept,
                                 @Header("Authorization") String token);

    @GET
    Call<MyOrders> getUserLatestOrders(@Header("Accept-Language") String accept_lang,@Url String url,@Header("Accept") String accept,
                                 @Header("Authorization") String token);

    @GET
    Call<Messages> getUserMessages(@Header("Accept-Language") String accept_lang,@Url String url,@Header("Accept") String accept,
                                   @Header("Authorization") String token);

    @GET
    Call<Users> getAllUsers(@Header("Accept-Language") String accept_lang,@Url String url,@Header("Accept") String accept,
                            @Header("Authorization") String token);

    @GET
    Call<UserRecomend> getUsersRecommends(@Header("Accept-Language") String accept_lang,@Url String url,@Header("Accept") String accept,
                                          @Header("Authorization") String token);

    @GET("api/v1/wallet")
    Call<UserWallet> getUserWallets(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                    @Header("Authorization") String token);

    @GET("api/v1/contactinfo")
    Call<ContactInfoObject> getContactInfo(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                           @Header("Authorization") String token);
    @GET("api/v1/faq_question")
    Call<QAList> getQuestionsAndAnswers(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept, @Header("Authorization") String token);
    @GET()
    Call<NotificationObject> getNotification(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,@Url String url, @Header("Authorization") String token);
    /*@GET("api/v1/saveditems")
    Call<SavedList> getSaved(@Header("Authorization") String token);*/

    @GET("distancematrix/json") // origins/destinations:  LatLng as string
    Call<ResultDistanceMatrix> getDistance(@Query("origins") String origins, @Query("destinations") String destinations);

    @GET("api/v1/provider/messages/{id}")
    Call<ChatObject> getMessagesChat(@Path("id") int id,
                                     @Header("Accept") String accept,
                                     @Header("Authorization") String token,
                                     @Query("page") int nextpage);

    @GET("api/v1/isoff/{id}")
    Call<ProviderOnOff> provider_onoff(@Header("Accept") String accept,
                                       @Header("Authorization") String token,
                                       @Path("id") int p_id);

    ///////////////////////// PUT /////////////////////////
    @FormUrlEncoded
    @PUT("api/v1/customer")
    Call<UpdateCustomer> updateCustomer(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                        @Header("Authorization") String token,
                                        @Field("name") String name,
                                        @Field("email") String email,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude,
                                        @Field("color_id") int color,
                                        @Field("address") String address,
                                        @Field("brand_id") int brand,
                                        @Field("car_type") String car_type,
                                         @Field("privac") int Privac);

    @FormUrlEncoded
    @PUT("api/v1/customer")
    Call<UpdateCustomer> updateLocation(@Header("Accept-Language") String accept_lang,@Header("Accept") String accept,
                                        @Header("Authorization") String token,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude,
                                        @Field("address") String address
                                        );
    //////////////////// DEL ///////////////////////////////////
    @DELETE("api/v1/provider/message/{id}")
    Call<SaveItem> deleteMessage(@Header("Accept-Language") String accept_lang,@Path("id") int url,@Header("Accept") String accept,
                                 @Header("Authorization") String token);
    @DELETE("api/v1/notification/{id}")
    Call<SaveItem> deleteNotification(@Header("Accept-Language") String accept_lang,@Path("id") int url,@Header("Accept") String accept,
                                      @Header("Authorization") String token);



}
