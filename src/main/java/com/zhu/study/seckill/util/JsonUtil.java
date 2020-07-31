package com.zhu.study.seckill.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 往页面中输出json
 * @author zhusl 
 * @date 2020年7月24日  上午11:46:50
 *
 */
public class JsonUtil {
    private JsonUtil() {}
    public static void writeJsonToPage(HttpServletResponse response, String msg) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(msg);
        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}