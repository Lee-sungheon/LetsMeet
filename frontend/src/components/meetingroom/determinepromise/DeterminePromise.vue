<template>
  <v-container>
    <div>
      <MyPromise :roomInfo="roomInfo" :mrUserInfo="mrUserInfo" @refresh="refresh"/>
    </div>
    <hr>
    <v-subheader>약속 가능 날짜</v-subheader>
    <v-subheader v-if="isFinal && roomInfo.mrSuperUNo == $store.state.uNo">
      <v-icon color="red">mdi-alert-box-outline</v-icon>약속 날짜를 최종 선택해주세요!
    </v-subheader>
    <v-date-picker
      v-model="dates"
      @click.native="pickDate"
      :event-color="date => 'date[9] % 2' ? 'red' : 'yellow'"
      :events="functionEvents"
      full-width
      no-title
      color="#536DFE"
      :min="min"
      :max="max"
      :picker-date="min"
    ></v-date-picker>
    <v-container class="ml-2 text-start">
      <v-icon color="green">mdi-circle-medium</v-icon> : 모두 가능 <br>
      <v-icon color="yellow">mdi-circle-medium</v-icon> : 일부 가능 <br>
      <v-icon color="red">mdi-circle-medium</v-icon> : 모두 불가능
      <div>
        <br><br>
        <div class="mb-1"><v-icon color="indigo deep-2">mdi-vote</v-icon>가장 많이 되는 날</div>
        <h6 v-for="(value, idx) in Object.keys(voteDates)" :key="idx" >
          <span v-if="idx <= 3"> 
            - {{ Object.keys(voteDates)[idx] }} : {{ voteDates[Object.keys(voteDates)[idx]] }}명
          </span>
        </h6>
      </div>
    </v-container>

    <hr>
    <v-subheader>출발 장소</v-subheader>
    <v-container v-if="userDeparture" class="text-start" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
      <span v-for="(value, idx) in userDeparture" :key="idx"> 
        <v-icon color="indigo deep-2">
          mdi-flag-checkered</v-icon>{{ value[1] }} : {{ value[2] }}<br>
      </span>
    </v-container><hr>
    <v-subheader>중간 지점</v-subheader>
    <MiddlePoint :mrUserInfo="mrUserInfo" :roomInfo="roomInfo" @rec_place="getPlace"/>
  </v-container>
</template>



<script>
import MyPromise from "./MyPromise.vue";
import MiddlePoint from "./MiddlePoint.vue";
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "DeterminePromise",
  components: {
    MyPromise,
    MiddlePoint,
  },
  props: {
    roomInfo: Object,
    mrUserInfo: Array,
  },
  data() {
    return {
      arrayEvents: null,
      dates: '',
      userDeparture: [],
      min: this.roomInfo.mrDateStart,
      max: this.roomInfo.mrDateEnd,
      availDates: {},
      voteDates: {},
      userInfo: [],
      isFinal: false,
    }
  },
  mounted () {
    this.getUserDeparture()
    this.min = this.roomInfo.mrDateStart
    this.max = this.roomInfo.mrDateEnd
    this.userInfo = this.mrUserInfo
    this.getAvailableDates()
    this.getIsFinal()
  },
  watch: {
    roomInfo() {
      this.min = this.roomInfo.mrDateStart
      this.max = this.roomInfo.mrDateEnd
    }
  },
  methods: {
    functionEvents (date) {
      const keys = Object.keys(this.availDates)
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i]
        if (key === date){
          return this.availDates[key]
        }
      }
      if (this.min <= date && date <= this.max){
        return ['red']
      }
    },
    departure(id, name, Lng, Lat) {
      axios.get(`https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${Lng}&y=${Lat}&input_coord=WGS84`, {
        headers: {
          Authorization: `KakaoAK 005fbdb435b40b9acf0eabc1b2010e7b`
        }
      })
      .then((res)=> {
        var tmp = res.data.documents[0].address.address_name
        this.userDeparture.push([id, name, tmp])
      })
      .catch((err) => {
        console.log(err)
      })
    },
    getUserDeparture() {
      this.userDeparture = []
      var li = this.mrUserInfo
      for(var i of li){
          if(i.mruUserLat !== null){
            this.departure(i.uNo, i.uName, i.mruUserLng, i.mruUserLat)
          }
      }
    },
    getAvailableDates() {
      this.availDates = {}
      this.voteDates = {}
      var li = this.userInfo
      var cnt = 0
      for(var i of li){
        if (i.mruUserDates) {
          cnt ++
          var tmp_li = i.mruUserDates.split('/')
          for(var j of tmp_li){
            if (!this.availDates[j]){
              this.availDates[j] = 1
            } else {
              this.availDates[j] += 1
            }
          }
          this.voteDates = JSON.parse(JSON.stringify(this.availDates))
        }
      }
      const keys = Object.keys(this.availDates)
      for (var k = 0; k < keys.length; k++) {
        const key = keys[k]
        if (this.availDates[key] === cnt){
          this.availDates[key] = ['green']
        } else if (0 < this.availDates[key] < cnt){
          this.availDates[key] = ['yellow']
        } else {
          this.availDates[key] = ['red']
        }
      }
      this.functionEvents()
    },
    refresh(ref_data) {
      console.log(ref_data)
      var li = this.mrUserInfo
      for(var i=0; i < li.length; i++){
          if(li[i].uNo === this.$store.state.uNo){
            this.userInfo[i].mruUserDates = ref_data.mruUserDates
            break
          }
      }
      this.getAvailableDates()
      this.$emit('refresh')
      this.getIsFinal()
      this.getUserDeparture()
    },
    getPlace(data) {
      this.$emit('rec_place', data)
    },
    getIsFinal() {
      this.isFinal = true
      var li = this.mrUserInfo
      for(var i of li){
        if(i.mruUserLat === null){
          this.isFinal = false
        }
      }
    },
    pickDate() {
      if (this.isFinal && this.roomInfo.mrSuperUNo == this.$store.state.uNo) {
        if(confirm(`${this.dates} 로 약속날짜를 확정하시겠습니까?`)){
          const data = {
            mrDate: this.dates,
            mrNo: this.roomInfo.mrNo
          }
          axios.put(`${server_URL}/letsmeet/meetingRoom/finaldate`, data)
          .then((res)=> {
            console.log(res.data)
            alert("약속 날짜를 확정하였습니다.")
            this.$emit('refresh3')
          })
          .catch((err) => {
            console.log(err)
          })
        }
      }
    }
  },
}
</script>

<style>

</style>