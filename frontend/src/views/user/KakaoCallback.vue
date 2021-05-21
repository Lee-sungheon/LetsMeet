<template>
    <div>
        <div id="naverIdLogin" style="display: none">login...</div>
    </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
import { getKakaoToken, getKakaoUserInfo } from "@/services/login";
export default {
    name: 'KakaoCallback',
    created() {
        if (this.$route.query.code) {
            this.setKakaoToken();
        }
    },
    data: () => {
        return  {
          user: {
          uEmail : '',
          uPassword: '',
          uName: '',
        }
      }
    },
    methods: {
        async setKakaoToken () {
            console.log('카카오 인증 코드', this.$route.query.code);
            const { data } = await getKakaoToken(this.$route.query.code);
            if (data.error) {
                alert('카카오톡 로그인 오류입니다.');
                this.$router.replace('/login');
                return;
            }
            window.Kakao.Auth.setAccessToken(data.access_token);
            this.$cookies.set('access-token', data.access_token, '1d');
            this.$cookies.set('refresh-token', data.refresh_token, '1d');
            await this.setUserInfo();
            this.$router.replace('/');
        },
        async setUserInfo () {
            let user_data = await getKakaoUserInfo();
            // console.log(user_data)
            // 여기에 백으로 계정 정보 넘겨주면 된다.
            this.user.uEmail = user_data.kakao_account.email+'_'+user_data.id;
            this.user.uPassword = 'kakaoPassword';
            this.user.uName = user_data.properties.nickname;
            axios.post(`${server_URL}/letsmeet/auth/kakao/callback`, this.user)
              .then((res) => {
              // console.log(res);
              let token = res.data['auth-token']
              if (token === undefined) {
                alert('비밀번호가 틀렸습니다.')}
              else {
                alert('로그인 되었습니다.')
                // context.commit('SET_USER_AUTH_DATA', res.data)
                localStorage.setItem('auth-token', token)
                // axios default 헤더에 현재 token 적재
                axios.defaults.headers.common['auth-token'] = window.localStorage.getItem("auth-token");
                this.$store.commit('SET_USER_AUTH_DATA', res.data)
                this.$router.push({ name: 'Main'})
                }
              })
            .catch((err) => {
              console.log(err)
              alert('로그인에 실패하셨습니다.')
            })
        },
        submit () {
            if (this.$refs.form.validate()) {
                // sprin url 받기
                axios.post(`${server_URL}/letsmeet/auth/kakao/callback`, this.user ).then(()=> {
                    alert('카카오 소셜 로그인 완료되었습니다.')
                    this.$router.push({ name: 'Login'});
                })
                .catch(() => {
                    alert('카카오 소셜 로그인에 실패하셨습니다.')
                })
            }
            else {
                console.log('카카오 소셜 로그인에 실패')
            }
        },
    }
}
</script>