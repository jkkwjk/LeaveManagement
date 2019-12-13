// pages/login/login.js
import '../../http/weapp-cookie/index'
Page({

  /**
   * 页面的初始数据
   */
  data: {

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

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },
  submit(e){
    const detail = e.detail.value;
    wx.request({
      url: 'http://localhost:8080/api/user/login',
      method: 'get',
      data: {
        id: detail.id,
        password: detail.pwd,
      },
      success(res){
        if (res.data.code === 200){
          wx.request({
            url: 'http://localhost:8080/api/auth',
            success(res){
              let url = '';
              switch(res.data.name){
                case '学生':
                  url = '../main/student/student'
                  break;
                case '教师':
                  url = '../main/teacher/teacher'
                  break;
                case '院领导':
                case '辅导员':
                  wx.setStorageSync("authType", res.data.name);
                  url = '../main/counselorAndAcademy/counselorAndAcademy'
                  break;
              }
              wx.navigateTo({
                url: url
              });
            }
          })
        }else{
          wx.showModal({
            title: '错误',
            content: res.data.msg,
            showCancel: false,
          });
        }
      }
    })
    
  }
})