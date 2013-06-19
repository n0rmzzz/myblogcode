package com.tinywebgears.simplecharts.shared;

public class FieldVerifier
{
    public static boolean isValidSecurityCodeList(String stockCodeList)
    {
        if (stockCodeList == null)
            return false;
        String[] stockCodes = StringHelper.separateCodes(stockCodeList);
        return areValidSecurityCodes(stockCodes);
    }

    public static boolean areValidSecurityCodes(String... stockCodes)
    {
        if (stockCodes == null)
            return false;
        for (String stockCode : stockCodes)
            if (!isValidSecurityCode(stockCode))
                return false;
        return true;
    }

    public static boolean isValidSecurityCode(String stockCode)
    {
        if (stockCode == null)
            return false;
        return stockCode.length() >= 3;
    }
}
