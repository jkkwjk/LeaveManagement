// pages/main/student/addOrChangeApply/applyMain.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: '',
    row: {},
    showLeaveType: false,
    now: {
      date: '',
    },
    form: {
      type: '',
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const now = new Date();
    this.setData({
      "now.date": now.getFullYear+'-'+now.getMonth+1+'-'+now.getDate();
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function (e) {
    
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
  }
})