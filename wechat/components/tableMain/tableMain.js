// components/tableMain/TableMain.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    column: {
      require: true,
      type: Array
    },
    tableData: {
      require: true,
      type: Array
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    apply: {
      waitColumn: [{ 'text': '交提请假单' }, { 'text': '辅导员审批' }, { 'text': '院内审批' }, { 'text': '任课老师查看' }]
    }
  },

  methods: {
    buttonClick(e) {
      const dataset = e.currentTarget.dataset;
      this.triggerEvent('buttonClick', dataset);
    },
  }
})
