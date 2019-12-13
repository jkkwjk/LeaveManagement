// pages/main/student/student.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    router: 'apply'
  },
  toTrachBin() {
    this.setData({
      router: 'trashBin',
    });
  },
  toApply() {
    this.setData({
      router: 'apply',
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },
  onReachBottom: function() {
    if (this.data.router === 'apply'){
      const leaveApply = this.selectComponent("#leaveApply");
      leaveApply.getData();
    }
  }
})