package com.wrotecode.powertool.utils;

import com.wrotecode.powertool.exception.CoreRuntimeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类。
 *
 * @author wrote-code
 */
public class FileUtils {
    /**
     * 进行文件流操作时的缓存大小。
     */
    private static final int BYTE_BUFFER = 4096;

    private FileUtils() {
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean directoryExists(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static boolean directoryExists(String parent, String name) {
        return directoryExists(parent + File.separator + name);
    }

    /**
     * 复制文件。
     *
     * <p>需要自己判断源文件和目标文件是否存在。</p>
     *
     * @param sourcePath 源文件。
     * @param targetPath 目标文件。
     */
    public static void copyFile(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);
        try (FileInputStream is = new FileInputStream(sourceFile); FileOutputStream os = new FileOutputStream(
                targetFile)) {
            byte[] bytes = new byte[BYTE_BUFFER];
            int length;
            while ((length = is.read(bytes)) != -1) {
                os.write(bytes, 0, length);
                os.flush();
            }
        } catch (IOException e) {
            throw new CoreRuntimeException("文件复制失败", e);
        }
    }

    /**
     * 创建新目录，创建成功返回true。
     *
     * <p>需要自行处理目录合法性。</p>
     *
     * @param dir 文件目录。
     *
     * @return 创建成功返回true。
     */
    public static boolean mkdir(String dir) {
        File file = new File(dir);
        return file.mkdir();
    }

    /**
     * 创建子目录，创建成功返回true。
     *
     * <p>需要自行判断父目录是否存在。</p>
     *
     * @param parent 父目录。
     * @param name 子目录目录名。
     *
     * @return 创建成功返回true。
     */
    public static boolean mkdir(String parent, String name) {
        return mkdir(parent + File.separator + name);
    }

    /**
     * 获取指定路径的文件名。
     *
     * @param path 路径。
     *
     * @return 文件名。
     */
    public static String getFileName(String path) {
        return new File(path).getName();
    }

}
