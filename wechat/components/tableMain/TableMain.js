// components/tableMain/TableMain.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    // 处理过的数据
    tableData: [
      {
        uid: '1',
        sendTime: '2019-11-30 15:08:21',
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        duration: '2天',
        startTime: '2019-11-24 16:44:04',
        endTime: '2019-11-26 16:44:10',

        showWhat: 'button'
      }, {
        uid: '2',
        sendTime: '2019-11-30 15:08:21',
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        duration: '2天',
        startTime: '2019-11-24 16:44:04',
        endTime: '2019-11-26 16:44:10',

        showWhat: 'allow'
      }, {
        uid: '3',
        sendTime: '2019-11-30 15:08:21',
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        duration: '2天',
        startTime: '2019-11-24 16:44:04',
        endTime: '2019-11-26 16:44:10',

        showWhat: 'reject'
      }, {
        uid: '4',
        sendTime: '2019-11-30 15:08:21',
        counselor: '赵雅静',
        type: '公假',
        detail: '去比赛',
        duration: '2天',
        startTime: '2019-11-24 16:44:04',
        endTime: '2019-11-26 16:44:10',

        showWhat: 'wait'
      }],
      coloum: [],
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
