package com.jk.ocr;

import com.jk.config.ConfigReader;
import io.qameta.allure.Attachment;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OCRUtil {

    private static final Tesseract tesseractInstance;
    private static final Logger logger = LoggerFactory.getLogger(OCRUtil.class);

    // Static block to initialize Tesseract once
    static {
        tesseractInstance = new Tesseract();
        tesseractInstance.setDatapath(ConfigReader.get("tessdata.path")); // 🧠 Set traineddata path
        tesseractInstance.setLanguage("eng"); // 🔠 English OCR
        tesseractInstance.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"); // 🔐 CAPTCHA-friendly whitelist
        tesseractInstance.setPageSegMode(7); // 🧠 Single line/word PSM
    }

    /**
     * Main OCR function with expectedText logging and Allure attachments
     */
    public static String getCaptchaTextFromBufferedImage(BufferedImage image, String expectedText) {
        try {
            String result = tesseractInstance.doOCR(image);
            String cleanedText = result != null ? result.replaceAll("[^A-Za-z0-9]", "").trim() : "";

            logger.info("OCR Text: '{}', Expected: '{}'", cleanedText, expectedText);

            attachOCRText("OCR Result", cleanedText);
            attachOCRImage("Captcha Image", image);

            return cleanedText;
        } catch (TesseractException e) {
            logger.error("OCR error: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Overloaded method for optional expectedText
     */
    public static String getCaptchaTextFromBufferedImage(BufferedImage image) {
        return getCaptchaTextFromBufferedImage(image, "N/A");
    }

    /**
     * Allure attachment for plain text OCR result
     */
    @Attachment(value = "{0}", type = "text/plain")
    private static String attachOCRText(String name, String text) {
        return text;
    }

    /**
     * Allure attachment for image
     */
    @Attachment(value = "{0}", type = "image/png")
    private static byte[] attachOCRImage(String name, BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Failed to attach OCR image: {}", e.getMessage());
            return null;
        }
    }
}
