import dateUtil from "../../../utils/datautil.js"
Component({
  /**
   * 组件的初始数据
   */
  data: {
    tableData: [],
    column: [
      { title: "发送时间", prop: "sendTime" },{ title: "学号", prop: "number" },
      { title: "姓名", prop: "name" }, { title: "辅导员", prop: "counselor" },
      { title: "班级", prop: "class" }, { title: "请假类型", prop: "type" },
      { title: "具体原因", prop: "detail" },{ title: "请假天数", prop: "duration" }, 
      { title: "开始时间", prop: "startTime" },{ title: "结束时间", prop: "endTime" }],
    baseUrl: '',
    page: 1,
    num: 10,
    hasNext: true
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getData() {
      if (this.data.hasNext) {
        let data = [];
        var _this = this;
        wx.request({
          url: `http://localhost:8080/api/${_this.data.baseUrl}`,
          method: "post",
          header: { 'content-type': 'application/x-www-form-urlencoded' },
          data: {
            page: this.data.page,
            num: this.data.num,
            custom: '{"sort":{"prop":"","type":""},"custom":[{"prop":"showWhat","content":"0"},{"prop":"showWhat","content":"1"}]}'
          },
          success(res) {
            if (res.data.code === 200) {
              let tableData = res.data.data;
              _this.setData({
                page: _this.data.page + 1,
                hasNext: tableData.length == _this.data.num
              });
              tableData.map(_ => {
                const s = new Date(_.startTime);
                const e = new Date(_.endTime);
                _.duration = dateUtil.calcDate(s, e) + '天';
                _.startTime = dateUtil.formatChina(s);
                _.endTime = dateUtil.formatChina(e);
                if (typeof _.sendTime === "number") {
                  _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
                }
                _.wxType = "finish";
                return _;
              });
              _this.setData({
                tableData: tableData
              });
            }

          }
        });
      }
    },
  },
  lifetimes: {
    attached() {
      if (wx.getStorageSync("authType") === '辅导员') {
        let tmp = this.data.column;
        tmp.splice(tmp.findIndex(_ => { return _.prop === 'counselor' }), 1);
        this.setData({
          baseUrl: 'cou',
          column: tmp
        });
      } else if (wx.getStorageSync("authType") === '院领导') {
        this.setData({
          baseUrl: 'col'
        });
      }
      this.getData();
    }
  },
})
