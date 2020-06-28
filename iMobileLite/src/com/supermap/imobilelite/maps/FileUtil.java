package com.supermap.imobilelite.maps;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/10/13.
 */

public class FileUtil {
    public boolean upLoadConfigFile(Context context){
        AssetManager mgr = context.getAssets();
        try {
            InputStream icuDat = mgr.open("icu/icudt50l.dat");
            File icuPath = new File(context.getFileStreamPath("icu").getAbsoluteFile()+"/icudt50l.dat");
            copyFile(icuDat, icuPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    public boolean copyFile(InputStream src, File des){

        if(!des.getParentFile().exists()){
            des.getParentFile().mkdirs();
        }
        if(des.exists()){
            des.delete();
        }

        try{
            InputStream fis = src;
            FileOutputStream fos = new FileOutputStream(des);
            //1kb
            byte[] bytes = new byte[1024];
            int readlength = -1;
            while((readlength = fis.read(bytes))>0){
                fos.write(bytes, 0, readlength);
            }
            fos.flush();
            fos.close();
            fis.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }
    public static boolean checkFile(Context context){

        File icu = new File(context.getFileStreamPath("icu").getAbsoluteFile()+"/icudt50l.dat");
        if(!icu.exists()){
            return false;
        }
        return true;
    }
}
