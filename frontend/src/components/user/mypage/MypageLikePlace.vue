<template>
  <v-container>
      <div v-for="(place, i) in myLikePlaceDetail" :key="i" class="pb-3">
        <MypageLikePlaceItem :place ='place' @refresh='refresh'/>
      </div>
  </v-container>
</template>

<script>
import MypageLikePlaceItem from '../mypage/MypageLikePlaceItem'

const axios = require('axios')
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
    name: 'MypageLikePlace',
    components: {
      MypageLikePlaceItem,
    },
    data: () => {
        return {
            myLikePlaceDetail: [],
            myLikePlaceNum: [],
            place: null,
            isDetail: false
        }
    },
    mounted() {
        this.getLikeStore()
    },
    methods: {
        getLikeStore () {
            this.myLikePlaceDetail = [],
            this.myLikePlaceNum = [],
            axios.get(`${server_URL}/letsmeet/mypage/likestore?uNo=${this.$store.state.uNo}`)
            .then((res)=> {
                // 가게 상세 조회 detail db 만들어져야함
                const likeplace = res.data
                likeplace.forEach(el => {
                    axios.get(`${server_URL}/letsmeet/map/sno?sno=${el.lsSNo}`)
                    .then((res)=> {
                        this.myLikePlaceDetail.push(res.data)
                    })
                });
                console.log(this.myLikePlaceDetail)
            })
            .catch(()=> {
                console.log('안됨/')
            })
        },
      refresh() {
        this.getLikeStore()
      }
    }
}
</script>