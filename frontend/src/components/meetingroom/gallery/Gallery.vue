<template>
  <mdb-container>
    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-file-upload</v-icon><v-subheader class="pl-2">추억 올리기</v-subheader>
    </div>
    <mdb-row>
      <mdb-col md="12" class="d-flex justify-content-center mb-5">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text" id="inputGroupFileAddon01" ><v-icon>mdi-camera</v-icon></span>
          </div>
          <div class="custom-file">
            <input type="file" class="custom-file-input" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01" 
              v-on:change="$fileSelect()" ref="iFile" accept="image/*" />
            <label class="custom-file-label" for="inputGroupFile01">Choose image</label>
          </div>
        </div>
      </mdb-col>
    </mdb-row>
    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-image-multiple</v-icon><v-subheader class="pl-2">추억 공유</v-subheader>
    </div>
    <div class="gallery" id="gallery">
      <transition v-for="(image, idx) in images" :key="idx">
        <div class="mb-3 pics all 2 animation" @click="visible=false, openGallery(idx)">
          <img class="img-fluid" :src="image.gName" alt="Card image cap">
        </div>
      </transition>
    </div>
    <LightBox 
      :media="imgs"
      :startAt="index"
      v-if="visible"
      @click.native="visible=false"
    ></LightBox>
  </mdb-container>
</template>


<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
import { mdbContainer, mdbRow, mdbCol } from "mdbvue";
import LightBox from 'vue-it-bigger'
import('vue-it-bigger/dist/vue-it-bigger.min.css')

export default {
  name: "Gallery",
  data() {
    return {
      iFile: '',
      iUserEmail: '',
      images: [],
      visible: false,
      index: 0,
      imgs: [],
    }
  },
  props: {
    mrNo: String,
  },
  components: {
    mdbContainer,
    mdbRow,
    mdbCol,
    LightBox,
  },
  mounted() {
    this.getImages()
  },
  methods: {
    getImages() {
      this.imgs = []
      this. images = []
      axios.get(`${server_URL}/letsmeet/memory/detail?gMrNo=${this.mrNo}`)
      .then((res) => {
        console.log(res.data)
        this.images = res.data
        for (var image of this.images) {
          var media = {
            thumb: image.gName,
            src: image.gName,
            caption: '',
            srcset: '',
          }
          this.imgs.push(media)
        }
      }).catch(error => {
        console.log(error);
        //commonUtils.$alertUncatchedError(error);
      });
    },
    $fileSelect : function $fileSelect() {
      console.log(this.$refs);
      this.iFile = this.$refs.iFile.files[0];
      if (confirm("사진을 업로드 하시겠습니까?")){
        this.$executeSave();
      }
    },
    $executeSave : function $executeSave() {
      //const url = 'letsmeet/image/galleryImageUpload'; // 서버에 올릴땐 이거 써야한다.
  
      //Object To FormData 변환
      var formData = new FormData();
      if(this.iFile != "") {
          formData.append("iFile", this.iFile) // 이미지 파일
          formData.append("gMrNo", this.mrNo) // 사진
          formData.append("gUNo", this.$store.state.uNo)
      }
      // * FormData객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
      // * FormData를 로깅하려면 FormData.entries()를 이용하여 key : value 쌍을 뽑아야 한다.
      for (let key of formData.entries()) {
          console.log(`${key}`);
      }
      // 파일 업로드시 경로, FormData, Header 설정
      // axios.post(url, formData, { // 서버에 올릴땐 이거 써야한다.
      axios.post(`${server_URL}/letsmeet/memory/add?gMrNo=${this.mrNo}&gUNo=${this.$store.state.uNo}`, formData, { // 이건 로컬용이다.
        header: {
            'Content-Type': 'multipart/form-data'
        }
      }).then(
          response => {
              if(!!response && response.status === 200) {
                  alert('사진이 업로드 되었습니다.')
                  this.getImages()
              }
          }
      ).catch(error => {
          console.log(error);
          //commonUtils.$alertUncatchedError(error);
      });
    },
    toggleLightBox() {
      console.log("toggle")
      this.visible = false
    },
    openGallery(index) {
      this.index = index || 0
      this.visible = true
    }
  },
}
</script>

<style>
  .gallery {
    -webkit-column-count: 3;
    -moz-column-count: 3;
    column-count: 3;
    -webkit-column-width: 33%;
    -moz-column-width: 33%;
    column-width: 33%; }
  .gallery .pics {
    -webkit-transition: all 350ms ease;
    transition: all 350ms ease; }
  .gallery .animation {
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    transform: scale(1); }

  @media (max-width: 450px) {
    .gallery {
      -webkit-column-count: 2;
      -moz-column-count: 2;
      column-count: 2;
      -webkit-column-width: 50%;
      -moz-column-width: 50%;
      column-width: 50%;
    }
  }

  @media (max-width: 400px) {
    .btn.filter {
      padding-left: 1.1rem;
      padding-right: 1.1rem;
    }
  }
</style>