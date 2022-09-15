package com.maquk.foodhelperapp;

import com.maquk.foodhelperapp.pojo.Meal;
import com.maquk.foodhelperapp.pojo.Nutrient;
import com.maquk.foodhelperapp.pojo.Product;
import com.maquk.foodhelperapp.pojo.Recipe;
import com.maquk.foodhelperapp.pojo.Water;
import com.maquk.foodhelperapp.pojo.Weight;

import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @Headers({"Accept: application/json"})
    @GET("/weights/")
    Call<List<Weight>> findAllByDateBetween(@Query("fromDate") LocalDate fromDate, @Query("toDate") LocalDate toDate);

    @POST("/meals/")
    Call<Meal> createMeal(@Body Meal meal);

    @GET("/meals/")
    Call<Nutrient> findAllByDate(@Query("date") LocalDate date);
}