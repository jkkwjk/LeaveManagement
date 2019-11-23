let template = {
    title : {
        text: '各种请假原因的请假人数',
        subtext: '杭州电子科技大学信息工程学院',
        x:'center'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    legend: {
        left: 'left',
        data:[]
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : []
};
import {deepCopy} from '@/util/MemoryUtil.js';
function getOptions(f) {
    let options = deepCopy(template);
    //求和并排序
    for (let i in f){
        let data = f[i];
        let sum = 0;
        for (let j in data.v){
            sum += data.v[j];
        }
        data["sum"] = sum;
    }
    f.sort((a,b)=>{
        return a.sum-b.sum;
    });


    for (let i in f){
        let data = f[i];
        let t = {
            name:data.t,
            type:'line',
            stack: '总量',
            areaStyle: {},
            data:data.v
        };
        options.legend.data.push(data.t);
        options.series.push(t);
    }
    options.series[options.series.length-1]["label"]={
        normal: {
            show: true,
            position: 'top'
        }
    };
    return options;
}

export default getOptions