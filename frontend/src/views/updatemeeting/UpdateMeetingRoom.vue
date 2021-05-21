<template>
  <div>
    <UpdateRoom :id='id' v-show="isMake" @is_next="isNext" @update_room_title="updateRoomTitle" @update_idx="updateIdx" @upload_img="uploadImg"/>
    <!-- <UpdateRoom v-show="isMake" @is_next="isNext" @update_room_title="updateRoomTitle" @update_idx="updateIdx" @upload_img="uploadImg"/> -->
    <UpdateSchedule :id='id' v-show="isMake2" @is_prev="isPrev" @update_dates="updateDates" @update_room2="updateRoom2"/>
  </div>
</template>
<script>
import UpdateRoom from "../../components/updatemeeting/UpdateRoom.vue";
import UpdateSchedule from "../../components/updatemeeting/UpdateSchedule.vue";

const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "UpdateMeetingRoom",
  components: {
    UpdateRoom,
    UpdateSchedule
  },
  props: {
      id: {}
  },
  data: function() {
    return {
      isMake: true,
      isMake2: false,
      update_room_title: '',
      update_room_type: '',
      update_dates: [],
      iFile: '',
      mrNo: this.id,
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
      this.update_room_title = data
    },
    updateIdx(data) {
      this.update_room_type = data
    },
    updateDates(data) {
      this.update_dates = data
    },
    uploadImg(data) {
      this.iFile = data
    },
    updateRoom2() {
      if (this.update_room_title && this.update_room_type && this.update_dates.length > 0){
        var tmp_end_day = ''
        if (this.update_dates.length == 2) {
          tmp_end_day = this.update_dates[1]
        } else {
          tmp_end_day = this.update_dates[0]
        }
        const t = this.type
        var formData = new FormData();
        if(this.iFile != "") {
          formData.append("file", this.iFile);
        }
        for (let key of formData.entries()) {
          console.log(`${key}`);
        }
        axios.post(`${server_URL}/letsmeet/meetingRoom/edit?mrCategory=${this.update_room_type}&mrDateEnd=${tmp_end_day}&mrDateStart=${this.update_dates[0]}&mrName=${this.update_room_title}&mrNo=${this.mrNo}&mrImage=${t[this.update_room_type]}`, formData, { // 이건 로컬용이다.
          header: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(()=> {
          alert('방 정보가 수정되었습니다.')
          this.$router.push({ name: 'Main'})
          this.$store.state.mrNo = this.mrNo
        })
        .catch(() => {
          alert('방 정보 수정에 실패하셨습니다.')
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