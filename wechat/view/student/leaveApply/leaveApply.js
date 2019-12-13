// view/student/leaveApply/leaveApply.js
import dateUtil from "../../../utils/datautil.js"
Component({
  data: {
    tableData: [],
    column: [{ title: "发送时间", prop: "sendTime" },
      { title: "请假类型", prop: "type" }, { title: "具体原因", prop: "detail" },
      { title: "辅导员", prop: "counselor" },{ title: "请假天数", prop: "duration" },
      { title: "开始时间", prop: "startTime" },{ title: "结束时间", prop: "endTime" }],
    fromHide: false,
    page: 1,
    num: 10,
    hasNext: true
  },

  methods: {
    getData() {
      if(this.data.hasNext){
        var _this = this;
        wx.request({
          url: 'http://localhost:8080/api/stu',
          method: "post",
          header: { 'content-type': 'application/x-www-form-urlencoded' },
          data: {
            page: this.data.page,
            num: this.data.num,
            custom: '{"sort":{"prop":"","order":""},"custom":[]}'
          },
          success(res){
            let data = res.data.data;
            _this.setData({
              page: _this.data.page+1,
              hasNext: data.length == _this.data.num
            });

            const statusAync = (i,id) => {
              return new Promise((resolve, reject) => {
                wx.request({
                  url: "http://localhost:8080/api/stu/getStatus",
                  method: 'post',
                  header: { 'content-type': 'application/x-www-form-urlencoded' },
                  data: {
                    id: id
                  },
                  success(res) {
                    resolve({
                      pos: i,
                      active: res.data.data.active
                    })
                  },
                  fail(err) {
                    reject(err)
                  }
                })
              })
            }
            let pro = [];
            data.map((_,i) => {
              const s = new Date(_.startTime);
              const e = new Date(_.endTime);
              _.duration = dateUtil.calcDate(s, e) + '天';
              _.startTime = dateUtil.formatChina(s);
              _.endTime = dateUtil.formatChina(e);
              if (_.sendTime !== null && _.sendTime !== '未发送') {
                _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
              } else {
                _.sendTime = '未发送';
              }
              if (_.showWhat === 'wait'){
                pro.push(statusAync(i, _.id));
              }
              _.wxType = "apply";
              return _;
            });
            Promise.all(pro).then(res=>{
              res.map(_ => {
                data[_.pos].waitActive = _.active;
              });
              _this.setData({
                tableData: _this.data.tableData.concat(data)
              });
            });
            
          }
        })
      }
      
    },
    
    delClick(row) {
      var _this = this;
      wx.request({
        url: 'http://localhost:8080/api/stu/toTrash',
        method: "post",
        header: { 'content-type': 'application/x-www-form-urlencoded' },
        data: {
          id: row.id
        },
        success(res) {
          const data = res.data;
          if (data.code === 200) {
            wx.showToast({ title: '删除成功' });
            let tmp = _this.data.tableData;
            tmp.splice(tmp.findIndex(_ => { return _.id === row.id }), 1);
            _this.setData({
              tableData: tmp
            });
            if (tmp.length < _this.data.num){
              _this.getData();
            }
          }else {
            wx.showModal({
              title: '错误',
              content: data.msg,
              showCancel: false,
            });
          }
        }
      })
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
              wx.request({
                url: 'http://localhost:8080/api/stu/send',
                method: "post",
                header: { 'content-type': 'application/x-www-form-urlencoded' },
                data: {
                  id: row.id
                },
                success(res){
                  const data = res.data;
                  if (data.code === 200){
                    wx.showToast({ title: '发送成功' });
                    const index = _this.data.tableData.findIndex(_ => { return _.id === row.id });
                    row.showWhat = 'wait';
                    row.sendTime = dateUtil.formatChina(new Date());
                    const editVal = `tableData[${index}]`;
                    _this.setData({
                      [editVal]: row
                    });
                  } else{
                    wx.showModal({
                      title: '错误',
                      content: data.msg,
                      showCancel: false,
                    });
                  }
                }
              })
              
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
    },
    reset(){
      this.setData({
        page: 1,
        hasNext: true,
        tableData: []
      });
      this.getData();
    }
  },
  lifetimes: {
    attached() {
      if(!this.data.fromHide){
        this.reset();
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
        this.reset();
      }
      this.setData({
        fromHide: false
      });
    },

    hide(){this.setData({fromHide: true});}
  },
})