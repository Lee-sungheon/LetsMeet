<template>
  <div>
    <v-card style="height: 100%;" class="mr-1">
      <v-img
      class= 'd-flex align-end'
      :src="place.sImage"
      height="150"
      >
      </v-img>
      <v-card-title style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
          {{place.sName}}
      </v-card-title>
      <v-card-text style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
          {{place.sRoadAddress}}
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between m-0 pr-0">
        <v-btn
          color="indigo accent-2"
          text
          @click="goDetail"
        >
          상세보기
        </v-btn>
        <v-btn 
          color="pink accent-4"
          text
          @click="pickPlace"
        >
          장소확정
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "RecommendItem",
  props: {
    place: Object,
    roomInfo: Object,
  },
  methods: {
    goDetail() {
      this.$emit('go_detail', this.place)
    },
    pickPlace() {
      if (this.roomInfo.mrSuperUNo == this.$store.state.uNo) {
        if(confirm(`${this.place.sName} 로 약속장소를 확정하시겠습니까?`)){
          const data = {
            mrPlace: this.place.sNo,
            mrNo: this.$route.params.id
          }
          axios.put(`${server_URL}/letsmeet/meetingRoom/finalplace`, data)
          .then((res)=> {
            console.log(res.data)
            alert("약속 장소를 확정하였습니다.")
            this.$emit('refresh')
          })
          .catch((err) => {
            console.log(err)
          })
        }
      }else {
        alert("방장만 장소를 확정할 수 있습니다.")
      }
    }
  }
}
</script>

<style>

</style>