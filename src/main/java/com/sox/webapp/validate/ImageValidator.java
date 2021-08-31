package com.sox.webapp.validate;

import com.sox.webapp.exception.FileFailOperationException;
import com.sox.webapp.exception.ImageFormatNotSupportException;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.ImageEntity;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.util.Constant;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class ImageValidator extends Validator{

    private final MultipartFile file;
    private final String animeId;

    public ImageValidator(BindingResult result,MultipartFile file,String animeId) {
        super(result);
        this.file = file;
        this.animeId = animeId;
    }

    @Override
    public ResponseDataSet<Object> getResult() {
        ResponseDataSet<Object> responseDataSet = ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",null);
        String[] fileToken = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        if(fileToken.length != 2){
            throw new ImageFormatNotSupportException("文件格式不支持");
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage == null){
                throw new ImageFormatNotSupportException("文件必须是图片");
            }

            float ratio = (float) bufferedImage.getHeight()/bufferedImage.getWidth();
            if(ratio < 1.2 || ratio > 1.6){
                throw new ImageFormatNotSupportException("图片高与宽的比例必须在1.7和2.3之间");
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new FileFailOperationException("Oops 服务器发生错误");
        }

        responseDataSet.setData(new ImageEntity(file,fileToken[fileToken.length - 1],animeId));
        return responseDataSet;
    }
}
