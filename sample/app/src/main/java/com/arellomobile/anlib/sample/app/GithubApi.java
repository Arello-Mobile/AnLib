package com.arellomobile.anlib.sample.app;

import java.util.List;

import com.arellomobile.anlib.sample.data.Repository;
import com.arellomobile.anlib.sample.data.User;
import com.arellomobile.anlib.sample.data.gson.SearchResult;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by senneco on 22.05.2014
 */
public interface GithubApi {

    @GET("/user")
    User signIn(@Header("Authorization") String token);

    @GET("/search/repositories?sort=stars&order=desc")
    SearchResult search(@Query("q") String query);

    @GET("/users/{login}/repos")
    List<Repository> getUserRepos(@Path("login") String login);
}
