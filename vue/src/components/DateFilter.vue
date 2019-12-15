<template>
    <div class="block">
        <el-date-picker
                v-model="time"
                type="monthrange"
                range-separator="至"
                start-placeholder="开始月份"
                end-placeholder="结束月份"
                :picker-options="pickerOptions">
        </el-date-picker>
    </div>
</template>

<script>
    import {calendar} from '@/util/calendar'
    export default {
        name: "DateFilter",
        props: {dateChange: {type: Function, require: true}},
        watch: {
            time(){
                this.dateChange(this.time);
            }
        },
        data() {
            return {
                pickerOptions: {
                    shortcuts: [{
                        text: '本学期',
                        onClick: (picker)=>{
                            picker.$emit('pick', this.getThisTeam());
                        }
                    }, {
                        text: '上学期',
                        onClick: (picker)=>{
                            picker.$emit('pick', this.getLastItem());
                        }
                    }, {
                        text: '今年至今',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date(new Date().getFullYear(), 0);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                time: ''
            };
        },
        methods: {
            getThisTeam(){
                let {chuxi_hou,now,aYear,chuxi}= this.calcTime();

                if (now > new Date(aYear,7,1) && now < new Date(chuxi.cYear, chuxi.cMonth, chuxi.cDay)){
                    return [new Date(aYear,7,1),new Date(chuxi.cYear, chuxi.cMonth-1, chuxi.cDay)];
                }else {
                    return [new Date(chuxi_hou.cYear, chuxi_hou.cMonth-1, chuxi_hou.cDay),new Date(aYear,7,1)];
                }
            },
            getLastItem(){
                let {chuxi_hou,now,aYear,chuxi}= this.calcTime();
                chuxi_hou = calendar.lunar2solar(aYear-1,12,30);
                if (now > new Date(aYear,7,1) && now < new Date(chuxi.cYear, chuxi.cMonth, chuxi.cDay)){ //8.1
                    return [new Date(chuxi_hou.cYear, chuxi_hou.cMonth-1, chuxi_hou.cDay),new Date(aYear,7,1)];
                    //console.log(`${aYear}-${aYear+1}-2`);
                }else {
                    return [new Date(aYear-1,7,1),new Date(chuxi_hou.cYear, chuxi_hou.cMonth-1, chuxi_hou.cDay)];
                    //console.log(`${aYear}-${aYear+1}-1`);
                }
            },
            calcTime(){
                let year = new Date().getFullYear();
                let mouth = new Date().getMonth();
                let day = new Date().getDay();
                let aYear = year; //农历中的今年 2020 1 1 的 今年是2019
                // 判断是否是来年
                let chuxi_hou = calendar.lunar2solar(year-1,12,30);
                let chuxi = calendar.lunar2solar(year,12,30);
                let now = new Date(year,mouth,day);
                if (now < new Date(chuxi_hou.cYear, chuxi_hou.cMonth, chuxi_hou.cDay)) {
                    chuxi = chuxi_hou;
                    aYear = year-1;
                }
                return {chuxi_hou: chuxi_hou, now: now, aYear: aYear, chuxi: chuxi};
            }
        }
    }
</script>

<style scoped>
    .block{
        margin-left: 40px;
        display: inline;
    }

</style>