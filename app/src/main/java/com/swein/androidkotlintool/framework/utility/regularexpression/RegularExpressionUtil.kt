package com.swein.androidkotlintool.framework.utility.regularexpression

import java.util.regex.Pattern

/**
 * 元字符
 * .    匹配除换行符以外的任意字符
 * \w   匹配字母或数字或下划线或汉字
 * \s   匹配任意的空白符
 * \d   匹配数字
 * \b   匹配单词的开始或结束
 * ^    匹配字符串的开始
 * $    匹配字符串的结束
 *
 * 重复限定符
 * *        重复零次或更多次
 * +        重复一次或更多次
 * ?        重复零次或一次
 * {n}      重复n次
 * {n,}     重复n次或更多次
 * {n,m}    重复n到m次
 *
 * 分组
 * 正则表达式中用小括号()来做分组
 *
 * 转义
 * 如果要匹配的字符串中本身就包含小括号，需要用
 * ^(\(xx\))$
 *
 * 条件或
 * |: 正则用符号 | 来表示或，也叫做分支条件，当满足正则里的分支条件的任何一种条件时，都会当成是匹配成功
 *
 * 区间
 * 限定0到9 [0-9]
 * 限定A-Z [A-Z]
 * 限定具体数字 [165]

 */
class RegularExpressionUtil {
    companion object {

        private const val PHONE_NUMBER_LENGTH_PATTERN = "^1\\d{10}$"

        private const val KOREAN_PHONE_NUMBER_LENGTH_PATTERN =
            "^(010|070|02|031|032|051|053|062|042|052|044|033|043|041|063|061|054|055|064)-?\\d{4}-?\\d{4}$"

        private const val EMAIL_PATTERN = "^.+@.+.com$"

        fun isMatchPhoneNumberLength(phoneNumber: String): Boolean {
            val pattern = Pattern.compile(PHONE_NUMBER_LENGTH_PATTERN)
            val matcher = pattern.matcher(phoneNumber)
            return matcher.find()
        }

        fun isMatchKoreanPhoneNumberLength(phoneNumber: String): Boolean {
            val pattern = Pattern.compile(KOREAN_PHONE_NUMBER_LENGTH_PATTERN)
            val matcher = pattern.matcher(phoneNumber)
            return matcher.find()
        }

        fun isMatchEmail(email: String): Boolean {
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.find()
        }

    }
}