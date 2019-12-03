import dateUtil from "../../../../utils/datautil.js"
Page({
  data: {
    title: '',
    row: {},
    showLeaveType: false,
    now: {
      date: '',
    },
    form: {
      type: '',
      date: {
        start: '',
        end: '',
      },
      time: {
        start: '00:00',
        end: '00:00',
      },
      detail: '',
    }
  },

  onLoad: function (options) {
    const now = new Date();
    this.setData({
      "now.date": now.getFullYear()+'-'+(parseInt(now.getMonth())+1)+'-'+now.getDate()
    });
    if (options.row === undefined){
      this.setData({
        title: '新建请假申请',
      });
    }else{
      this.setData({
        title: '修改请假申请',
      });
    }
  },

  showLeaveType() {
    this.setData({
      showLeaveType: true
    })
  },
  closeLeaveType() {
    this.setData({
      showLeaveType: false
    })
  },
  clickLeaveType(e){
    this.setData({
      "form.type": e.currentTarget.dataset.type,
    });
    this.closeLeaveType();
  },
  detailChange(e){
    this.setData({
      "form.detail": e.detail.value
    });
    
  },
  dateChange(e){
    const dataset = e.currentTarget.dataset;
    if (dataset.what === 'date'){
      if (dataset.range === 'start'){
        this.setData({
          "form.date.start" :e.detail.value
        });
      }else{
        this.setData({
          "form.date.end": e.detail.value
        });
      }
    }else{
      if (dataset.range === 'start') {
        this.setData({
          "form.time.start": e.detail.value
        });
      } else {
        this.setData({
          "form.time.end": e.detail.value
        });
      }
    }
  },
  verifyForm(){
    const form = this.data.form;
    if (form.type === '')
      return '请假类型不能为空';
    if (form.detail === '')
      return '请假原因不能为空';
    if (form.date.start === '')
      return '开始日期不能为空';
    if (form.date.end === '')
      return '结束日期不能为空';
    const startTime = form.date.start + ' ' + form.time.start;
    const startTimeD = dateUtil.fixTimezoneOffset(dateUtil.parse(startTime));
    const endTime = form.date.end + ' ' + form.time.end;
    const endTimeD = dateUtil.fixTimezoneOffset(dateUtil.parse(endTime));
    if (startTimeD >= endTimeD)
      return '开始日期不能大于结束日期';

    if (endTimeD < new Date())
      return '结束时间超过当前日期';
  },
  submit(){
    const tip = this.verifyForm();
    if(tip === undefined){
      console.log("成功");
    }else{
      console.log(tip);
    }
  }
})