package com.wrotecode.powertool;

import com.wrotecode.powertool.exception.CoreException;
import com.wrotecode.powertool.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 使用自定义命令在typora中上传图片到本地图床。
 *
 * <p>本地图床使用iis搭建，上传过程其实就是复制过程。</p>
 *
 * @author wrote-code
 */
public class TyporaImageUpload {
    private static final Logger log = LoggerFactory.getLogger(TyporaImageUpload.class);

    public static void main(String[] args) throws Exception {
        // https://support.typora.io/Upload-Image/#use-current-filename--filepath-in-custom-commands
        if (args.length != 2) {
            throw new CoreException("参数错误,应该输入目录和文件名");
        }
        String directory = args[0];
        String filename = args[1];
        log.info("源文件:{} -> {}", filename, directory);
        // 通过系统属性传入，typora上传的文件会复制到这里，按日期分组。
        String fileDir = System.getProperty("fileDir");
        log.info("本地图床{}", fileDir);
        if (!FileUtils.directoryExists(fileDir)) {
            throw new CoreException("请检查目标目录是否存在");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDate = format.format(System.currentTimeMillis());
        log.info("当前日期:{}", currentDate);
        String targetPath = fileDir + File.separator + currentDate;
        if (!FileUtils.directoryExists(targetPath)) {
            FileUtils.mkdir(targetPath);
        }
        log.info("目标目录:{}", targetPath);
        FileUtils.copyFile(directory + File.separator + filename,
                targetPath + File.separator + filename);
        log.info("复制完成");
    }
}
