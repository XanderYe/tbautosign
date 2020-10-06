package cn.xanderye.tbautosign.util;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author XanderYe
 * @description:
 * @date 2020/10/6 20:54
 */
public class WebpUtil {
    /**
     * 普通图片转webp
     * @param inputStream
     * @return byte[]
     * @author XanderYe
     * @date 2020/10/6
     */
    public static void imageToWebp(InputStream inputStream, String outPath) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        ImageWriter writer = null;
        try {
            writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(WebPWriteParam.MODE_DEFAULT);
            FileImageOutputStream fos = new FileImageOutputStream(new File(outPath));
            writer.setOutput(fos);
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            inputStream.close();
        }
    }

    /**
     * webp转普通图片
     * @param file
     * @return byte[]
     * @author XanderYe
     * @date 2020/10/6
     */
    public static byte[] webpToImage(File file, String ext) throws IOException {
        ImageReader reader = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (FileImageInputStream fis = new FileImageInputStream(file)) {
            reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);
            reader.setInput(fis);
            BufferedImage image = reader.read(0, readParam);
            ImageIO.write(image, ext, bos);
        } finally {
            if (reader != null) {
                reader.dispose();
            }
        }
        return bos.toByteArray();
    }
}
