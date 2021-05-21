<template>
  <div>
    <v-list-item>
      <v-list-item-subtitle>리뷰 보기
        (<v-icon>mdi-comment</v-icon>
        <span>: {{ items.length / 2}}</span>)
      </v-list-item-subtitle>
    </v-list-item>
    <v-list v-if="items.length > 0">
      <template v-for="(item, index) in items">
        <v-subheader
          v-if="item.header"
          :key="index"
        ></v-subheader>
        <v-divider
          v-else-if="item.divider"
          :key="index"
          :inset="item.inset"
        ></v-divider>
        <v-list-item
          v-else
          :key="index"
        >
          <v-list-item-avatar>
            <v-img :src="item.avatar"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-subtitle>{{item.title}} 
              <v-icon 
                v-if="$store.state.uNo == item.uNo" class="mb-1" color="red" @click="deleteReview(item)"
              >mdi-close</v-icon>
            </v-list-item-subtitle>
            <v-rating
              :value="item.rating"
              color="yellow darken-3"
              background-color="yellow"
              dense
              readonly
              size="1rem"
            ></v-rating>
            <div style="font-size: 0.8rem; text-color: gray;" class="ml-1">{{item.time}}</div>
            <div style="font-size: 0.9rem;" class="mt-2">{{item.subtitle}}</div>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list> 
    <v-list v-else>
     <div style="font-size: 0.9rem;" class="mt-2">리뷰가 없습니다. 첫 리뷰를 작성해주세요!</div>
    </v-list>

    <div class="text-center" v-if="items.length > 0">
      <v-pagination
        v-model="page"
        :length="len"
        color="indigo accent-2"
      ></v-pagination>
    </div>
    <v-container>
      <v-textarea
        solo
        name="input-7-3"
        label="comments"
        style="position: relative; top: 1.2rem;"
        rows="3"
        v-model="comment"
      ></v-textarea>
      <v-row class="d-flex align-center">
        <v-col cols=7 class="d-flex justify-center">
          <v-rating
            v-model="rating"
            color="yellow darken-3"
            background-color="yellow"
            dense
            icon-label="custom icon label text {0} of {1}"
          ></v-rating>
        </v-col>
        <v-col cols=5 class="d-flex justify-center">
          <v-btn
            class="ma-2"
            outlined
            color="indigo"
            @click="writeReview"
          >
            리뷰 작성
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const axios = require('axios')
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "StoreReview",
  data () {
    return {
      page: 1,
      rating: 5,
      items: [],
      len: 0,
      comment: '',
      comments: [
      ],
    }
  },
  created() {
    this.getComments()
  },
  mounted() {
    this.items = this.comments.slice((this.page-1)*10, this.page*10-1)
  },
  props: {
    place: Object
  },
  watch: {
    page() {
      this.items = this.comments.slice((this.page-1)*10, this.page*10-1)
      console.log(this.items)
    },
    comments() {
      this.items = this.comments.slice((this.page-1)*10, this.page*10-1)
    }
  },
  methods: {
    getComments() {
      axios.get(`${server_URL}/letsmeet/store/detail/review?srSNo=${this.place.sNo}`)
      .then((res)=> {
        this.comments = []
        for (var da of res.data) {
          var com = {
            avatar: da.srImg,
            title: da.srUName,
            rating: da.srScore, 
            subtitle: da.srContent,
            time: da.srTime,
            no: da.srNo,
            uNo: da.srUNo,
            sNo: da.srSNo,
          }
          this.comments.push(com)
          this.comments.push({ divider: true, inset: true })
        }
        this.len = parseInt(parseInt(this.comments.length) / 10) + 1
      })
      .catch(()=> {
        console.log('못드감')
      })
    },
    writeReview() {
      if (this.comment === '') {
        alert("내용을 입력헤주세요.")
        return
      }
      var imgUrl = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
      if (this.$store.state.uImage) {
        imgUrl = this.$store.state.uImage
      }
      axios.post(`${server_URL}/letsmeet/store/detail/review/add?srContent=${this.comment}&srImg=${imgUrl}&srSNo=${this.place.sNo}&srScore=${this.rating}&srUNo=${this.$store.state.uNo}&srUName=${this.$store.state.uName}`)
      .then((res)=> {
        console.log(res.data)
        this.getComments()
      })
      .catch(()=> {
        console.log('못드감')
      })
      this.rating= 5
      this.comment = ''
    },
    deleteReview(item){
      console.log(item)
      if(confirm("정말 삭제하시겠습니까?")){
        axios.delete(`${server_URL}/letsmeet/store/detail/review/delete?srNo=${item.no}&srSNo=${item.sNo}&srUNo=${item.uNo}`)
        .then((res)=> {
          console.log(res.data)
          this.getComments()
        })
        .catch(()=> {
          console.log('못드감')
        })
      }
    }
  },
}
</script>

<style lang="scss">
  $pagination-navigation-margin : .2rem 5px !default;
</style>