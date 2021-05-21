<template>
  <div>
      <input v-on:change="$fileSelect()" type="file" ref="iFile" />
  </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "Upload",
  data(){
      return {
          iFile: '',
          iName: '',
          imgUserName: '',
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
          //const url = 'letsmeet/image/upload';
      
          //Object To FormData 변환
          var formData = new FormData();
          if(this.iFile != "") {
              formData.append("iFile", this.iFile); // 이미지 파일
              formData.append("iUserName", this.$store.state.uEmail)
          }
      
          // * FormData객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
          // * FormData를 로깅하려면 FormData.entries()를 이용하여 key : value 쌍을 뽑아야 한다.
          for (let key of formData.entries()) {
              console.log(`${key}`);
          }
      
          // 파일 업로드시 경로, FormData, Header 설정
          //axios.post(url, formData, {
              axios.post(`${server_URL}/letsmeet/image/upload`, formData, {
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