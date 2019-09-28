<template>
  <div :style="{height:height+'px'}">
    <el-container :style="{height:height+'px'}">
      <el-aside width="200px">
        <el-table :data="list" stripe style="width: 100%">
          <el-table-column
            prop="name"
            label="在线列表"
          />
        </el-table>
      </el-aside>
      <el-container>
        <el-header>雪中聊天室</el-header>
        <el-main>
          <div id="msgDiv" style="height:80%;text-align: left;width: 100%;" />
          <div style="margin-top: 15px;">
            <el-input v-model="message" placeholder="请输入内容" class="input-with-select">
              <el-button slot="append" :disabled="webSocket===null || webSocket.readyState!=1" @click="sendMsg()"><svg-icon icon-class="send" /></el-button>
            </el-input>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'ChatRoom',
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      webSocket: null,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10
      },
      height: 0,
      sortable: null,
      oldList: [],
      newList: [],
      messages: '',
      message: ''
    }
  },
  created() {
    this.initWebSocket()
  },
  mounted() {
    this.$nextTick(() => {
      this.height = document.getElementById('app').clientHeight - 90
      console.log(this.height)
    })
  },
  methods: {
    async initWebSocket() {
      // const wsuri = process.env.VUE_APP_BASE_API + "/webSocket/chatroom";//这个地址由后端童鞋李诗怡提供
      const wsuri = 'ws://192.168.0.80:8090/webSocket/chatroom'
      this.webSocket = new WebSocket(wsuri)
      this.webSocket.onmessage = this.websocketonmessage
      this.webSocket.onopen = this.websocketonopen
      this.webSocket.onerror = this.websocketonerror
      this.webSocket.onclose = this.websocketClose
    },
    websocketonopen() {
      console.log('连接建立成功')
    },
    websocketonerror() {
      // 先关闭
      this.webSocket.close()
      // 连接建立失败重连
      this.initWebSocket()
    },
    websocketonmessage(msg) {
      document.getElementById('msgDiv').innerHTML += msg.data + '<br/>'
    },
    sendMsg() {
      if (this.message !== null && this.message !== '') {
        this.webSocket.send(this.message)
        this.message = ''
      }
    },
    websocketClose(e) {
      console.log('断开连接', e)
    }
  }
}
</script>

<style>
.sortable-ghost{
  opacity: .8;
  color: #fff!important;
  background: #42b983!important;
}
</style>

<style scoped>
.icon-star{
  margin-right:2px;
}
.drag-handler{
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.show-d{
  margin-top: 15px;
}
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
    height: 20%;
}
.el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    height: 100%;
}
.el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    height: 80%;
}
</style>
