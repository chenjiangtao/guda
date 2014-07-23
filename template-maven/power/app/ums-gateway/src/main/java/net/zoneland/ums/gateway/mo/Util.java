package net.zoneland.ums.gateway.mo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public static int DAYTIME = 0x15180;

    public Util() {
    }

    public static int getCurrentTime(String format) {
        SimpleDateFormat current = new SimpleDateFormat(format);
        int currentTime = Integer.parseInt(current.format(new Date()));
        return currentTime;
    }

    public static String addMinute(String format, int minute) {
        SimpleDateFormat current = new SimpleDateFormat(format);
        Date utilDate = new Date();
        utilDate.setTime(utilDate.getTime() + (minute * 1000 * 60));
        String currentTime = current.format(utilDate);
        return currentTime;
    }

    public static String getCurrentTimeStr(String format) {
        SimpleDateFormat current = new SimpleDateFormat(format);
        String currentTime = current.format(new Date());
        return currentTime;
    }

    public static int getUTCTime1() {
        Calendar calendar = Calendar.getInstance();
        return (int) (calendar.getTimeInMillis() / 1000L);
    }

    public static int getUTCTime() {
        long UTCTime = System.currentTimeMillis() / 1000L;
        return (int) UTCTime;
    }

    public static synchronized byte[] getByteString(String msg) {
        byte b1[] = new byte[21];
        System.arraycopy(msg.getBytes(), 0, b1, 0, msg.getBytes().length);
        b1[msg.getBytes().length] = 0;
        return b1;
    }

    public static synchronized byte[] getMaxByteString(String msg) {
        byte b1[] = new byte[612];
        System.arraycopy(msg.getBytes(), 0, b1, 0, msg.getBytes().length);
        b1[msg.getBytes().length] = 0;
        return b1;
    }

    public static byte[] getByte(String msg, int data_code) {
        try {
            if (data_code == 15) {
                return msg.getBytes("GBK");
            }
            if (data_code == 0) {
                return msg.getBytes("8859_1");
            }
            if (data_code == 21 || data_code == 4) {
                return getByte(msg);
            } else {
                return msg.getBytes("UnicodeBig");
            }
        } catch (Exception e) {
            return msg.getBytes();
        }
    }

    public static String DupStr(String s, int count) {
        if (count <= 0) {
            return null;
        }
        StringBuffer buf = new StringBuffer(s.length() * 2 * count);
        for (int i = 0; i < count; i++) {
            buf.append(s);
        }
        return buf.toString();
    }

    public static String LPad(String msg, int len) {
        return LPad(msg, len, " ");
    }

    public static String LPad(String msg, int len, String c) {
        return DupStr(c, (len - msg.length()) / c.length()) + msg;
    }

    public static String RPad(String msg, int len) {
        return RPad(msg, len, " ");

    }

    public static String RPad(String msg, int len, String c) {
        return msg + DupStr(c, (len - msg.length()) / c.length());
    }

    public static String getFixedString(String msg, int len) {

        if (msg == null) {
            return DupStr(" ", len);
        }

        StringBuffer sb = new StringBuffer(msg);

        int bytes = msg.getBytes().length;
        // if (bytes>=len)
        //    return msg;
        //        sb.append( DupStr(" ", len - bytes));

        for (int i = msg.getBytes().length + 1; i <= len; i++) {
            sb.append(' ');
        }
        //        Res.log(Res.INFO,String.format("msg=%d(%s) len=%d result=%d",msg.length(),msg,len,sb.toString().getBytes().length));
        return sb.toString();
    }

    public static int getTime(int time) {
        return (time / 10000) * 3600 + (time / 100 - (time / 10000) * 100) * 60
               + (time - (time / 100) * 100);
    }

    public static String replaceNULL(String msg) {
        if (msg == null || msg.equals("null")) {
            return "";
        } else {
            return msg;
        }
    }

    public static String strReplace(String sAll, String sOld, String sNew) {
        int iT = 0;
        String sF = null;
        String sH = null;
        if (sNew.indexOf(sOld) != -1) {
            return sAll;
        }
        if (sAll == null || sOld == null || sNew == null) {
            return sAll;
        }
        for (iT = sAll.indexOf(sOld); iT != -1; iT = sAll.indexOf(sOld)) {
            sF = sAll.substring(0, iT);
            sH = sAll.substring(iT + sOld.length());
            sAll = sF + sNew + sH;
        }

        return sAll;
    }

    /*
     * ���˽����ַ�{MT}
     * @param sMt String       ת��ǰ�ַ�
     * @return boolean         ת�����ַ�
     * @˵��
     */
    public static String convertHalfToFull(String sMt) {
        String sReturn = sMt;
        //        if (sReturn == null) {
        //            return sReturn;
        //        }
        //        try {
        //            sReturn = strReplace(sReturn, "'", "��");
        //            sReturn = sReturn.replace(',', '��');
        //            sReturn = sReturn.replace('"', '��');
        //        } catch (Exception ex) {
        //            return sMt;
        //        }
        return sReturn;
    }

    /*
     * ���˽����ַ�{MT}
     * @param sMo String       ת��ǰ�ַ�
     * @return boolean         ת�����ַ�
     * @˵��
     */
    public static String convertFullToHalf(String sMt) {
        String sReturn = sMt;
        //        if (sReturn == null) {
        //            sReturn = "";
        //        }
        //        try {
        //            sReturn = strReplace(sReturn, "��", "'");
        //            sReturn = sReturn.replace('��', ',');
        //            sReturn = sReturn.replace('��', '.');
        //            sReturn = sReturn.replace('��', ';');
        //            sReturn = sReturn.replace('��', '!');
        //            sReturn = sReturn.replace('��', '?');
        //            sReturn = sReturn.replace('��', ':');
        //            sReturn = sReturn.replace('��', '��');
        //            sReturn = sReturn.replace('��', '��');
        //            sReturn = sReturn.replace('��', '��');
        //        } catch (Exception ex) {
        //        }
        return sReturn;
    }

    /*
    public static synchronized String fromDatabase(String change) {
    String result = "";
    try {
    byte temp[] = change.getBytes("GBK");
    return new String(temp, "iso-8859-1");
    } catch (Exception exception) {
    return result;
    }
    }

    public static  String toDatabase(String change) {
    String result = "";
    try {
    result = new String(change.getBytes("GBK"), "iso-8859-1");
    } catch (Exception exception) {
    }
    return result;
    }
     */

    public static boolean isValidMobileNumber(String msg) {
        if (msg == null) {
            return false;
        }
        return msg.matches("1[3458][0-9][0-9]{8}");
    }

    /*
        //�ж��Ƿ���ͨ�ֻ�
        public static boolean isUnicom(String recvID) {
            if (recvID == null) {
                return false;
            }

            if (recvID.matches("13[0-2][0-9]{8}")) {
                return true;
            } else if (recvID.matches("15[56][0-9]{8}")) {
                return true;
            } else if (recvID.matches("186[0-9]{8}")) {
                return true;
            } else if (recvID.matches("145[0-9]{8}")) {
                return true;
            } else {
                return false;
            }
        }
     *
     */
    /*
        //�ж��Ƿ�����ֻ�
        public static boolean isSMGP(String recvID) {
            if (recvID == null) {
                return false;
            }

            if (recvID.matches("1[35]3[0-9]{8}") || recvID.matches("189[0-9]{8}") || recvID.matches("153[0-9]{8}")) {
                return true;
            } else {
                return false;
            }
        }
    */
    public static boolean isNumeric(String msg) {
        boolean result = true;
        try {
            if (msg.length() != 11) {
                result = false;
            } else {
                Long.parseLong(msg);
            }
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public static String getASCII(byte[] shortMsg, int smLen) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < smLen; i++) {
            byte b = shortMsg[i];
            String ascii = Integer.toHexString(b);
            if (b < 16 && b >= 0) {
                ascii = "0" + ascii;
            }
            if (ascii.length() > 2) {
                ascii = ascii.substring(6, 8);
            }
            result.append(ascii);
        }

        return result.toString().toUpperCase();
    }

    public static byte[] getByte(String msg) {
        int length = msg.getBytes().length;
        byte temp[] = new byte[length];
        int i = 0;
        int j = 0;
        for (i = 0; i < length; i++) {
            int b1 = Integer.parseInt(msg.substring(i, i + 1), 16);
            b1 <<= 4;
            if (++i < length) {
                int b2 = Integer.parseInt(msg.substring(i, i + 1), 16);
                b1 += b2;
            }
            temp[j] = (byte) b1;
            j++;
        }

        byte result[] = new byte[j];
        for (int f = 0; f < j; f++) {
            result[f] = temp[f];
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(isNumeric("0124235345643"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
