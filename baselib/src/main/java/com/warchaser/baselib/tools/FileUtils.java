package com.warchaser.baselib.tools;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public class FileUtils {

    private FileUtils(){

    }

    /**
     * 清空制定文件夹下的文件
     * 不考虑嵌套文件夹
     * */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearDir(File file){

        if(file.isDirectory() && file.exists()){
            File[] files = file.listFiles();

            if(files == null){
                return;
            }

            for(File bean : files){
                bean.delete();
            }
        }
    }

    /**
     * 删除文件夹及其内部所有文件和文件夹
     * @param dir File 文件夹
     * @return 成功与否
     * @noinspection ConstantValue
     * */
    public static boolean deleteDirAndFiles(File dir){
        boolean result = true;

        if(dir == null || !dir.exists() || !dir.isDirectory()){
            result = false;
            return result;
        }

        final File[] files = dir.listFiles();
        if(files == null){
            result = false;
            return result;
        }

        for(File file : files){
            if(file.isFile()){
                if(!file.delete()){
                    result = false;
                }
            } else if(file.isDirectory()){
                if(!deleteDirAndFiles(file)){
                    result = false;
                }
            }
        }

        if(!dir.delete()){
            result = false;
        }

        return result;
    }

    public static boolean isFileExist(String path){
        return new File(path).exists();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteOnExist(String path){
        if(TextUtils.isEmpty(path)){
            return;
        }

        final File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * @noinspection ConstantValue
     * */
    public static boolean deleteFileOnExist(String path){
        boolean result = false;
        if(TextUtils.isEmpty(path)){
            return result;
        }

        final File file = new File(path);
        if(file.exists() && !file.isDirectory()){
            result = file.delete();
        }

        return result;
    }

    /** @noinspection ResultOfMethodCallIgnored*/
    public static File createFileOnNotExist(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }

        final File file = new File(path);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return file;
    }

    public static boolean makeDirsOnNotExist(String path){
        boolean result = false;
        if(TextUtils.isEmpty(path)){
            return result;
        }

        final File file = new File(path);

        if(file.isDirectory() && !file.exists()){
            result = file.mkdirs();
        }

        return result;
    }

    /**
     * 图片转Base64
     * */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data;
        String result = null;
        try{
            is = Files.newInputStream(Paths.get(path));
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
            result = "data:image/png;base64," + result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static File[] getAllFiles(String dirPath){
        final File file = new File(dirPath);
        if(!file.isDirectory()){
            return null;
        }

        return file.listFiles();
    }

    public static String[] getAllFilesStr(String dirPath){
        final File file = new File(dirPath);
        if(!file.isDirectory()){
            return null;
        }

        return file.list();
    }

    public static boolean isDir(String dirPath){
        return new File(dirPath).isDirectory();
    }

    /**
     * 获取安卓自带File文件夹目录路径
     * */
    public static String getExternalFilePath(Context context, String dirName){
        String path;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            final File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if(dir != null){
                path = dir.getAbsolutePath() + File.separator + dirName + File.separator;
            } else {
                path = File.separator + dirName + File.separator;
            }
        } else {
            path = context.getFilesDir().getAbsolutePath() + File.separator + dirName + File.separator;
        }

        final File targetFilePath = new File(path);

        boolean mkdirsSuccess;

        if(!targetFilePath.exists()){
            mkdirsSuccess = targetFilePath.mkdirs();
        } else {
            mkdirsSuccess = true;
        }

        return mkdirsSuccess ? path : "";
    }

    /**
     * 检测系统预留空间
     * */
    public static float availableSizeInMB() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getRootDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSizeLong();
            long blockCount = sf.getBlockCountLong();
            long availCount = sf.getAvailableBlocksLong();
            return (float) availCount * blockSize / 1024 / 1024;
        }

        return 0;
    }

    public static float readDataDir(){
        File root = Environment.getDataDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSizeLong();
        long blockCount = sf.getBlockCountLong();
        long availCount = sf.getAvailableBlocksLong();
        return (float) availCount * blockSize / 1024 / 1024;
    }

}
