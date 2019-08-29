package com.example.a12306.utils;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;


public class QRCodeUtils {
    private Bitmap qrcode_bitmap;//生成的二维码
    private int codecolor = Color.BLACK;
    private int bagcolor = Color.WHITE;
    private Bitmap blackBitmap;//代替黑色色块的图片
    private String margin;


    public void generateQrcodeAndDisplay(String content, ImageView iv_qrcode){



       qrcode_bitmap = QRCodeUtils.createQRCodeBitmap(content, 650, 650,
               "UTF-8", margin, codecolor, bagcolor, 0.2F, blackBitmap);
       iv_qrcode.setImageBitmap(qrcode_bitmap);
   }

    public static Bitmap createQRCodeBitmap(String content, int width, int height, String character_set,
                                            String margin, int color_black, int color_white, float logoPercent,
                                            Bitmap bitmap_black) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }

            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            if (bitmap_black != null) {
                //从当前位图按一定的比例创建一个新的位图
                bitmap_black = Bitmap.createScaledBitmap(bitmap_black, width, height, false);
            }
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {// 黑色色块像素设置
                        if (bitmap_black != null) {//图片不为null，则将黑色色块换为新位图的像素。
                            pixels[y * width + x] = bitmap_black.getPixel(x, y);
                        } else {
                            pixels[y * width + x] = color_black;
                        }
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }

            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }



}
