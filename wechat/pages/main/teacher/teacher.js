// pages/main/teacher/teacher.js
import dateUtil from "../../../utils/datautil.js"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    lessones:[],
    showLessones: false,
    currentLesson: {},
    displaySearch: true,
    leaveData: [],

    showWeeks: false,
    week: {},
    column: [{ title: "学号", prop: "number" }, { title: "班级", prop: "class" },
      { title: "姓名", prop: "name" }, { title: "辅导员", prop: "counselor" },
      { title: "请假类型", prop: "type" }, { title: "具体原因", prop: "detail" }],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    wx.request({
      url: 'http://localhost:8080/api/tea',
      method: "get",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      success(res){
        _this.setData({
          lessones: res.data.data
        })
      }
    })
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

  search(){
    var _this = this;
    wx.request({
      url: 'http://localhost:8080/api/tea',
      method: "post",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        week: _this.data.week.prop,
        lesson: _this.data.currentLesson.prop,
      },
      success(res) {
        let data = res.data.data;
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
        _this.setData({
          displaySearch: false,
          leaveData: data,
        });
      }
    })
  },

  closeLessones() {
    this.setData({
      showLessones: false,
    });
  },
  openLessones() {
    this.setData({
      showLessones: true,
    });
  },
  clickLesson(e){
    this.setData({
      currentLesson: e.currentTarget.dataset,
    });
    this.closeLessones();
  },

  closeWeeks() {
    this.setData({
      showWeeks: false,
    });
  },
  openWeeks() {
    this.setData({
      showWeeks: true,
    });
  },
  clickWeek(e) {
    this.setData({
      week: e.currentTarget.dataset,
    });
    this.closeWeeks();
  },
  back(){
    this.onLoad();
    this.setData({
      displaySearch: true,
    });
  }
})