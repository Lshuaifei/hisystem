package com.xgs.hisystem.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/11
 */
public class QRcodeUtil {

    private static final int width=300;

    private static final int height=300;

    public static String crateQRCode(String content) throws IOException {

        String resultImage = "";

        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // 设置图片的边距
            hints.put(EncodeHintType.MARGIN, 2);

            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);

                resultImage = new String("data:image/png;base64," + Base64Utils.encodeToString(os.toByteArray()));
                return resultImage;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
        return null;
    }
}
