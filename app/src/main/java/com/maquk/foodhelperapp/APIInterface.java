package com.maquk.foodhelperapp;

import com.maquk.foodhelperapp.pojo.Product;
import com.maquk.foodhelperapp.pojo.Recipe;
import com.maquk.foodhelperapp.pojo.Water;
import com.maquk.foodhelperapp.pojo.Weight;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface APIInterface {

    @GET("/products/{name}")
    Call<Product> getProduct(@Path("name") String name);

    @GET("/products")
    Call<List<Product>> getProducts();

    @POST("/products/")
    Call<Product> createProduct(@Body Product product);

    @GET("/recipes/{name}")
    Call<Recipe> getRecipe(@Path("name") String name);

    @PUT("/recipes/")
    Call<Recipe> updateRecipe(@Body Recipe recipe);

    @POST("/recipes/")
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET("/recipes/")
    Call<List<Recipe>> getRecipes();

    @POST("/waters/")
    Call<Water> addWater(@Body Water water);

    @POST("/weights/")
    Call<Weight> addWeight(@Body Weight weight);

    /*@GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}