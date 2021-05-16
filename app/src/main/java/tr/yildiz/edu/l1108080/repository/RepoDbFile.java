package tr.yildiz.edu.l1108080.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tr.yildiz.edu.l1108080.repository.models.BaseModel;
import tr.yildiz.edu.l1108080.util.MethodResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * Created by y3 on 30/04/2021 09:25.
 */
public class RepoDbFile {

    public static File getDataFile(Activity context, String repo) {
        SharedPreferences pref = context.getPreferences(Context.MODE_PRIVATE);
        String dbFile = pref.getString(repo, "");
        if (!TextUtils.isEmpty(dbFile)) {
            File dir = new File(context.getFilesDir(), "data");
            if (dir.exists()) {
                File file = new File(dir, dbFile);
                if (file.exists())
                    return file;
            }
        }
        String filename = UUID.randomUUID().toString().replace("-", "");
        MethodResponse<File> resp = doWrite(context, filename, "");
        if (!resp.success()) {
            return null;
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(repo, filename);
        editor.commit();
        return resp.data();
    }

    public static <T extends BaseModel> List<T> read(Activity context, String repo, BaseModel klass) {
        File dataFile = getDataFile(context, repo);

        try {
            FileInputStream inputStream = new FileInputStream(dataFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String content = new String(buffer, "UTF-8");
            Type typeOfT = TypeToken.getParameterized(List.class, klass.getClass()).getType();
            return new Gson().fromJson(content, typeOfT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void write(Activity context, String repo, List<T> data) {
        File dataFile = getDataFile(context, repo);
        String json = new Gson().toJson(data);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(dataFile);
            outputStream.write(json.getBytes("UTF-8"));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MethodResponse<File> doWrite(Context context, String fname, String content) {
        File dir = new File(context.getFilesDir(), "data");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File file = new File(dir, fname);
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
            return new MethodResponse<File>().success(true).data(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new MethodResponse<File>(e);
        }
    }
}
