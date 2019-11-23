let template = {
    title : {
        text: '请假人数与成绩的相对系数',
        subtext: '杭州电子科技大学信息工程学院',
        x:'center'
    },
    xAxis: {
        type: 'category',
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [],
        type: 'line'
    }]
};
import {deepCopy} from '@/util/MemoryUtil.js';
function getOptions(f) {
    let options = deepCopy(template);
    for (let i in f){
        let data = f[i];
        options.xAxis.data.push(data.t);
        options.series[0].data.push(data.v);
    }
    return options;
}

export default getOptions