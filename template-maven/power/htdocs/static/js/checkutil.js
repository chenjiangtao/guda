/******************************************************

js 验证

author: Vincent Tong
version: 1.0.4
create date: 2006-05-09
last modify date: 2006-05-12 10:10
********************************************************/

// 字符串是否为空格
function isNull(str) {
    var i;
    for (i = 0; i < str.length; i ++) {
        if (str.charAt(i) != ' ') return false;
    }
    return true;
}

// 取得字符串的字节长度
function strlen(str) {
    var i = 0;
    var len = 0;

    for (i = 0; i < str.length; i ++) {
        if (str.charCodeAt(i) > 255)  len += 3;  else len ++;
    }
    return len;
}

// 是否为空字符串
function isEmpty(str) {
	return strlen(str) == 0;
}

// 去除首尾空格
function strTrim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 是否是数字
function isNumber(str, format) {
    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[0-9" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^(\d)+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是整数
function isInteger(str, format) {

    var regexp = /^([1-9][0-9]{0,9}|[0-9])$/;
    if (!regexp.exec(str)) return false;

	if (format != null && format != "")
	{
		var part = format.split("-");
        var min = Number(part[0]);
        var max = Number(part[1]);
        if (min > max || max > 2147483647) return false;
        if (!(min <= Number(str) && Number(str) <= max)) return false;
	}

    return true;
}

// 是否是浮点数
// format 格式 intlen.fraclen . intlen-整数部分长度(max:38), fraclen-小数部分长度(max:38) 0-最大长度
function isFloat(str, format) {
    var pattern;
    var regexp;

	if (format == null || format == "" || format == "0.0")
	{
	    regexp = /^(([1-9][0-9]{0,38}\.[0-9]{1,38})|(0\.[0-9]{1,38})|([1-9][0-9]{0,38}))$/;
	    if (!regexp.exec(str)) return false;
	}
	else
	{
        var part = format.split(".");
        var intlen = Number(part[0]);
        var fraclen = Number(part[1]);
		if (intlen == 0 || intlen > 38) intlen = 38;
		if (fraclen == 0 || fraclen > 38) fraclen = 38;

        if (intlen == 1) {
            pattern = "^(([0-9]\\.[0-9]{1," + fraclen + "})|([0-9]))$";
		} else {
			intlen = intlen - 1;
            pattern = "^(([1-9][0-9]{0," + intlen + "}\\.[0-9]{1," + fraclen + "})|([1-9][0-9]{0," + intlen + "}))$";
		}
    	regexp = new RegExp(pattern);
		if (!regexp.exec(str)) return false;
	}

	return true;
}


// 是否是字母
function isLetter(str, format) {

    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[a-zA-Z" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[a-zA-Z]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是字母+中文
function isCN(str, format) {

    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[\u4E00-\u9FA5" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[\u4E00-\u9FA5]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是字母+数字
function isLetterNumber(str, format) {

    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[a-zA-Z0-9" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[a-zA-Z0-9]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是字母+中文
function isLetterCN(str, format) {

    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[a-zA-Z\u4E00-\u9FA5" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[a-zA-Z\u4E00-\u9FA5]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是字母 + 数字 + 中文
function isLetterNumberCN(str, format) {
    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[a-zA-Z0-9\u4E00-\u9FA5" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[a-zA-Z0-9\u4E00-\u9FA5]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是字母+中文+双字节字符
function isLetterWcCN(str, format) {

    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[a-zA-Z\u4E00-\u9FA5\uFF00-\uFFFF" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[a-zA-Z\u4E00-\u9FA5\uFF00-\uFFFF]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是字母+数字+中文+双字节字符
function isLetterNumberWcCN(str, format) {
    var pattern;
    var regexp;

    if (format != null && format != "") {
        pattern = "^[a-zA-Z0-9\u4E00-\u9FA5\uFF00-\uFFFF" + format + "]+$";
        regexp = new RegExp(pattern);
    } else {
        regexp = /^[a-zA-Z0-9\u4E00-\u9FA5\uFF00-\uFFFF]+$/;
	}

    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是手机号
function isMobile(str) {
    var regexp =/^[1]([3][0-9]|[4][0-9]|[5][0-9]|[8][0-9])[0-9]{8}$/;
    if (!regexp.test(str)) return false;
    return true;
}

// 是否是联通手机
function isUnicomMobile(str) {
    var regexp = /^1((30)|(31)|(32)|(55)|(56)|(86))(\d){8}$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是186号
function is186(str) {
    var regexp = /^186(\d){8}$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是email
function isEmail(str){
	//^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+
    var regexp = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是联系电话
function isPhone(str){
    var regexp = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是邮编
function isPostcode(str){
    var regexp = /^[1-9]\d{5}$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是页面URL
function isPageurl(str){
    var regexp = /^[a-zA-Z]+:\/\/(\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\s*)?$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是日期
function isDate(str) {
    var	pattern =  "^((([0-9]{2}([02468][048])|([13579][26])))(-)(02)(-)(([0][1-9])|([1-2][0-9])))" + // 润2月完整日期
		           "|((([0-9]{2}([02468][1235679])|([13579][01345789])))(-)(02)(-)(([0][1-9])|([1][0-9])|([2][0-8])))" + // 非润2月完整日期
		           "|(([0-9]{4})(-)(01|03|05|07|08|10|12)(-)(([0][1-9])|([1-2][0-9])|30|31))" + // 大月完整日期
		           "|(([0-9]{4})(-)(04|06|09|11)(-)(([0][1-9])|([1-2][0-9])|30))$"; // 小月完整日期
    regexp = new RegExp(pattern);
    if (!regexp.exec(str)) return false;
    return true;
}

function isDate_yyyyMMdd(str) {
    var	pattern =  "^((([0-9]{2}([02468][048])|([13579][26])))(02)(([0][1-9])|([1-2][0-9])))" + // 润2月完整日期
		           "|((([0-9]{2}([02468][1235679])|([13579][01345789])))(02)(([0][1-9])|([1][0-9])|([2][0-8])))" + // 非润2月完整日期
		           "|(([0-9]{4})(01|03|05|07|08|10|12)(([0][1-9])|([1-2][0-9])|30|31))" + // 大月完整日期
		           "|(([0-9]{4})(04|06|09|11)(([0][1-9])|([1-2][0-9])|30))$"; // 小月完整日期
    regexp = new RegExp(pattern);
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否是身份证号
function idCardUpdate(str) {
    var idCard18;
    var regIDCard15 = /^(\d){15}$/;
    if (regIDCard15.test(str)) {
        var nTemp = 0;
        var ArrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        var ArrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        str = str.substr(0,6) + '1' + '9' + str.substr(6,str.length-6);
        for (var i=0;i<str.length;i ++) {
            nTemp += parseInt(str.substr(i,1)) * ArrInt[i];
        }
        str += ArrCh[nTemp % 11];
        idCard18 = str;
    } else {
        idCard18 = "#";
    }
    return idCard18;
}

function isIdCard(str) {
    var iSum = 0;
    var info = "";
    var sId;
    var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"***",71:"台湾",81:"香港",82:"澳门",91:"国外"};
    //如果输入的为15位数字,则先转换为18位身份证号
    if(str.length == 15)
        sId = idCardUpdate(str);
    else
        sId = str;

    if(!/^\d{17}(\d|x)$/i.test(sId)) {
        return false;
    }
    sId = sId.replace(/x$/i,"a");
    //非法地区
    if (aCity[parseInt(sId.substr(0,2))] == null) {
        return false;
    }
    var sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10,2)) + "-" + Number(sId.substr(12,2));
    var d = new Date(sBirthday.replace(/-/g,"/"))
    //非法生日
    if (sBirthday != (d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate())) {
        return false;
    }
    for (var i = 17;i >= 0; i --) {
        iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i), 11);
    }
    if (iSum%11 != 1) {
        return false;
    }
    return true;
}


function isQQ(str) {
    var regexp = /^[1-9]\d{4,9}$/;
    if (!regexp.exec(str)) return false;
    return true;
}

// 扩展验证
function extCheck(str) {
    var	pattern = str;
    regexp = new RegExp(pattern);
    if (!regexp.exec(str)) return false;
    return true;
}

// 是否IP
function isIP(s) {
	var regexp = /^((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d?\d))$/;
    if (!regexp.exec(s)) return false;
	return true;
}


// 验证函数映射
var checkMap = new Array();
checkMap["number"]  = checkMap["N"] = isNumber;
checkMap["integer"] = checkMap["I"] = isInteger;
checkMap["float"]   = checkMap["F"] = isFloat;
checkMap["letter"]  = checkMap["L"] = isLetter;
checkMap["letternumber"]     = checkMap["LN"]     = isLetterNumber;
checkMap["lettercn"]         = checkMap["LCN"]    = isLetterCN;
checkMap["letternumbercn"]   = checkMap["LNCN"]   = isLetterNumberCN;
checkMap["letterwccn"]       = checkMap["LWCCN"]  = isLetterWcCN;
checkMap["letternumberwccn"] = checkMap["LNWCCN"] = isLetterNumberWcCN;
checkMap["email"] = isEmail;
checkMap["mobile"] = isMobile;
checkMap["postcode"] = isPostcode;
checkMap["phone"] = isPhone;
checkMap["pageurl"] = isPageurl;
checkMap["date"] = isDate;
checkMap["idcard"] = isIdCard;
checkMap["qq"] = isQQ;
checkMap["ext"] = extCheck;

/* 基本验证
   str:      字符
   msg:      错误消息
   maxlen:   最大长度
   nullable: 可否为null
   type:     验证类型
   lenRange: 长度范围(必须将最大长度设为0)
   format:   验证格式
*/

// 显示错误消息
function showErrMsg(msg) {
	if (msg != null && msg != "") alert(msg);
}


function validatestr(str, msg, maxlen, nullable, type, lenRange, format) {

    if (isNull(str) == true) {
		if (nullable == false) {
			showErrMsg(msg);
            return false;
		}
		else
			return true;
    }

    var len = strlen(str);
    if (maxlen != 0) {
        if (len >  maxlen)
        {
			showErrMsg(msg);
		    return false;
        }
    } else {
		if (lenRange != null && lenRange != "") {
            var part = lenRange.split(",");
            var minlen = Number(part[0]);
            var maxlen = Number(part[1]);
            if(minlen > maxlen) {
		        alert("字符串长度范围设置错误！")
                return false;
			}
			if (!(minlen <= len && len <= maxlen)) {
		        showErrMsg(msg);
		        return false;
			}
		}
	}

	if (type != null && type != "")  {
		if (type == "none") return true;

        var fnc = checkMap[type];
		if (fnc != null) {
		    if (fnc(str, format) == false)
		    {
		        showErrMsg(msg);
		        return false;
		    }
		} else {
		    alert("没有匹配的检测方法！")
            return false;
		}
	}
    return true;
}

//比较前须确保日期格式正确
// date1 > date2  返回 1
// date1 = date2  返回 0
// date1 < date2  返回 -1
function compareDate(date1, date2) {

	var date1_split = date1.split("-");
	var date1_year = parseFloat(date1_split[0]);
	var date1_month = parseFloat(date1_split[1]);
	var date1_day = parseFloat(date1_split[2]);

	var date2_split = date2.split("-");
	var date2_year = parseFloat(date2_split[0]);
	var date2_month = parseFloat(date2_split[1]);
	var date2_day = parseFloat(date2_split[2]);

	if (date1_year < date2_year)
		return -1;
	else if (date1_year > date2_year)
		return 1;
	else {
		if (date1_month < date2_month)
			return -1;
		else if (date1_month > date2_month)
			return 1;
		else {
			if (date1_day < date2_day)
				return -1;
			else if (date1_day > date2_day)
			    return 1;
			else
				return 0;
		}
	}
}

function formValue(field, isTrim) {
	if (isTrim != undefined && isTrim == false) {
		return field.value;
	} else {
		return field.value = strTrim(field.value);
	}
}


/**
 * 确认函数
 * @param url
 * @param msg
 */
function confirmTool(url,msg){
	if(confirm(msg)){
		window.location.href = currenthost + url;
	}
}