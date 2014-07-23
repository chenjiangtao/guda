/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.autoconfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

import com.foodoon.mvc.runtime.core.config.AppConfigLocation;
import com.foodoon.mvc.runtime.core.config.Configration;
import com.foodoon.mvc.runtime.core.xml.XmlResovle;

/**
 * 生成app.config文件
 * @author gag
 * @version $Id: GenerateConfigProperties.java, v 0.1 2012-4-26 下午5:54:23 gag Exp $
 */
public class CreateAppConfigProperties {

    protected final Log         logger              = LogFactory.getLog(getClass());

    private XmlResovle          xmlResovle          = new XmlResovle();

    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

    public Properties createaAppConfig(String srcFile) throws ParserConfigurationException,
                                                      SAXException, IOException,
                                                      TransformerException {
        Properties newProps = new Properties();
        xmlResovle.resolveXmlToProperties(srcFile, newProps);
        Properties oldProps = new Properties();
        loadDefaultProperties(oldProps);
        String runMode = String.valueOf(oldProps.get(Configration.APP_RUN_MODE));
        if (Configration.APP_RUN_MODE_PROD.equalsIgnoreCase(runMode)) {
            mergeProperties(newProps, oldProps);
            writeProperties(oldProps);
            return oldProps;
        } else {
            writeProperties(newProps);
            return newProps;
        }
    }

    public void writeProperties(Properties props) {
        FileSystemResource fsr = new FileSystemResource(AppConfigLocation.getLocation());
        try {
            store(props,
                new BufferedWriter(new OutputStreamWriter(fsr.getOutputStream(), "UTF-8")),
                "update date:" + Calendar.getInstance().getTime(), true);
        } catch (IOException e) {
            logger.error("创建app-config.xml错误.", e);
        }

    }

    public void store(Properties props, BufferedWriter bw, String comments, boolean escUnicode)
                                                                                               throws IOException {
        if (comments != null) {
            writeComments(bw, comments);
        }
        bw.write("#" + new Date().toString());
        bw.newLine();
        Set<Object> set = props.keySet();
        List<Object> temp = new ArrayList<Object>(set);
        List<String> list = new ArrayList<String>(temp.size());
        for (Object obj : temp) {
            list.add(String.valueOf(obj));
        }
        Collections.sort(list);
        for (String key : list) {

            String val = (String) props.get(key);
            key = saveConvert(key, true, escUnicode);

            val = saveConvert(val, false, escUnicode);
            bw.write(key + "=" + val);
            bw.newLine();
        }
        bw.flush();
    }

    private String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch (aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\n':
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r':
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f':
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                case '=': // Fall through
                    // case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\');
                    outBuffer.append(aChar);
                    break;
                default:
                    if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    private static void writeComments(BufferedWriter bw, String comments) throws IOException {
        bw.write("#");
        int len = comments.length();
        int current = 0;
        int last = 0;
        char[] uu = new char[6];
        uu[0] = '\\';
        uu[1] = 'u';
        while (current < len) {
            char c = comments.charAt(current);
            if (c > '\u00ff' || c == '\n' || c == '\r') {
                if (last != current)
                    bw.write(comments.substring(last, current));
                if (c > '\u00ff') {
                    uu[2] = toHex((c >> 12) & 0xf);
                    uu[3] = toHex((c >> 8) & 0xf);
                    uu[4] = toHex((c >> 4) & 0xf);
                    uu[5] = toHex(c & 0xf);
                    bw.write(new String(uu));
                } else {
                    bw.newLine();
                    if (c == '\r' && current != len - 1 && comments.charAt(current + 1) == '\n') {
                        current++;
                    }
                    if (current == len - 1
                        || (comments.charAt(current + 1) != '#' && comments.charAt(current + 1) != '!'))
                        bw.write("#");
                }
                last = current + 1;
            }
            current++;
        }
        if (last != current)
            bw.write(comments.substring(last, current));
        bw.newLine();
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'     };

    /**
     * 将来源properties复制到目标properties
     * 对于目标已经有的值不会覆盖
     * @param src 来源
     * @param dest 目标
     */
    public void mergeProperties(Properties src, Properties dest) {
        if (src == null || dest == null) {
            return;
        }
        Iterator<Entry<Object, Object>> it = src.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            Object key = entry.getKey();
            if (dest.get(key) == null) {
                dest.put(key, entry.getValue());
            }

        }
    }

    public void loadDefaultProperties(Properties props) {
        try {
            File file = ResourceUtils.getFile(AppConfigLocation.getLocation());
            if (file.exists()) {
                propertiesPersister.load(props, new InputStreamReader(new FileInputStream(file)));
            } else {
                logger.warn("在路径:[" + AppConfigLocation.getLocation()
                            + "]中无法找到app.config。创建新的app.config!");
            }
        } catch (Exception e) {

        }
    }
}
