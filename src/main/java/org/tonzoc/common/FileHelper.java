package org.tonzoc.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.configuration.IntelliSiteProperties;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Configuration
public class FileHelper {
    @Autowired
    private IntelliSiteProperties intelliSiteProperties;

    // 上传文件
    public String fileUpload(MultipartFile file){

        if(file.isEmpty()){
            return "";
        }
        String fileName = file.getOriginalFilename();

        String path = intelliSiteProperties.getFilePath();
//        String path ="D:/tool/file";
        File dest = new File(path + "/" + fileName);
        //如果原先有相同文件，则删除
        if (dest.exists()){
            dest.delete();
        }
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件

            return path + "/" + fileName + "," + fileName;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    // 上传多个文件
    public String fileUploads (MultipartFile []file) {
        if (file.length > 0) {

            for (MultipartFile f: file) {
                this.fileUpload(f);
            }

            return "true";
        }

       return "flase";
    }

    // 下载文件
    public String downLoad(HttpServletResponse response, String name, String url) throws UnsupportedEncodingException {

        File file = new File(url);

        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(name,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public byte[] getImage(String url) throws IOException {

        File file = new File(url);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    public void PdfPreview (HttpServletResponse response, String url) throws IOException {

        response.setContentType("application/pdf");
        FileInputStream in = new FileInputStream(new File(url));
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[512];
        while ((in.read(b))!=-1) {
            out.write(b);
        }
        out.flush();
        in.close();
        out.close();
    }

}
