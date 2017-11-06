package id.co.imastudio1.barcodevote.rest;

import id.co.imastudio1.barcodevote.SheetModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by idn on 8/12/2017.
 */

public interface ApiService {
    @GET("macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1t8Iu50efjRKlYUm_R2qIAWgVG-fvlwdgLp7C-R2UaIQ&sheet=anggota")
    Call<SheetModel> ambilData();

    @FormUrlEncoded
    @POST("macros/s/AKfycbyW1xZC0y2oTQqHvnP57nMViNTkSYd3dvoI_JvxtzNrqGqKSjOm/exec")
    Call<ResponseBody> updatedata(@Field("id") String id,
                                       @Field("id_barcode") String id_barcode,
                                       @Field("nama") String nama,
                                       @Field("registrasi") String registrasi);

    @FormUrlEncoded
    @POST("macros/s/AKfycbxnOQ61yI-vq5FyJi0ZP9XbQM6wFIRGrd2Lbzhy4WAj3-gdz8s/exec")
    Call<ResponseBody> updatedatacert(@Field("id") String id,
                                  @Field("id_barcode") String id_barcode,
                                  @Field("nama") String nama,
                                  @Field("registrasi") String registrasi);

    @FormUrlEncoded
    @POST("macros/s/AKfycbxnOQ61yI-vq5FyJi0ZP9XbQM6wFIRGrd2Lbzhy4WAj3-gdz8s/exec")
    Call<ResponseBody> updatedatakit(@Field("id") String id,
                                      @Field("id_barcode") String id_barcode,
                                      @Field("nama") String nama,
                                      @Field("registrasi") String registrasi);


}
