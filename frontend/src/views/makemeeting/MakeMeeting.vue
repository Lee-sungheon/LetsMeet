<template>
  <div>
    <MakeRoom v-show="isMake" @is_next="isNext" @update_room_title="updateRoomTitle" @update_idx="updateIdx" @upload_img="uploadImg"/>
    <MakeSchedule v-show="isMake2" @is_prev="isPrev" @update_dates="updateDates" @confirm_room="confirmRoom"/>
  </div>
</template>

<script>
import MakeRoom from "../../components/makemeeting/MakeRoom.vue";
import MakeSchedule from "../../components/makemeeting/MakeSchedule.vue";
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "MakeMeeting",
  components: {
    MakeRoom,
    MakeSchedule
  },
  data: function() {
    return {
      isMake: true,
      isMake2: false,
      room_title: '',
      room_type: '',
      dates: [],
      iFile: '',
      mrNo: '',
      type: {
        '밥': 'https://www.gyeongju.go.kr/upload/content/thumb/20200529/A42F50C69A8A4DDB94DA408C290735C1.jpg',
        '카페': 'http://www.lightingnews.net/images/theme/cafe_01.png',
        '술': 'https://img.lovepik.com/photo/50011/5863.jpg_wh860.jpg',
        '스터디': 'https://modo-phinf.pstatic.net/20180304_283/1520151276251GkP1Q_JPEG/mosaOtd1XG.jpeg?type=w720',
        '놀거리': 'https://www.travel.taipei/image/65598/1024x768',
        '관광지': 'https://post-phinf.pstatic.net/MjAxNzA1MTFfMTQg/MDAxNDk0NDgyODE4OTI2.P7H0n7pqJChBq_g42dZAwi_K16adlRxBerf26cW1Hvgg.oqVaoHWzt7D7dFhW_W62oMaoRn9TCStRJ6A9j_D7C0Yg.JPEG/%EC%84%AC.jpg?type=w1200',
      }
    }
  },
  methods: {
    isNext() {
      this.isMake = false,
      this.isMake2 = true
    },
    isPrev() {
      this.isMake = true,
      this.isMake2 = false
    },
    updateRoomTitle(data) {
      this.room_title = data
    },
    updateIdx(data) {
      this.room_type = data
    },
    updateDates(data) {
      this.dates = data
    },
    uploadImg(data) {
      this.iFile = data
    },
    confirmRoom() {
      if (this.room_title && this.room_type && this.dates.length > 0){
        var tmp_end_day = ''
        if (this.dates.length == 2) {
          tmp_end_day = this.dates[1]
        } else {
          tmp_end_day = this.dates[0]
        }
        const t = this.type
        var formData = new FormData();
        if(this.iFile != "") {
          formData.append("file", this.iFile); // 이미지 파일
        }
        // * FormData객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
        // * FormData를 로깅하려면 FormData.entries()를 이용하여 key : value 쌍을 뽑아야 한다.
        for (let key of formData.entries()) {
          console.log(`${key}`);
        }
        axios.post(`${server_URL}/letsmeet/meetingRoom/create?mrCategory=${this.room_type}&mrDateEnd=${tmp_end_day}&mrDateStart=${this.dates[0]}&mrName=${this.room_title}&mrSuperUNo=${this.$store.state.uNo}&mrImage=${t[this.room_type]}`, formData, { // 이건 로컬용이다.
          header: {
            'Content-Type': 'multipart/form-data'
          }
        })
          .then((res)=> {
            alert('방 생성이 완료되었습니다.')
            console.log(res.data)
            this.$store.state.mrNo = res.data
            const members = this.$route.params.members
            // this.$fileSelect()
            if (members) {
              var cnt = 0
              for(let mb of members) {
                if (mb.uNo !== this.$store.state.uNo){
                  const data = {
                    mruMrNo: this.$store.state.mrNo,
                    mruUNo: mb.uNo,
                  }
                  axios.post(`${server_URL}/letsmeet/meetingRoomUser/adduser`, data)
                  .then(()=> {
                    var img = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
                    const content = `${this.$store.state.uName}님이 '${this.room_title}'방에 초대하였습니다./${this.$store.state.mrNo}`
                    const title = '방 초대 알림'
                    if (this.$store.state.uImage) {
                      img = this.$store.state.uImage
                    }
                    axios.post(`${server_URL}/letsmeet/alarm/create?aContent=${content}&aRecvUNo=${mb.uNo}&aSenderImage=${img}&aTitle=${title}`)
                    .then((res) => {
                      console.log(res.data)
                    })
                    .catch((err) => {
                      console.log(err)
                    })
                    cnt ++
                    if (cnt === members.length-1) {
                      this.$router.push({name:"MeetingRoom", params:{"id":this.$store.state.mrNo}})
                    }
                  })
                  .catch(()=> {
                    cnt ++
                    console.log('못드감')
                  })
                }
              }
              this.$route.params.members = null
            } else{
              this.$router.push({ name: 'Main'})
            }
          })
          .catch((err) => {
            console.log(err)
          })
        
      }else {
        alert("데이터를 모두 입력해주세요.")
      }
    },
  },

}
</script>

<style>

</style>