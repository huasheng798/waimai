package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * @Author:luosheng
 * @Date:2023/3/9 21:18
 * @Description:一个通用的controller ，现在暂时使用文件上传下载
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 土图片下载，其实这个功能 就是我们用户要上传一张图片，然后我们上传的图片还需要再回显到上传的那个位置，所以这里要两个controller一个用于下载
     * 一个用于回显图片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是也给临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        //获取文件名(这个是原始文件名)
        String originalFilename = file.getOriginalFilename();
        //截取后缀.jpg
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //为了防止文件名重复造成文件覆盖 使用UUID从新生成文件名
        String  fileName= UUID.randomUUID().toString()+substring;
        //创建一个目录对象
        File file1 = new File(basePath);
        if (!file1.exists()){
         //这就表示他这个目录不存在，创建一个
         file1.mkdir();
        }
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 图片回显
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
