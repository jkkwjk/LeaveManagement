import dateUtil from "../../../utils/datautil.js"
Component({
  /**
   * 组件的初始数据
   */
  data: {
    tableData: [],
    column: [{ title: "开始时间", prop: "startTime" }, { title: "结束时间", prop: "endTime" },
      { title: "请假天数", prop: "duration" },{ title: "请假类型", prop: "type" },
      { title: "具体原因", prop: "detail" }],
  },

  /**
   * 组件的方法列表
   */
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
        waitActive: 2,
        showWhat: 'wait'
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
        _.wxType = "trashBin";
        return _;
      });
      this.setData({
        tableData: testData
      });
    },
    reductionClick(row){
      wx.showModal({
        title: '成功',
        content: '还原成功',
        showCancel: false
      });
      // 获取数据的代码写在外层
    },
    delClick(row) {
      wx.showModal({
        title: '成功',
        content: '删除成功',
        showCancel: false
      });
    },
    buttonClick(e){
      switch(e.detail.what){
        case 'reduction':
          this.reductionClick(e.detail.row);
          break;
        case 'del':
          this.delClick(e.detail.row);
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
