package com.example.office_web.web;


import com.example.office_web.utils.DateUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/api/fileUpload")
public class FileUploadController extends BaseController{


    @Value("${image.src}")
    private String imgSrc;

    /**
     * 上传图片
     * @param myfiles
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/doUpload")
     public  String uploadFile(MultipartFile[] myfiles, String  moudle, HttpServletRequest request,
                               HttpServletResponse response){
          if(myfiles == null || myfiles.length == 0){
              return ajaxFail("上传的文件数据为空");
          }

          String dateTemp = DateUtils.getNowDateStr();
          Map<String, String> errorMap = new HashMap<>();
          List<String> urlList = new ArrayList<>();
         for(int i = 0; i < myfiles.length; i++){
             MultipartFile multipartFile = myfiles[i];
             String originalFilename = multipartFile.getOriginalFilename();
             String subffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
             String newFileName = UUID.randomUUID().toString().replace("-", "")+"."+subffix;
             try {
                 String url = imgSrc+moudle+"/"+dateTemp+"/"+newFileName;
                 FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(imgSrc+moudle+"/"+dateTemp+"/", newFileName));
                 urlList.add(url);
             } catch (Exception e) {
                 logger.error("上传图片异常", e);
                 errorMap.put(originalFilename, e.getMessage());
             }
         }



         if(errorMap.size() == 0){
             return ajaxSucess(urlList);
         }else {
             return ajaxFail(errorMap);
         }


     }



}
