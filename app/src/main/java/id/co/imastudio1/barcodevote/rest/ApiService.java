package id.co.imastudio1.barcodevote.rest;

import id.co.imastudio1.barcodevote.SheetModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by idn on 8/12/2017.
 */

public interface ApiService {
    @GET("macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1t8Iu50efjRKlYUm_R2qIAWgVG-fvlwdgLp7C-R2UaIQ&sheet=anggota")
    Call<SheetModel> ambilData();

    //    @FormUrlEncoded
    @POST("macros/s/AKfycbyW1xZC0y2oTQqHvnP57nMViNTkSYd3dvoI_JvxtzNrqGqKSjOm/exec")
    Call<Void> updatedataReg(@Query("id") String id,
                                     @Query("id_barcode") String id_barcode,
                                     @Query("registrasi") String registrasi);

    @POST("macros/s/AKfycbyW1xZC0y2oTQqHvnP57nMViNTkSYd3dvoI_JvxtzNrqGqKSjOm/exec")
    Call<Void> updatedataCert(@Query("id") String id,
                                      @Query("id_barcode") String id_barcode,
                                      @Query("sertifikat") String sertifikat);
//

    @POST("macros/s/AKfycbyW1xZC0y2oTQqHvnP57nMViNTkSYd3dvoI_JvxtzNrqGqKSjOm/exec")
    Call<Void> updatedataKit(@Query("id") String id,
                                     @Query("id_barcode") String id_barcode,
                                     @Query("seminarkit") String seminarkit);

//    @FormUrlEncoded
//    @POST("macros/s/AKfycbxnOQ61yI-vq5FyJi0ZP9XbQM6wFIRGrd2Lbzhy4WAj3-gdz8s/exec")
//    Call<ResponseBody> updatedatacert(@Field("id") String id,
//                                      @Field("id_barcode") String id_barcode,
//                                      @Field("registrasi") String registrasi,
//                                      @Field("sertifikat") String sertifikat,
//                                      @Field("seminarkit") String seminarkit);
//
//
//    @FormUrlEncoded
//    @POST("macros/s/AKfycbxnOQ61yI-vq5FyJi0ZP9XbQM6wFIRGrd2Lbzhy4WAj3-gdz8s/exec")
//    Call<ResponseBody> updatedatakit(@Field("id") String id,
//                                     @Field("id_barcode") String id_barcode,
//                                     @Field("registrasi") String registrasi,
//                                     @Field("sertifikat") String sertifikat,
//                                     @Field("seminarkit") String seminarkit);


}
