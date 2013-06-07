package com.test.type;

import java.io.Serializable;

public class UUID implements Serializable
{
    private static final long serialVersionUID = -1156841361193249489L;

    private long mostSigBits = 0L;
    private long leastSigBits = 0L;

    public UUID()
    {
    }

    public UUID(long mostSigBits, long leastSigBits)
    {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    private static void toBytes(byte[] bytes, final int startindex, final long val)
    {
        for (int i = 0; i < 8; i++)
            bytes[(7 - i) + startindex] = (byte)(val >> (i * 8));
    }

    public byte[] toBytes()
    {
        byte[] bytes = new byte[16];
        toBytes(bytes, 0, mostSigBits);
        toBytes(bytes, 8, leastSigBits);
        return bytes;
    }

    private static long fromBytes(byte[] bytes, final int startindex)
    {
        long l = 0;
        for (int i = 0; i < 8; i++)
        {
            l <<= 8;
            l |= (long)bytes[i + startindex] & 0xFFL;
        }
        return l;
    }

    public static UUID fromBytes(byte[] bytes)
    {
        long msb = fromBytes(bytes, 0);
        long lsb = fromBytes(bytes, 8);
        return new UUID(msb, lsb);
    }

    public String toString()
    {
        return (digits(mostSigBits >> 32, 8) +
            digits(mostSigBits >> 16, 4) +
            digits(mostSigBits, 4) +
            digits(leastSigBits >> 48, 4) +
            digits(leastSigBits, 12));
    }

    private String digits(long val, int digits)
    {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1).toUpperCase();
    }

    public int hashCode()
    {
        return (int)((mostSigBits >> 32)
            ^ mostSigBits
            ^ (leastSigBits >> 32)
            ^ leastSigBits);
    }


    public boolean equals(Object obj)
    {
        if (!(obj instanceof UUID))
            return false;
        UUID id = (UUID)obj;
        return (mostSigBits == id.mostSigBits
            && leastSigBits == id.leastSigBits);
    }
}
