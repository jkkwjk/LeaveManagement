import dateUtil from "../../../../utils/datautil.js"
Page({
  data: {
    title: '',
    row: {},
    showLeaveType: false,
    isSubmit: false, //交提按钮可点击
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

  onLoad: function(options) {
    const now = new Date();
    this.setData({
      "now.date": now.getFullYear() + '-' + (parseInt(now.getMonth()) + 1) + '-' + now.getDate()
    });
    if (options.row === undefined) {
      this.setData({
        title: '新建请假申请',
      });
    } else {
      const row = JSON.parse(options.row);
      const startTimeD = dateUtil.fixTimezoneOffset(dateUtil.parse(row.startTime));
      const endTimeD = dateUtil.fixTimezoneOffset(dateUtil.parse(row.endTime));
      function PrefixZero(num, n) {
        return (Array(n).join(0) + num).slice(-n);
      }
      this.setData({
        title: '修改请假申请',
        row: row,
        "form.type": row.type,
        "form.detail": row.detail,
        "form.date.start": startTimeD.getFullYear() + '-' + PrefixZero(startTimeD.getMonth() + 1, 2) + '-' + PrefixZero(startTimeD.getDate(),2),
        "form.date.end": endTimeD.getFullYear() + '-' + PrefixZero(endTimeD.getMonth() + 1, 2) + '-' + PrefixZero(endTimeD.getDate(),2),
        "form.time.start": PrefixZero(startTimeD.getHours(), 2) + ':' + PrefixZero(startTimeD.getMinutes(),2),
        "form.time.end": PrefixZero(endTimeD.getHours(), 2) + ':' + PrefixZero(endTimeD.getMinutes(),2),
      });
    }
  },
  showLeaveType() {this.setData({showLeaveType: true})},

  closeLeaveType() {this.setData({showLeaveType: false})},

  clickLeaveType(e) {
    this.setData({"form.type": e.currentTarget.dataset.type});
    this.closeLeaveType();
  },

  detailChange(e) {this.setData({"form.detail": e.detail.value});},
  /**
   * 所有选择时间日期的控件change回调
   */
  dateChange(e) {
    const dataset = e.currentTarget.dataset;
    if (dataset.what === 'date') {
      if (dataset.range === 'start') {
        this.setData({
          "form.date.start": e.detail.value
        });
      } else {
        this.setData({
          "form.date.end": e.detail.value
        });
      }
    } else {
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
  /**
   * 表单验证
   */
  verifyForm() {
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
  /**
   * 表单交提
   */
  submit() {
    var _this = this;
    this.setData({
      isSubmit: true,
    });
    const tip = this.verifyForm();
    if (tip === undefined) {
      const type = this.data.form.type;
      const detail = this.data.form.detail;
      const startTime = this.data.form.date.start + " " + this.data.form.time.start + ":00";
      const endTime = this.data.form.date.end + " " + this.data.form.time.end + ":00";
      if (this.data.row.id === undefined){
        wx.request({
          url: 'http://localhost:8080/api/stu/add',
          method: "post",
          header: { 'content-type': 'application/x-www-form-urlencoded' },
          data: {
            type: type, detail: detail,
            startTime: dateUtil.fixTimezoneOffset(dateUtil.parse(startTime)).getTime(),
            endTime: dateUtil.fixTimezoneOffset(dateUtil.parse(endTime)).getTime()
          },
          success(res){
            const data = res.data
            if (data.code === 200){
              wx.showToast({
                title: _this.data.title.substr(0, 2) + '成功',
                success() { setTimeout(() => { wx.navigateBack({ delta: 1 }); }, 1500) }
              });
            }
          }
        })
      }else{
        wx.request({
          url: 'http://localhost:8080/api/stu/modify',
          method: "post",
          header: { 'content-type': 'application/x-www-form-urlencoded' },
          data: {
            id: _this.data.row.id, type: type, detail: detail,
            startTime: dateUtil.fixTimezoneOffset(dateUtil.parse(startTime)).getTime(),
            endTime: dateUtil.fixTimezoneOffset(dateUtil.parse(endTime)).getTime()
          },
          success(res) {
            const data = res.data
            if (data.code === 200) {
              wx.showToast({
                title: _this.data.title.substr(0, 2) + '成功',
                success() { setTimeout(() => { wx.navigateBack({ delta: 1 }); }, 1500) }
              });
            }
          }
        })
      }
    } else {
      wx.showModal({
        title: '错误',
        content: tip,
        showCancel: false,
      });
      this.setData({
        isSubmit: false,
      });
    }
  }
})