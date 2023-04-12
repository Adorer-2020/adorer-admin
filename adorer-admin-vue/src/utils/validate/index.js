/**
 * 校验手机号是否合法
 *
 * @param {*} value
 * @returns
 */
export function isMobile(value) {
    return /^1[0-9]{10}$/.test(value);
}

/**
 * 校验邮箱是否合法
 * @param {*} value
 * @returns
 */
export function isEmail(value) {
    return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(value);
}

/**
 * 验证 URL 地址是否合法
 * @param {*} value
 * @returns
 */
export function isURL(value) {
    return /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/.test(value);
}

/**
 * 验证身份证号码
 * @param {*} value
 * @returns
 */
export function isIDCard(value) {
    return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
}

/**
 * 校验文件是否为图片
 * @param {*} file
 * @returns
 */
export function isImage(file) {
    return /\.(jpg|jpeg|png|gif|bmp)$/.test(file.name.toLowerCase());
}

/**
 * 校验文件是否为文本文件
 * @param {*} file
 * @returns
 */
export function isTextFile(file) {
    return /\.(txt|md)$/.test(file.name.toLowerCase());
}
