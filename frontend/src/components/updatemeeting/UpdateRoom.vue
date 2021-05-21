<template>
<div>
    <div class="pt-5 d-flex justify-center mx-5" style="border-bottom: 2px solid black">
      <h5>약속방 수정하기</h5>
    </div>
    <v-form>
      <v-container>
        <v-row class="d-flex align-center">
          <v-col
            cols="3"
            sm="6"
            class="d-flex justify-center"
          >
            <span>방 이름</span>
          </v-col>
          <v-col
            cols="8"
            sm="6"
            class="d-flex justify-start"
          >
            <v-text-field
              single-line
              :placeholder="update_room_title"
              v-model="update_room_title"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <v-container>
      <v-row class="d-flex align-center mx-0">
        <v-col
          cols="12"
          class="d-flex justify-start"
        >
          <span>약속 유형</span>
        </v-col>
        <v-col
          cols="4"
          class="d-flex justify-start flex-column"
          v-for="(image, idx) in images"
          :key="idx"
        >
          <b-img v-if="idx!==update_image_idx" v-bind="mainProps" rounded="circle" alt="Circle image"
            :src="images[idx].src" style="border: 4px solid black; width: 100%;" 
            @click="selectImage(idx)"></b-img>
          <b-img v-else v-bind="mainProps" rounded="circle" alt="Circle image"
            :src="images[idx].src" style="border: 4px solid #536DFE; width: 100%;" 
            @click="selectImage(idx)"></b-img>
          <span class="text-center">{{ images[idx].title }}</span>
        </v-col>
      </v-row>
    </v-container>
    <v-form>
      <v-container>
        <v-row class="d-flex align-center">
          <v-col
            cols="3"
            sm="6"
            class="d-flex justify-center"
          >
            <span>사진</span>
          </v-col>
          <v-col
            cols="8"
            sm="6"
            class="d-flex justify-start"
          >
            <input v-on:change="$fileSelect()" type="file" ref="iFile" accept="image/*" />
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <div class="mx-10 mb-15 d-flex justify-end">
      <v-btn
        fab
        dark
        small
        color="indigo accent-2"
        @click="is_next"
      >
        <v-icon dark>
          mdi-chevron-double-right
        </v-icon>
      </v-btn>
    </div>
  </div>
</template> 

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
export default {
    name: 'UpdateRoom',
    props: {
      id: {}
    },
    data: function () {
      return {
        mrNo: this.id,
        update_room_title: '',
        update_image_idx: -1,
        iFile: '',
        images: [
        {
          title:'밥',
          src:'data:image/png;base64,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', 
        },
        {
          title: '카페',
          src: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs2HtlLgPMfIbZ3X7J48jCGkk7wcMdUUwOJw&usqp=CAU',
        },
        {
          title: '술',
          src: 'data:image/png;base64,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',
        },
        {
          title: '스터디',
          src: 'data:image/png;base64,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',
        },
        {
          title: '놀거리',
          src: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjhe4RwMi_jvyg6TVuVdFqiHZJ7UGn-yKQqQ&usqp=CAU',
        },
        {
          title: '관광지',
          src: 'https://en.pimg.jp/058/378/440/1/58378440.jpg'
        }
      ],
      mainProps: { width: '100%', height: '100%'},
      }
    },
    created () {
      this.getRoomInfo()
    },
    watch: {
        update_room_title() {
          this.$emit('update_room_title', this.update_room_title)
        },
        update_image_idx() {
          this.$emit('update_idx', this.images[this.update_image_idx].title)
        }
      },
    methods: {
      getRoomInfo() {
        axios.get(`${server_URL}/letsmeet/meetingRoom/detail?mrNo=${this.mrNo}`)
        .then( (res)=> {
          console.log(res.data)
          this.update_room_title = res.data.mrName
          this.images.forEach((image, index) => {
            if (image.title === res.data.mrCategory) {
              this.update_image_idx = index
            }
          });
          console.log(this.update_image_idx)
        })

        .catch((err)=> {
          console.log(err)
        })
      },
      is_next(){
        this.$emit('is_next')
      },
      selectImage(idx){
        this.update_image_idx = idx
      },
      $fileSelect : function $fileSelect() {
      console.log(this.$refs);
      this.iFile = this.$refs.iFile.files[0];
      this.$emit("upload_img", this.iFile)
    },
    }

}
</script>