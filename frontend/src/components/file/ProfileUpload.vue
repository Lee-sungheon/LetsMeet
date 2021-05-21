<template>
  <div>
      <input v-on:change="$fileSelect()" type="file" ref="iFile" accept="image/*"/>
  </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "ProfileUpload",
  data(){
      return {
          iFile: '',
          iUserEmail: '',
      }
  },
  methods: {
      // 파일 업로드하고 확인 버튼 누르면 emit 해야함

      // 파일 첨부시 Change이벤트
      $fileSelect : function $fileSelect() {
          console.log(this.$refs);
          this.iFile = this.$refs.iFile.files[0];
          this.$executeSave();
      },
      
      // 저장
      $executeSave : function $executeSave() {
          //const url = 'letsmeet/image/profileUpload'; // 서버에 올릴땐 이거 써야한다.
      
          //Object To FormData 변환
          var formData = new FormData();
          if(this.iFile != "") {
              formData.append("iFile", this.iFile); // 이미지 파일
              formData.append("iUserEmail", this.$store.state.uEmail)
          }
      
          // * FormData객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
          // * FormData를 로깅하려면 FormData.entries()를 이용하여 key : value 쌍을 뽑아야 한다.
          for (let key of formData.entries()) {
              console.log(`${key}`);
          }
      
          // 파일 업로드시 경로, FormData, Header 설정
          // axios.post(url, formData, { // 서버에 올릴땐 이거 써야한다.
              axios.put(`${server_URL}/letsmeet/image/profileUpload`, formData, { // 이건 로컬용이다.
              header: {
                  'Content-Type': 'multipart/form-data'
              }
          }).then(
              response => {
                  if(!!response && response.status === 200) {
                      console.log('된거임?')
                      console.log(response.data.uImage)
                      this.$emit('updateImage', response.data.uImage)
                  }
                  // 업로드 된 후에 저장된 곳(유저 테이블에 있낭?)에서 데이터 받아서 emit?

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