package com.center.medical.common.utils;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author: 路飞船长
 * @date: 2025/6/19 14:21
 * @description:
 */
public class XmlUtils {

    /**
     * 将 XML 字符串解析为 Java 对象
     *
     * @param xmlData XML 字符串（如 <Request>...</Request>）
     * @param clazz   目标 Java 类型（必须有无参构造函数）
     * @param <T>     泛型类型
     * @param isEscape 是否处理 HTML 转义字符
     * @return 解析后的对象
     */
    public static <T> T parseXmlToObject(String xmlData, Class<T> clazz, Boolean isEscape) {
        try {
//            System.out.println("开始解析XML，数据长度: " + (xmlData != null ? xmlData.length() : 0));

            String unescapedXml = xmlData;
            if (isEscape) {
                System.out.println("需要转义处理");
                try {
                    Document doc = XmlUtil.readXML(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));
                    Element resultElement = doc.getDocumentElement();
                    String innerXml = resultElement.getTextContent();
                    System.out.println("提取的内部XML长度: " + innerXml.length());
                    unescapedXml = EscapeUtil.unescape(innerXml);
                    System.out.println("转义后XML长度: " + unescapedXml.length());
                } catch (Exception e) {
                    System.err.println("转义处理失败: " + e.getMessage());
                    throw e;
                }
            }

//            System.out.println("最终解析的XML: " + unescapedXml.substring(0, Math.min(200, unescapedXml.length())));

            Document requestDoc = XmlUtil.readXML(new ByteArrayInputStream(unescapedXml.getBytes(StandardCharsets.UTF_8)));
            Element rootElement = requestDoc.getDocumentElement();
//            System.out.println("根元素: " + rootElement.getTagName());

            T obj = clazz.getDeclaredConstructor().newInstance();

            for (int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
                org.w3c.dom.Node node = rootElement.getChildNodes().item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element child = (Element) node;
                    String nodeName = child.getTagName();
                    String value = child.getTextContent();
//                    System.out.println("处理字段: " + nodeName + " = " + value);

                    try {
                        java.lang.reflect.Field field = clazz.getDeclaredField(nodeName);
                        field.setAccessible(true);
                        field.set(obj, value);
                    } catch (NoSuchFieldException e) {
//                        System.err.println("字段不存在: " + nodeName);
                        // 可以选择忽略不存在的字段
                    }
                }
            }

            return obj;
        } catch (Exception e) {
            System.err.println("XML转换失败详情:");
            e.printStackTrace();
            throw new RuntimeException("将 XML 转换为对象失败: " + e.getMessage(), e);
        }
    }
}
