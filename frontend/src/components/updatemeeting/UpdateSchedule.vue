<template>
  <div>
    <div class="pt-5 d-flex justify-center mx-5" style="border-bottom: 2px solid black">
      <h5>약속방 만들기</h5>
    </div>
    <v-container>
      <v-row class="d-flex align-center mx-0">
        <v-col
          cols="12"
          class="d-flex justify-start"
        >
          <span>약속 가능한 날짜</span>
        </v-col>
      </v-row>
    </v-container>
    
    <v-container>
      <v-col
        cols="12"
        sm="12"
      >
        <v-date-picker
          v-model="update_dates"
          range
          color="indigo accent-2"
          full-width
          no-title
          :min="today"
        ></v-date-picker>
      </v-col>
      <v-col
        cols="12"
      >
        <v-text-field
          v-model="dateRangeText"
          label="약속 기간"
          prepend-icon="mdi-calendar"
          readonly
        ></v-text-field>
      </v-col>
    </v-container>
    
    <div class="mx-10 d-flex justify-space-between">
      <v-btn
        fab
        dark
        small
        color="indigo accent-2"
        @click="is_prev"
      >
        <v-icon dark>
          mdi-chevron-double-left
        </v-icon>
      </v-btn>
      <v-dialog
        v-model="dialog"
        persistent
        max-width="290"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            fab
            dark
            small
            color="indigo accent-2"
            v-bind="attrs"
            v-on="on"
          >
            <v-icon dark>
              mdi-check-bold
            </v-icon>
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="headline">
           방 정보를 수정하시겠습니까?
          </v-card-title>
          <v-card-text></v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="green darken-1"
              text
              @click="updateRoom"
            >
              생성
            </v-btn>
            <v-btn
              color="green darken-1"
              text
              @click="dialog = false"
            >
              취소
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </div>
</template>

<script>
const axios = require('axios')
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "UpdateSchedule",
  props: {
    id: {}
  },
  data () {
    return {
      textContent: '',
      update_dates: [],
      today: new Date().toISOString().substr(0, 10),
      dialog: false,
      direct: false,
      flag: true,
      mrNo: this.id
    }
  },
  watch: {
  },
  computed: {
    dateRangeText () {
      this.dateSort()
      this.$emit('update_dates', this.update_dates)
      return this.update_dates.join(' ~ ')
    }
  },
  mounted() { 
    this.getRoomInfo()
  },
  methods: {
    getRoomInfo() {
      axios.get(`${server_URL}/letsmeet/meetingRoom/detail?mrNo=${this.mrNo}`)
      .then( (res)=> {
        console.log(res.data)
        this.update_dates.push(res.data.mrDateStart)
        this.update_dates.push(res.data.mrDateEnd)
      })
      .catch((err)=> {
        console.log(err)
      })
    },
    is_prev(){
      this.$emit('is_prev')
    },
    directSelect(){
      this.direct = !this.direct
      this.flag = false
    },
    dateSort(){
      if (this.update_dates.length == 2){
        if (this.update_dates[0] > this.update_dates[1]) {
          var tmp = this.update_dates[0]
          this.update_dates[0] = this.update_dates[1]
          this.update_dates[1] = tmp
        }
      }
    },
    updateRoom() {
      this.dialog = false
      this.$emit('update_room2')
    }
  },
}
</script>

<style>
</style>