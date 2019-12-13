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
      let tableData = [];
      var _this = this;
      wx.request({
        url: 'http://localhost:8080/api/stu',
        method: "get",
        header: { 'content-type': 'application/x-www-form-urlencoded' },
        success(res){
          let data = res.data.data;
          data.map(_ => {
            const s = new Date(_.startTime);
            const e = new Date(_.endTime);
            _.duration = dateUtil.calcDate(s, e) + '天';
            _.startTime = dateUtil.formatChina(s);
            _.endTime = dateUtil.formatChina(e);
            _.wxType = "trashBin";
            return _;
          });

          _this.setData({
            tableData: data
          });
        }
      });
      
      
    },
    reductionClick(row){
      var _this = this;
      wx.request({
        url: 'http://localhost:8080/api/stu/reduction',
        method: "post",
        header: { 'content-type': 'application/x-www-form-urlencoded' },
        data: {
          id: row.id
        },
        success(res){
          const data = res.data;
          if (data.code === 200){
            let tmp = _this.data.tableData;
            tmp.splice(tmp.findIndex(_ => { return _.id === row.id }), 1);
            _this.setData({
              tableData: tmp
            });

            wx.showModal({
              title: '成功',
              content: '还原成功',
              showCancel: false
            });
          } else {
            wx.showModal({
              title: '错误',
              content: data.msg,
              showCancel: false,
            });
          }
        }
      });
    },
    delClick(row) {
      var _this = this;
      wx.request({
        url: 'http://localhost:8080/api/stu/del',
        method: "post",
        header: { 'content-type': 'application/x-www-form-urlencoded' },
        data: {
          id: row.id
        },
        success(res) {
          const data = res.data;
          if (data.code === 200) {
            let tmp = _this.data.tableData;
            tmp.splice(tmp.findIndex(_ => { return _.id === row.id }), 1);
            _this.setData({
              tableData: tmp
            });

            wx.showModal({
              title: '成功',
              content: '删除成功',
              showCancel: false
            });
          } else {
            wx.showModal({
              title: '错误',
              content: data.msg,
              showCancel: false,
            });
          }
        }
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
