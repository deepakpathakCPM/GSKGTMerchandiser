package com.cpm.gskgtmerchandiser.upload.Retrofit_method;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.widget.Toast;

import com.cpm.gskgtmerchandiser.constant.CommonString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by deepakp on 5/22/2017.
 */

public class UploadImageWithRetrofit {

    boolean isvalid;
    RequestBody body1;
    private Retrofit adapter;
    Context context;
    public static int uploadedFiles = 0;
    int status = 0;

    public UploadImageWithRetrofit(Context context) {
        this.context = context;
    }


    int UploadImagesAndData() {
        try {
            uploadedFiles = 0;
            File f = new File(CommonString.FILE_PATH);
            File file[] = f.listFiles();
            // int totalfiles = f.listFiles().length;
            if (file.length > 0) {

                // data.value = 0;
                for (int i = 0; i < file.length; i++) {

                    //data.value = data.value + factor;
                    //data.name = "Uploading Images";
                    //data.name = uploadedFiles+" images uploaded out of "+totalfiles;

                    if (new File(CommonString.FILE_PATH + file[i].getName()).exists()) {
                        String folderName = "";
                        if (file[i].getName().contains("NonWorking") || file[i].getName().contains("StoreImage")) {
                            folderName = "StoreImages";
                        } else if (file[i].getName().contains("competitorImage")) {
                            folderName = "CompetitionImages";
                        } else if (file[i].getName().contains("PaidVisibility_")) {
                            folderName = "MTPaidVisibility";
                        } else if (file[i].getName().contains("Posmimage")) {
                            folderName = "PosmImages";
                        } else if (file[i].getName().contains("Promotion_")) {
                            folderName = "MTPromotion";
                        } else if (file[i].getName().contains("SOS_")) {
                            folderName = "MTShareOfShelf";
                        } else if (file[i].getName().contains("SignageImage")) {
                            folderName = "SignageImages";
                        } else if (file[i].getName().contains("WindowsImage") || file[i].getName().contains("WINDOWImage")) {
                            folderName = "WindowImages";
                        } else if (file[i].getName().contains("GeoTag")) {
                            folderName = "GEOStoreImages";
                        } else {
                            folderName = "BulkImages";
                        }
                        // UploadImage2(file[i].getName(), folderName, CommonString.FILE_PATH);
                    }

                }
            }

            return uploadedFiles;

        } catch (Exception e) {
            e.printStackTrace();
            return uploadedFiles;
        }

    }


/*
    public String UploadImage2(final String filename, String foldername, String folderPath) {
        try {
            status = 0;
            File originalFile = new File(folderPath + filename);
            final File finalFile = saveBitmapToFileSmaller(originalFile);
            isvalid = false;
            RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), finalFile);
            body1 = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("file", finalFile.getName(), photo)
                    .addFormDataPart("FolderName", foldername)
                    .build();

            adapter = new Retrofit.Builder()
                    .baseUrl(CommonString.URL2)
                    .addConverterFactory(new StringConverterFactory())
                    .build();
            PostApi api = adapter.create(PostApi.class);

            Call<String> call = api.getUploadImage(body1);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response) {
                    if (response.isSuccess()) {
                        finalFile.delete();
                        uploadedFiles++;
                        isvalid = true;
                        status = 1;
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    isvalid = true;
                    Toast.makeText(context, finalFile.getName() + " not uploaded", Toast.LENGTH_SHORT).show();
                    if (t instanceof SocketTimeoutException) {
                        status = 2;
                    } else if (t instanceof IOException) {
                        status = 3;
                    } else {
                        status = -1;
                    }

                }
            });

            while (isvalid == false) {
                synchronized (this) {
                    this.wait(25);
                }
            }
            if (isvalid) {
                synchronized (this) {
                    this.notify();
                }
            }
            if (status == 1) {
                return CommonString.KEY_SUCCESS;
            } else if (status == 2) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } else if (status == 3) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } else {
                return CommonString.KEY_FAILURE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonString.KEY_FAILURE;
        }
    }
*/

    public String downloadDataUniversal(final String jsonString, int type) {
        try {
            status = 0;
            isvalid = false;
            final String[] data_global = {""};
            RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);

            adapter = new Retrofit.Builder()
                    .baseUrl(CommonString.URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi api = adapter.create(PostApi.class);
            Call<ResponseBody> call = null;
            if (type == CommonString.LOGIN_SERVICE) {
                call = api.getLogindetail(jsonData);
            } else if (type == CommonString.DOWNLOAD_ALL_SERVICE) {
                call = api.getDownloadAll(jsonData);
            }

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody responseBody = response.body();
                    String data = null;
                    if (responseBody != null && response.isSuccessful()) {
                        try {
                            data = response.body().string();
                            data = data.substring(1, data.length() - 1).replace("\\", "");
                            data_global[0] = data;
                            isvalid = true;
                            status = 1;
                        } catch (Exception e) {
                            e.printStackTrace();
                            isvalid = true;
                            status = -2;
                        }
                    } else {
                        isvalid = true;
                        status = -1;
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    isvalid = true;
                    if (t instanceof SocketTimeoutException) {
                        status = 2;
                    } else if (t instanceof IOException) {
                        status = 3;
                    } else {
                        status = -1;
                    }

                }
            });

            while (isvalid == false) {
                synchronized (this) {
                    this.wait(25);
                }
            }
            if (isvalid) {
                synchronized (this) {
                    this.notify();
                }
            }
            if (status == 1) {
                return data_global[0];
            } else if (status == 2) {
                return CommonString.MESSAGE_NO_RESPONSE_SERVER;
            } else if (status == 3) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } else if (status == -2) {
                return CommonString.MESSAGE_INVALID_JSON;
            }
            else {
                return CommonString.KEY_FAILURE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonString.KEY_FAILURE;
        }
    }

    public File saveBitmapToFile(File file) {
        File file2 = file;
        try {

                    /*// The new size we want to scale to
        final int REQUIRED_SIZE = 1024;
 o.inJustDecodeBounds = true;
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }*/

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            // File file3 = new File(CommonString.FILE_PATH_compressed + file.getName());
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return file2;
        }
    }


    public File saveBitmapToFileSmaller(File file) {
        File file2 = file;
        try {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(file2);
            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(file2);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth / 800, inHeight / 500);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, 800, 500);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
            // save image
            try {
                FileOutputStream out = new FileOutputStream(file2);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            } catch (Exception e) {
                Log.e("Image", e.toString(), e);
            }
        } catch (IOException e) {
            Log.e("Image", e.toString(), e);
            return file2;
        }
        return file;
    }


/*
    class StringConverterFactory extends Converter.Factory {
        private StringConverterFactory() {
        }

        @Override
        public Converter<String> get(Type type) {
            Class<?> cls = (Class<?>) type;
            if (String.class.isAssignableFrom(cls)) {
                return new StringConverter();
            }
            return null;
        }
    }

    private static class StringConverter implements Converter<String> {
        private static final MediaType PLAIN_TEXT = MediaType.parse("text/plain; charset=UTF-8");

        @Override
        public String fromBody(ResponseBody body) throws IOException {
            return new String(body.bytes());
        }

        @Override
        public RequestBody toBody(String value) {
            return RequestBody.create(PLAIN_TEXT, convertToBytes(value));
        }

        private static byte[] convertToBytes(String string) {
            try {
                return string.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
*/


}
