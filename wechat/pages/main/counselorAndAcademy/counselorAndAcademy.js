// pages/main/student/student.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    router: 'todo'
  },
  toFinish() {
    this.setData({
      router: 'finish',
    });
  },
  toTodo() {
    this.setData({
      router: 'todo',
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
  onReachBottom: function () {
    if (this.data.router === 'todo') {
      const leaveApply = this.selectComponent("#todo");
      leaveApply.getData();
    } else if (this.data.router === 'finish') {
      const leaveApply = this.selectComponent("#finish");
      leaveApply.getData();
    }
  }
  
})