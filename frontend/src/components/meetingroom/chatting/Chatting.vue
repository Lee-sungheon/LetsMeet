<template>
  <v-container>
    <Chat 
      :initial-feed="feed"
      :title="'Chat Room'+ ' (' +(mrUserInfo.length)+ '명)'"
      :initial-author-id="authorId"
      @newOwnMessage="onNewOwnMessage"
    />
  </v-container>
</template>

<script>
import { scrollToBottom } from '@/helpers/scroll.js'
import Chat from './BasicVueChat.vue'
import moment from 'moment'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
const axios = require('axios')
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "Chatting",
  components: {
    Chat,
  },
  props: {
    mrNo: {},
    mrUserInfo: Array,
  },

  data() {
    return {
      authorId: 1,
      authname: '',
      message: {
        id: 0,
        author: 'Person',
        imageUrl: 'http://path/to/image',
        image: '',
        contents: '테스트 중',
        date: '16:30'
      },
      user: {
      }
      ,
      feed: [
      ],
    }
  },
  created() {
    this.connect()
    this.authname = this.$store.state.uName
    this.authorId = this.$store.state.uNo
  },
  beforeDestroy() {
    console.log("채팅창 종료!")
    if (this.stompClient) {
      this.stompClient.unsubscribe("/send")
      this.stompClient.disconnect()
    }
  },
  destroyed() {
    console.log("제거완료")
  },
  methods: {
    onNewOwnMessage (message) {
      const newOwnMessage = {
        id: this.authorId,
        author: this.authname,
        contents: message,
        date: moment().format('HH:mm')
      }
      console.log(newOwnMessage)
      this.message = newOwnMessage
      this.send()
    },
    created() {
      this.connect()
    },
    send() {
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          mrcUNo: this.authorId,
          mrcContent: this.message.contents,
          mrcMrNo: this.mrNo
        };
        this.stompClient.send("/pub/receive", JSON.stringify(msg), {});
      }
    },    
    connect() {
      for (var i=0; i < this.mrUserInfo.length; i++) {
        this.user[this.mrUserInfo[i].uNo] = this.mrUserInfo[i].uName
      }
      axios.get(`${server_URL}/letsmeet/chat/open?mrcMrNo=${this.mrNo}`)
      .then((res)=> {
        console.log(res.data)
        for (var re of res.data){
          var tmp_date = re.mrcDate
          var img = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
          for (var user of this.mrUserInfo) {
            if (re.mrcUNo === user.uNo && user.uImage !== null){
              img = user.uImage
            }
          }
          const feed_data = {
            id: re.mrcUNo,
            author: this.user[re.mrcUNo],
            contents: re.mrcContent,
            date: tmp_date.substring(11,16),
            uImage: img
          }
          console.log(tmp_date)
          this.feed.push(feed_data)
          scrollToBottom ()
        }

      })
      .catch((err) => {
        console.log(err)
      })

      const serverURL = `${server_URL}/letsmeet/websocket`
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", res => {
            console.log('구독으로 받은 메시지 입니다.', this.authname, JSON.parse(res.body));
            var img = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
            for (var user of this.mrUserInfo) {
              if (JSON.parse(res.body).mrcUNo === user.uNo && user.uImage !== null){
                img = user.uImage
              }
            }
            this.message = {
              id: JSON.parse(res.body).mrcUNo,
              roomNo: JSON.parse(res.body).mrcMrNo,
              author: this.user[JSON.parse(res.body).mrcUNo],
              contents: JSON.parse(res.body).mrcContent,
              date: moment().format('HH:mm'),
              uImage: img
            }
            if(this.message.roomNo == this.mrNo){
              console.log("방번호가 일치합니다.")
              this.feed.push(this.message)
              console.log(this.message)
              setTimeout(function () {
                var scrollContainer = document.getElementById('window__messages__container')
                var isScrolledToBottom = scrollContainer.scrollHeight - scrollContainer.clientHeight <= scrollContainer.scrollTop + 1
                if (!isScrolledToBottom) { scrollContainer.scrollTop = scrollContainer.scrollHeight }
              }, 201)
            }
          });
          const msg = { 
          mrcUNo: this.authorId,
          mrcContent: this.message.contents,
          mrcMrNo: this.mrNo
          };
        console.log(msg)
        this.stompClient.send("/pub", JSON.stringify(msg), {});
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
        
      );        
    },
  },
}
</script>
<style lang="scss">
  .window {
      max-width: 500px !important;
      height: 500px !important;
  }
  .window__header__container {
    background: #536DFE !important;
  }
  .window .message--own .message__contents {
    color: black !important;
    background-color: #C5CAE9 !important;
  }
  .window .message--foreign .message__contents {
    background-color: #80DEEA !important;
  }
  .window__input__container .input__button {
    background-color: #536DFE !important;
    color: #FAF9FF;
  }
  .window__input__container .input__field {
    background-color: #FAF9FF !important;
  }
</style>