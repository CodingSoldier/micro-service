package com.github.codingsoldier.starter.web.util;

import com.github.codingsoldier.common.util.Md5Util;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author chenpq05
 * @since 2022/2/16 11:23
 */
@SuppressWarnings("squid:S2696")
@Component
public class FileLocalUtil {

    public static final List<String> IMAGE_TYPE_LIST = ImmutableList.of("jpeg", "png", "jfif", "jpg", "tif", "bmp");

    /**
     * 图片路径目录
     */
    private static String fileUploadDir;
    /**
     * 图片预览
     */
    private static String imagePreviewUrl;

    /**
     * 上传图片，返回图片路径
     *
     * @param file file
     * @return 图片路径
     * @throws IOException ex
     */
    public static String upload(MultipartFile file) throws IOException {
        // 如果文件夹不存在则创建
        File fileDir = new File(fileUploadDir);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        byte[] bytes = file.getBytes();
        String md5Name = Md5Util.md5(bytes);
        String fileType = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        String fileName = md5Name + "." + fileType;
        String filePath = getImageAbsolutePath(fileName);
        File dest = new File(filePath);
        file.transferTo(dest);
        return fileName;
    }

    /**
     * @param imagePath imagePath
     * @return 得到图片在磁盘的绝对路径
     */
    public static String getImageAbsolutePath(String imagePath) {
        return fileUploadDir + File.separator + imagePath;
    }

    /**
     * @param imagePath imagePath
     * @return 图片预览地址
     */
    public static String getImagePreviewUrl(String imagePath) {
        if (StringUtils.isBlank(imagePath)) {
            return "";
        }
        return imagePreviewUrl + "/" + imagePath;
    }

    /**
     * @return 图片预览地址前缀
     */
    public static String getImagePreviewPrefix() {
        return imagePreviewUrl + "/";
    }

    /**
     * 创建目录
     *
     * @param dirPath 文件夹路径
     * @return fileDir.mkdirs()
     */
    public static boolean createDir(String dirPath) {
        File fileDir = new File(dirPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            return fileDir.mkdirs();
        }
        return false;
    }

    @Value("${file.upload.dir:null}")
    public void setFileUploadDir(String fileUploadDir) {
        FileLocalUtil.fileUploadDir = fileUploadDir;
    }

    @Value("${file.image-preview-url:null}")
    public void setImagePreviewUrl(String imagePreviewUrl) {
        FileLocalUtil.imagePreviewUrl = imagePreviewUrl;
    }

}
