package com.wrotecode.powertool;

import com.wrotecode.powertool.exception.CoreException;
import com.wrotecode.powertool.exception.CoreRuntimeException;
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

    public static void main(String[] args) throws CoreException {
        try {
            // https://support.typora.io/Upload-Image/#custom
            log.info("==========");
            if (args.length == 0) {
                return;
            }
            log.info("当前需要上传{}个文件", args.length);
            StringBuilder argsBuilder = new StringBuilder();
            for (String arg : args) {
                argsBuilder.append(arg).append(System.lineSeparator());
            }
            log.info("参数:\r\n{}", argsBuilder);
            // 通过系统属性传入，typora上传的文件会复制到这里，按日期分组。
            String fileDir = System.getProperty("fileDir");
            // 本地图床根目录，比如localhost:8080/images/，文件上传后就会再根目录后加上文件目录
            String server = System.getProperty("server");
            log.info("本地图床{}", fileDir);
            if (!FileUtils.directoryExists(fileDir)) {
                log.info("请检查图床目录是否存在");
                throw new CoreRuntimeException("请检查图床目录是否存在");
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String currentDate = format.format(System.currentTimeMillis());
            log.info("当前日期:{}", currentDate);
            String targetDir = fileDir + File.separator + currentDate;
            if (!FileUtils.directoryExists(targetDir)) {
                FileUtils.mkdir(targetDir);
            }
            log.info("目标目录:{}", targetDir);

            // 图片访问路径，比如http://localhost:8080/20230926/typora.png
            StringBuilder urlBuilder = new StringBuilder();
            for (String file : args) {
                log.info("当前文件:{}", file);
                String filename = FileUtils.getFileName(file);
                String targetFile = targetDir + File.separator + filename;
                if (!FileUtils.fileExists(file)) {
                    log.warn("当前文件不存在，已忽略，文件路径{}", file);
                    continue;
                }
                FileUtils.copyFile(file, targetFile);
                if (FileUtils.fileExists(targetFile)) {
                    urlBuilder.append(server).append("/").append(currentDate)
                            .append("/").append(filename)
                            .append(System.lineSeparator());
                }
                log.info("文件复制完成，新路径:{}", file);
            }
            log.info("上传完成:\r\n{}", urlBuilder);
            // 根据typora规范，图床返回的url通过标准输出展示
            System.out.println(urlBuilder);
        } catch (Exception e) {
            // 将报错写入日志文件
            log.error("上传失败", e);
            // 异常写入日志文件后再抛出，会展示在typora中。
            throw new CoreException("上传失败", e);
        }
    }
}
