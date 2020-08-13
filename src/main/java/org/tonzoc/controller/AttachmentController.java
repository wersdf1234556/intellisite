package org.tonzoc.controller;


import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.controller.params.AttachmentQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.AttachmentModel;
import org.tonzoc.service.IAttachmentService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("attachments")
public class AttachmentController extends BaseController {

    @Autowired
    IAttachmentService attachmentsService;

    @PostMapping("/upFile")
    public void upFile(MultipartFile file) {

        attachmentsService.upFile(file);
    }

    @GetMapping("/downLoadFile")
    public void downLoadFile(HttpServletResponse response, String guid) throws UnsupportedEncodingException {

        attachmentsService.downLoadFile(response, guid);
    }

    @PostMapping("/upFiles")
    public void upFiles(MultipartFile[] file) {

        attachmentsService.upFiles(file);
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, AttachmentQueryParams attachmentsQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Page<AttachmentModel> page = parsePage(pageQueryParams);
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(attachmentsQueryParams);

        List list = attachmentsService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid AttachmentModel attachmentModel) {
        this.attachmentsService.save(attachmentModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid AttachmentModel attachmentModel) {
        this.attachmentsService.update(attachmentModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.attachmentsService.remove(guid);
    }

    @GetMapping(value = "image/{guid}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "guid") String guid) throws IOException {
        return attachmentsService.getImage(guid);
    }

}
