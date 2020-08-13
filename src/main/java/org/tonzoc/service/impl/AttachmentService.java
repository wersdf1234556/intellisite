package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.common.FileHelper;
import org.tonzoc.model.AttachmentModel;
import org.tonzoc.service.IAttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("attachmentService")
public class AttachmentService extends BaseService<AttachmentModel> implements IAttachmentService {

    @Autowired
    private FileHelper fileHelper;



    public void upFile(MultipartFile file) {

        String str = fileHelper.fileUpload(file);

        AttachmentModel attachmentModel = new AttachmentModel();
        attachmentModel.setName(str.split(",")[1]);
        attachmentModel.setUrl(str.split(",")[0]);

        this.save(attachmentModel);
    }

    public void downLoadFile(HttpServletResponse response, String guid) throws UnsupportedEncodingException {

        AttachmentModel attachmentsModel = this.get(guid);

        fileHelper.downLoad(response, attachmentsModel.getName(), attachmentsModel.getUrl());
    }

    public void upFiles(MultipartFile[] file) {

        if (file.length > 0) {
            List<AttachmentModel> list = new ArrayList<>();
            for (MultipartFile f : file) {
                System.out.println(f);
                String str = fileHelper.fileUpload(f);
                AttachmentModel attachmentsModel = new AttachmentModel();
                attachmentsModel.setName(str.split(",")[1]);
                attachmentsModel.setUrl(str.split(",")[0]);

                list.add(attachmentsModel);
            }

            this.saveMany(list);
        }
    }


    public byte[] getImage(String attachmentId) throws IOException {
        AttachmentModel attachmentsModel =get(attachmentId);
        String url =attachmentsModel.getUrl();
        return fileHelper.getImage(url);
    }

    public void PdfPreview (HttpServletResponse response, String guid) throws IOException {

        AttachmentModel attachmentsModel = this.get(guid);

        fileHelper.PdfPreview(response, attachmentsModel.getUrl());
    }

}
