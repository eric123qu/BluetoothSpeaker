package com.haier.ai.bluetoothspeaker.util;

import java.nio.ByteOrder;

/**
 * author: qu
 * date: 15-10-12
 * introduce:
 */
public class BytesUtil {
    public static byte[] shortToByteArray(short s) {

        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }

    public static boolean getCPU() {
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            // System.out.println("is big ending");
            return true;
        } else {
            // System.out.println("is little ending");
            return false;
        }
    }

    public static short getShort(byte[] buf, boolean bBigEnding) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        if (bBigEnding) {
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        } else {
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        }

        return r;
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     *
     * @return
     */
    public static short getShort(byte[] b) {
        /*return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));*/
        return getShort(b, true);
    }

    /**
     * 字节数组转为十六进制格式字符串。
     * @param buf
     * @return
     */
    public static String byte2Hex(byte[] buf)
    {
        if(buf==null)return "";
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("{");
        for (byte b : buf)
        {
            if (b == 0)
            {
                strbuf.append("00");
            } else if (b == -1)
            {
                strbuf.append("FF");
            } else
            {
                String str = Integer.toHexString(b).toUpperCase();
                if (str.length() == 8)
                {
                    str = str.substring(6, 8);
                } else if (str.length() < 2)
                {
                    str = "0" + str;
                }
                strbuf.append(str);
            }
            strbuf.append(" ");
        }
        strbuf.append("}");
        return strbuf.toString();
    }

    /**
     * 字节数组转为十六进制格式字符串。
     * @param buf
     * @param offset
     * @param length
     * @return
     */
    public static String byte2Hex(byte[] buf, int offset, int length)
    {
        byte[] buffer = new byte[length];
        System.arraycopy(buf, offset, buffer, 0, length);
        return byte2Hex(buffer);
    }
}
