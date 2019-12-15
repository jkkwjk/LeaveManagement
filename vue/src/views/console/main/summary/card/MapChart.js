let template = {
    title : {
        text: '请假人数比总人数',
        subtext: '杭州电子科技大学信息工程学院',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        padding: [5,0,0,25],
        data: []
    },
    series : [
        {
            name: '请假人数',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
import {deepCopy} from '@/util/MemoryUtil.js';
function getOptions(f) {
    let options = deepCopy(template);
    for (let i in f){
        let data = f[i];
        if (data.n!==0){
            options.legend.data.push(data.p);
            options.series[0].data.push({value:data.n,name:data.p});
        }
    }
    return options;
}

export default getOptions