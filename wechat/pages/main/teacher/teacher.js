// pages/main/teacher/teacher.js
import dateUtil from "../../../utils/datautil.js"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    lessones:[
      
    ],
    showLessones: false,
    currentLesson: {},
    displaySearch: true,
    leaveData: [],
    column: [{ title: "发送时间", prop: "sendTime" }, { title: "学号", prop: "number" },
      { title: "姓名", prop: "name" }, { title: "辅导员", prop: "counselor" },
      { title: "班级", prop: "class" }, { title: "请假类型", prop: "type" },
      { title: "具体原因", prop: "detail" }, { title: "请假天数", prop: "duration" }, 
      { title: "开始时间", prop: "startTime" }, { title: "结束时间", prop: "endTime" }],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      lessones: [{title: '高等数学A1',prop: 'XML-88-99-1'},
      { title: '高等数学A2', prop: 'XML-88-99-2' }],
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  closeLessones(){
    this.setData({
      showLessones: false,
    });
  },
  openLessones() {
    this.setData({
      showLessones: true,
    });
  },
  search(){
    let data = [{
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
    data.map(_ => {
      const s = new Date(_.startTime);
      const e = new Date(_.endTime);
      _.duration = dateUtil.calcDate(s, e) + '天';
      _.startTime = dateUtil.formatChina(s);
      _.endTime = dateUtil.formatChina(e);
      if (typeof _.sendTime === "number") {
        _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
      }
      return _;
    });
    this.setData({
      displaySearch: false,
      leaveData: data,
    });
  },
  clickLesson(e){
    this.setData({
      currentLesson: e.currentTarget.dataset,
    });
    this.closeLessones();
  },
  back(){
    this.onLoad();
    this.setData({
      currentLesson: {},
      displaySearch: true,
    });
  }
})