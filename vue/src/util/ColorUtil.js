//hex颜色转rgb颜色
function HexToRgb (str) {
    //replace替换查找的到的字符串
    str = str.replace("#", "");
    //match得到查询数组
    let hxs = str.match(/../g);
    //alert('bf:'+hxs)
    for (let i = 0; i < 3; i++) hxs[i] = parseInt(hxs[i], 16);
    //alert(parseInt(80, 16))
    //console.log(hxs);
    return hxs;
}
//GRB颜色转Hex颜色
/**
 * @return {string}
 */
function RgbToHex (a, b, c) {
    let hexs = [a.toString(16), b.toString(16), c.toString(16)];
    for (let i = 0; i < 3; i++) if (hexs[i].length === 1) hexs[i] = "0" + hexs[i];
    return "#" + hexs.join("");
}

//得到hex颜色值为color的加深颜色值，level为加深的程度，限0-1之间
function getDarkColor(color, level) {
    let rgbc = HexToRgb(color);
    //floor 向下取整
    for (let i = 0; i < 3; i++) rgbc[i] = Math.floor(rgbc[i] * (1 - level));
    return RgbToHex(rgbc[0], rgbc[1], rgbc[2]);
}
//得到hex颜色值为color的减淡颜色值，level为加深的程度，限0-1之间
function getLightColor(color, level) {
    let rgbc = HexToRgb(color);
    for (let i = 0; i < 3; i++) rgbc[i] = Math.floor((255 - rgbc[i]) * level + rgbc[i]);
    return RgbToHex(rgbc[0], rgbc[1], rgbc[2]);
}

export {
    getDarkColor,
    getLightColor
}