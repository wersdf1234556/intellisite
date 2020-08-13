package org.tonzoc.model.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;


public class FileUtils {

	/**
	 * 文件上传到服务器的目录名
	 */
	private final static String FILEUPLOADPATH = "upload";
    /**
     * 文件上传到硬盘的目录
     */
    private final static String FILEUPLOADDISKPATH = "D:/upload";


    public static String upload(MultipartFile picFile, HttpServletRequest request) throws IOException {

        if (!picFile.isEmpty()) {
            String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + ".jpg";
            File uploadFile = new File(FILEUPLOADDISKPATH);

            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            File dest = new File(FILEUPLOADDISKPATH + "\\" + fileName);

            try {
                picFile.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return uploadFile.getPath() + "\\" + fileName;
        }
        return "";
    }


    public static boolean delete(String fileName, HttpServletRequest request) throws IOException {
        boolean bj = false;
        if (fileName != null && !"".equals(fileName)) {
            File file = new File(fileName);
            if (fileName != null && !"".equals(fileName)) {
                if (file.exists()) {
                    bj = file.delete();
                    System.out.println("deletebj" + bj);
                }
            }
        }
        return bj;
    }

    /**
     * 文件上传到服务器
     * @param picFile
     * @return
     */
    public static String uploadToServer(MultipartFile picFile, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("");
        //路径后面拼接一个/
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        //判断目标目录是否存在,如果不存在则创建对应目录
        File uploadPath = new File(path+FILEUPLOADPATH);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();	//创建目录
        }
        //保存文件
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        //获取文件的扩展名
        String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + ".jpg";
        File dest = new File(path+fileName);
        try {
            picFile.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path + "\\" + fileName;
    }

    /**
     * 删除服务器文件
     * @param fileName
     */
    public static boolean deleteFileFromServer(String fileName, HttpServletRequest request) {
        boolean bj = false;
        if (fileName != null && !"".equals(fileName)) {
            String uploadPath = request.getSession().getServletContext().getRealPath(FILEUPLOADPATH);
            if (!fileName.endsWith("/")) {
                fileName = fileName + "/";
            }
            File file = new File(fileName);
            if (file.exists()) {
                bj = file.delete();
            }
        }
        return bj;
    }
}
