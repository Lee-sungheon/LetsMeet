<template>
  <div>
      <input v-on:change="$fileSelect()" type="file" ref="iFile" />
  </div>
</template>

<script>
const axios = require('axios');

export default {
  name: "GalleryImageUpload",
  data(){
      return {
          iFile: '',
          gMrNo: '', // 사진첩 번호
      }
  },
  methods: {
      // 파일 첨부시 Change이벤트
      $fileSelect : function $fileSelect() {
          console.log(this.$refs);
          this.iFile = this.$refs.iFile.files[0];
          this.$executeSave();
      },
      
      // 저장
      $executeSave : function $executeSave() {
          //const url = 'letsmeet/image/galleryImageUpload'; // 서버에 올릴땐 이거 써야한다.
      
          //Object To FormData 변환
          var formData = new FormData();
          if(this.iFile != "") {
              formData.append("iFile", this.iFile); // 이미지 파일
              formData.append("gMrNo", this.$store.state.uEmail) // 사진첩 번호
          }
      
          // * FormData객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
          // * FormData를 로깅하려면 FormData.entries()를 이용하여 key : value 쌍을 뽑아야 한다.
          for (let key of formData.entries()) {
              console.log(`${key}`);
          }
      
          // 파일 업로드시 경로, FormData, Header 설정
          // axios.post(url, formData, { // 서버에 올릴땐 이거 써야한다.
              axios.post('http://localhost:8000/letsmeet/image/galleryImageUpload', formData, { // 이건 로컬용이다.
              header: {
                  'Content-Type': 'multipart/form-data'
              }
          }).then(
              response => {
                  if(!!response && response.status === 200) {
                      //commonUtils.$alert('업로드 완료');
                  }
              }
          ).catch(error => {
              console.log(error);
              //commonUtils.$alertUncatchedError(error);
          });
      },
  }

}
</script>

<style>

</style>