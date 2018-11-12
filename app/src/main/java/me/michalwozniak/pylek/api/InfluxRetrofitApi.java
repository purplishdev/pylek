package me.michalwozniak.pylek.api;

import io.reactivex.Single;
import me.michalwozniak.pylek.model.InfluxResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InfluxRetrofitApi {

    @GET("{endpoint}/query")
    Single<InfluxResponse> query(@Path("endpoint") int endpoint, @Query("q") String query,
                                 @Query("db") String db, @Query("epoch") String epoch);
}
