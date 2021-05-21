<template>
    <div>
        <v-card style="height: 100%;" class="mr-1">
          <v-img
          class= 'd-flex align-end'
          :src="place.sImage"
          height="150"
          >
          </v-img>
          <v-row>
            <v-col>
            <v-card-title style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                {{place.sName}}
            </v-card-title>
            </v-col>
            <v-col>
              <v-card-title class="d-flex justify-end">
                <v-icon color="red" @click="unlikeStore(place.sNo)">mdi-heart</v-icon>
              </v-card-title>
            </v-col>
          </v-row>
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
          </v-card-actions>
        </v-card>

        <div v-if="isDetail" class="pt-3">
          <MypageLikePlaceDetail :place='place' />
        </div>
    </div>
</template>
<script>
const axios = require('axios')
const server_URL = process.env.VUE_APP_SERVER_URL
import MypageLikePlaceDetail from '../mypage/MypageLikePlaceDetail'
export default {
    name: 'MypageLikePlaceItem',
    components: {
      MypageLikePlaceDetail
    },
    props: {
      place: Object,
    },
    data: ()=> {
      return {
        isDetail: false,
        heart: 'mdi-heart',
      }
    },
    methods: {
      goDetail () {
        this.isDetail = !this.isDetail
      },
      unlikeStore(storenumber) {
          if (this.heart === "mdi-heart") {
            axios.delete(`${server_URL}/letsmeet/mypage/likestore/delete?lsSNo=${storenumber}&lsUNo=${this.$store.state.uNo}`)
            .then((res)=> {
              console.log(res.data)
              this.heart = "mdi-heart-outline"
              alert('해당 장소를 찜 목록에서 삭제하시겠습니까?')
              this.$emit('refresh')
            })
            .catch((err)=> {
              console.log(err)
            })
          }
      },
    }
}
</script>