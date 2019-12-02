// view/student/leaveApply/leaveApply.js
import dateUtil from "../../../utils/datautil.js"
Component({
  data: {
    tableData: [],
    column: [{ title: "发送时间", prop: "sendTime" },
      { title: "请假类型", prop: "type" }, { title: "具体原因", prop: "detail" },
      { title: "辅导员", prop: "counselor" },{ title: "请假天数", prop: "duration" },
      { title: "开始时间", prop: "startTime" },{ title: "结束时间", prop: "endTime" }],
  },

  methods: {
    getData() {
      let testData = [{
        uid: '1',
        sendTime: 1575264600817,
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        startTime: 1575264600817,
        endTime: 1575999606817,
        showWhat: 'button'
      }, {
        uid: '2',
        sendTime: 1575264600817,
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        startTime: 1575264600817,
        endTime: 1575264606817,

        showWhat: 'allow'
      }, {
        uid: '3',
        sendTime: 1575264600817,
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        startTime: 1575264600817,
        endTime: 1575264606817,

        showWhat: 'reject'
      }, {
        uid: '4',
        sendTime: 1575264600817,
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        startTime: 1575264600817,
        endTime: 1575264606817,

        showWhat: 'wait'
      }];
      testData.map(_=>{
        const s = new Date(_.startTime);
        const e = new Date(_.endTime);
        _.duration = dateUtil.calcDate(s, e) + '天';
        _.startTime = dateUtil.formatChina(s);
        _.endTime = dateUtil.formatChina(e);
        if (typeof _.sendTime === "number") {
          _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
        }
        _.wxType = "apply";
        return _;
      });
      this.setData({
        tableData: testData
      });
    },
    delClick() {
      this.triggerEvent('del', myEventDetail, myEventOption)
    },
    sendClick() {

    },
    editClick() {

    },
    editClick() {

    },
    addClick() {
      wx.navigateTo({
        url: 'applyMain/applyMain',
      })
    }
  },
  lifetimes: {
    attached() {
      this.getData();
    },
  },

})