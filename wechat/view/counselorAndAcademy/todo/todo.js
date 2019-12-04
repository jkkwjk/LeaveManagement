import dateUtil from "../../../utils/datautil.js"
Component({
  /**
   * 组件的初始数据
   */
  data: {
    tableData: [],
    column: [
      { title: "发送时间", prop: "sendTime" },{ title: "学号", prop: "number" },
      { title: "姓名", prop: "name" }, { title: "辅导员", prop: "counselor" },
      { title: "班级", prop: "class" }, { title: "请假类型", prop: "type" },
      { title: "具体原因", prop: "detail" },{ title: "请假天数", prop: "duration" }, 
      { title: "开始时间", prop: "startTime" },{ title: "结束时间", prop: "endTime" }],
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getData() {
      let testData = [{
        uid: '1',
        name: '金凯凯',
        number: '189050416',
        sendTime: 1575264600817,
        counselor: '赵雅静',
        class: '189050113',
        type: '公假',
        detail: '去比赛',
        startTime: 1575264600817,
        endTime: 1575999606817,
        showWhat: 'button'
      }, {
          uid: '2',
          name: '金凯凯',
          number: '189050416',
          sendTime: 1575264600817,
          counselor: '赵雅静',
          class: '189050113',
          type: '公假',
          detail: '去比赛',
          startTime: 1575264600817,
          endTime: 1575999606817,

        showWhat: 'allow'
      }, {
          uid: '3',
          name: '金凯凯',
          number: '189050416',
          sendTime: 1575264600817,
          counselor: '赵雅静',
          class: '189050113',
          type: '公假',
          detail: '去比赛',
          startTime: 1575264600817,
          endTime: 1575999606817,

        showWhat: 'reject'
      }];
      testData.map(_ => {
        const s = new Date(_.startTime);
        const e = new Date(_.endTime);
        _.duration = dateUtil.calcDate(s, e) + '天';
        _.startTime = dateUtil.formatChina(s);
        _.endTime = dateUtil.formatChina(e);
        if (typeof _.sendTime === "number") {
          _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
        }
        _.wxType = "leaveManagement";
        return _;
      });
      this.setData({
        tableData: testData
      });
    },
    allowClick(row){
      wx.showModal({
        title: '同意',
        content: '已同意该请假申请',
        showCancel: false
      });
      // 获取数据的代码写在外层
    },
    rejectClick(row) {
      wx.showModal({
        title: '拒绝',
        content: '已拒绝该请假申请',
        showCancel: false
      });
    },
    buttonClick(e){
      switch(e.detail.what){
        case 'allow':
          this.allowClick(e.detail.row);
          break;
        case 'reject':
          this.rejectClick(e.detail.row);
          break;
      }
    }
  },
  lifetimes: {
    attached() {
      this.getData();
    }
  },
})
