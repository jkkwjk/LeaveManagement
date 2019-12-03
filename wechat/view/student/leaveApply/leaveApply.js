// view/student/leaveApply/leaveApply.js
import dateUtil from "../../../utils/datautil.js"
Component({
  data: {
    tableData: [],
    column: [{ title: "发送时间", prop: "sendTime" },
      { title: "请假类型", prop: "type" }, { title: "具体原因", prop: "detail" },
      { title: "辅导员", prop: "counselor" },{ title: "请假天数", prop: "duration" },
      { title: "开始时间", prop: "startTime" },{ title: "结束时间", prop: "endTime" }],
    fromHide: false
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
        waitActive: 2,
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
    delClick(row) {
      wx.showToast({ title: '删除成功' });
      this.getData();
    },
    sendClick(row) {
      const _this = this;
      const endTimeD = dateUtil.fixTimezoneOffset(dateUtil.parse(row.endTime));
      if (endTimeD < new Date()){
        wx.showModal({
          title: '错误',
          content: '结束时间超过当前日期, 请修改后再试',
          showCancel: false,
        });
      }else{
        wx.showModal({
          title: '确认继续',
          content: '发送请假信息后将不能修改',
          confirmText: "确认",
          cancelText: "取消",
          success(res) {
            if (res.confirm) {
              wx.showToast({ title: '发送成功' });
              const index = _this.data.tableData.findIndex(_ => { return _.uid === row.uid });
              row.showWhat = 'wait';
              const editVal = `tableData[${index}]`;
              _this.setData({
                [editVal]: row
              });
            }
          }
        });
      }
      
    },
    editClick(row) {
      wx.navigateTo({
        url: '/view/student/leaveApply/applyMain/applyMain?row='+JSON.stringify(row),
      })
    },
    addClick() {
      wx.navigateTo({
        url: '/view/student/leaveApply/applyMain/applyMain',
      })
    },
    buttonClick(e) {
      switch(e.detail.what){
        case 'send':
          this.sendClick(e.detail.row);
          break;
        case 'edit':
          this.editClick(e.detail.row);
          break;
        case 'del':
          this.delClick(e.detail.row);
          break;
      }
    }
  },
  lifetimes: {
    attached() {
      if(!this.data.fromHide){
        // 底部导航栏进入
        this.getData();
      }
      this.setData({
        fromHide: false
      });
    }
  },
  pageLifetimes: {
    show() {
      // 添加或修改进入
      if (this.data.fromHide) {
        this.getData();
      }
      this.setData({
        fromHide: false
      });
    },

    hide(){this.setData({fromHide: true});}
  },
})