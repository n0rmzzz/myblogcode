package com.tinywebgears.simplecharts.shared;

public class StringHelper
{
    private final static String SEPARATOR = ",";

    public static String unquote(String text)
    {
        while (text.startsWith("\'") || text.startsWith("\""))
            text = text.substring(1);
        while (text.endsWith("\'") || text.endsWith("\""))
            text = text.substring(0, text.length() - 1);
        return text;
    }

    public static String[] separateCodes(String codeList)
    {
        String[] codes = codeList.split(SEPARATOR);
        String[] result = new String[codes.length];
        Integer index = 0;
        for (String code : codes)
            result[index++] = code.trim();
        return result;
    }

    public static String escapeHtml(String html)
    {
        if (html == null)
            return null;
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
