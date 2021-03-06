package jackteng.util;

import java.util.*;



/**
 * <p>Description: This class contains various methods for manipulating
 *                 bits.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Institute of Information Science, Academia Sinica</p>
 * @author Jei-Wen Teng
 * @version 1.0
 */

//字符串的编码方式
public class Bits {

	private Bits() {
	}

	/**
	 * 将一个bytes串存入BitSet数据结构中
	 * Returns a bitset of the given byte array. The byte-ordering of
	 * bytes must be big-endian which means the most significant bit is in
	 * element 0.
	 *
	 * @param bytes the target byte array.
	 * @return the bitset of the given byte array.
	 */
	public static BitSet toBitSet(byte[] bytes) {
		BitSet bits = new BitSet();

		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[i / 8] & (128 >> (i % 8))) > 0) {
				bits.set(i);
			}
		}

		return bits;
	}

	/**
	 * 将一个在BitSet中存储bit串存入byte串中
	 * Returns a byte array of at least length 1. The byte-ordering of the result
	 * is big-endian which means the most significant bit is in element 0. The
	 * bit at index 0 of the bit set is assumed to be the most significant bit.
	 *
	 * @param bits the target bitset.
	 * @return the byte array of the bitset.
	 */
	public static byte[] toByteArray(BitSet bits) {
		int bitLen = bits.length();
		int byteLen = (((bitLen % 8) == 0) ? (bitLen / 8) : (bitLen / 8 + 1));
		byte[] bytes = new byte[byteLen];

		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				bytes[i / 8] |= (128 >> (i % 8));
			}
		}

		return bytes;
	}

	/**
	 * 返回将BitSet中的内容转换为一个由0，1构成的String
	 * Returns a conventional string representation of the given bitset which
	 * means that we use a string of 1 and 0 to represent the bitset.
	 *
	 * @param bits the target bitset.
	 * @return the conventional string representation of the given bitset.
	 */
	public static String toStr(BitSet bits) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				sb.append("1");
			} else {
				sb.append("0");
			}
		}

		return sb.toString();
	}

	/**
	 * 获取两个bitset的比较位。如果两个bitset的长度不同并且更长bitset的前缀是较短的这个
	 * bitset,那么返回紧接在此前缀后面的第一位。如果两个bitset相同，则返回0。（这里的位
	 * 是从第0位开始算起的）
	 * Gets the comparison bit of the two bitsets. If the lengths of the two
	 * bitsets are different and the prefix of the longer bitset is the shorter
	 * bitset, then returns the first 1 bit after that prefix (i.e. the shorter
	 * bitset is padded with 0 bits). If the two bitsets are equal, then returns
	 * 0.
	 *
	 * @param bit1 the first bitset.
	 * @param bit2 the second bitset.
	 * @return the comparison bit of the two bitsets, 0 if the two bitsets are
	 *         equal.
	 */
	public static int getComparisonBit(BitSet bit1, BitSet bit2) {
		int bit1Len = bit1.length();
		int bit2Len = bit2.length();
		int maxLen = Math.max(bit1Len, bit2Len);
		int i = 0;
		boolean found = false;

		for (i = 0; (i < maxLen) && !found; i++) {
			if ((i < bit1Len) && (i < bit2Len)) {
				if (bit1.get(i) != bit2.get(i)) {
					found = true;
				}
			} else {
				if (bit1Len > bit2Len) {
					if (bit1.get(i)) {
						found = true;
					}
				} else {
					if (bit2.get(i)) {
						found = true;
					}
				}
			}
		}
		if (!found) {
			i = 0;
		}

		return i;
	}

	public static void main(String[] args) throws Exception {
		String str = "筿福";
		BitSet bits = Bits.toBitSet(str.getBytes("big5"));//getBytes将str用big5编码后存入到一个bytes串中
		int bitIndex = 359;
		System.out.println((bitIndex + 1) + "th bit is " + bits.get(bitIndex));//get返回的是一个布尔值
		String newStr = new String(Bits.toByteArray(bits));
		System.out.println("Bits of " + str + " is " + Bits.toStr(bits));
		System.out.println("String of " + Bits.toStr(bits) + " is " + newStr);
		System.out.println("length of the new string is " + newStr.length());
		String str1 = "筿福";
		BitSet bits1 = Bits.toBitSet(str1.getBytes("big5"));
		System.out.println("Bits of " + str1 + " is " + Bits.toStr(bits1));
		System.out.println("CB of " + str + " and " + str1 + " is " +
											 Bits.getComparisonBit(bits, bits1));
		String s = "中华人民共和国";
		BitSet b1 = Bits.toBitSet(str.getBytes("utf8"));
		BitSet b2 = Bits.toBitSet(s.getBytes("big5"));
		System.out.println(Bits.toStr(b1)+" "+Bits.toStr(b2));
	}
}