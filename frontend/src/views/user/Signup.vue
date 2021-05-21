<template>
  <div>
    <v-form ref='form'>
      <v-card-title>
        회원가입
      </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols='10' class="p-0">
                <v-text-field
                  ref='email'
                  v-model='user.uEmail'
                  label ='이메일'
                  prepend-icon="email"
                  :rules="[rules.required, rules.email, dodo2]"
                  required
                  >
                </v-text-field>
              </v-col>
            
            <!-- 이메일 인증 팝업창 -->
            <v-col cols='2' class="d-flex align-center p-0">
              <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="400"
                >
                  <template v-slot:activator="{ on, attrs }">
                      <v-btn class="pt-0 px-1" v-bind="attrs" v-on="on" text :color="auth_button_color"> 인증 </v-btn>                    
                  </template>
  
                  <v-card>
                    
                    <v-container class="pt-3">
                      <v-row>
                        <v-col cols='10' class="px-5 py-4">
                          <v-text-field
                            label="중복 확인을 해주세요."
                            v-model='user.uEmail'
                            persistent-hint
                          ></v-text-field>
                          
                          <!-- <v-text-field class="px-4 pt-0 pr-0" :placeholder="this.user.uEmail" v-model="authCode" @keypress.enter="authCodeCheck">
                          </v-text-field> -->
                        </v-col>
                        <v-col cols='2' class="d-flex pl-0 align-center">
                          <v-btn
                            x-small
                            outlined
                            fab
                            :color="button_color"
                          >
                            <v-icon @click='emailDoubleCheck'>mdi-check</v-icon>
                          </v-btn>
                        </v-col>
                      </v-row>
                        <!-- <div class="px-5 pt-0 text-end" @click ="sendCode(user.uEmail)" style="font-size:14px; color:#546E7A;">
                          인증코드 보내기
                        </div> -->

                      <v-row>
                        <v-col cols='10' class="px-5 pb-2">
                          <v-text-field
                            label="인증 번호를 입력해주세요."
                            v-model='authCode'
                            persistent-hint
                          ></v-text-field>
                        </v-col>
                        <v-col cols='2' class='d-flex pl-0 align-center'>
                          <v-btn
                            x-small
                            outlined
                            fab
                            color="blue-grey lighten-1"
                            >
                            <v-icon @click='authCodeCheck'>mdi-send</v-icon>
                          </v-btn>
                        </v-col>
                      </v-row>
                     </v-container>

                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="indigo accent-2"
                        text
                        @click="disagree"
                      >
                      확인
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-row>
          </v-container>
            
            
            
            
            <v-text-field
              ref='password'
              v-model="user.uPassword"
              label="비밀번호"
              :rules="[rules.required]"
              :type="showPassword ? 'text' : 'password'"
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword = !showPassword"
              prepend-icon="mdi-lock"

            >
            </v-text-field>
            
            <v-text-field
              ref="passwordConfirm"
              v-model="passwordConfirm"
              :rules="[rules.required, passwordCheck]"
              label="비밀번호 확인"
              :type="showPassword2 ? 'text' : 'password'"
              :append-icon="showPassword2 ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword2 = !showPassword2"
              prepend-icon="mdi-lock-alert"
            >
            </v-text-field>

            <v-text-field
              ref='name'
              v-model="user.uName"
              required
              label="이름"
              :rules="[rules.required]"
              prepend-icon="mdi-account-circle"
              type='text'
              @keyup.enter='submit'
            >
            </v-text-field>

            <!-- <v-text-field
              type ='date'
              value ='1950-01-01'
              min = '1950-01-01'
              max = '2010-12-31'
              ref= 'birthdate'
              v-model="user.birthdate"
              :rules = "[rules.required]"
              label='생년원일'
              prepend-icon="mdi-calendar-range"
            >
            </v-text-field>

            <v-text-field
              type='tel'
              placeholder="010-1234-5678"
              :rules="[rules.phone]"
              ref='phone'
              v-model="user.phone"
              label='전화번호'
              prepend-icon="phone"
            >
            </v-text-field> -->
            
          </v-card-text>

            <v-card-actions>
                <v-btn color="indigo accent-2" class="text-white" rounded style='width:100%' @click="submit">회원가입</v-btn>
            </v-card-actions>
          <v-card-actions class="pt-3 px-3">
              <router-link class="ro" :to="{ name: 'Login' }"><v-icon large color="indigo accent-2">mdi-arrow-left-circle</v-icon></router-link>
          </v-card-actions>
    </v-form>
  </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
export default {
    name: 'Signup',
    data: () => {
      return  {
        user: {
          uEmail : '',
          uPassword: '',
          uName: '',
        },
        dialog: false,
        passwordConfirm: '',
        showPassword: false,
        showPassword2: false,
        SignupFormHasError: false,
        authCodeIsRight: false,
        authCode: '',
        button_color: 'blue-grey lighten-1',
        emailNotDup : false,
        auth_button_color: 'blue-grey lighten-1',
        dup2: false,  
        rules: {
          // 8자 이상으로
            required: value => !!value, 
            phone: value => {
              const pattern = /[0-9]{3}-[0-9]{4}-[0-9]{4}/
              return pattern.test(value) || '휴대폰번호 형식이 아닙니다.'
            },
            // counter: value => value.length <= 20 || '최대 20자까지 가능합니다.',
            email: value => {
              return pattern.test(value) || '이메일 형식이 아닙니다.'
              },
        },
      }
    },
    computed: {
        dodo2 () {
          return this.dup2 === true || '인증체크가 필요합니다.'
        },
        passwordCheck () { 
          return () => (this.user.uPassword === this.passwordConfirm) || '비밀번호가 일치하지 않습니다.'
          // return () => (this.user.uPassword === this.passwordConfirm) || '비밀번호가 일치하지 않습니다.'
        },
    },
    methods: {
      //이메일 중복 체크
        emailDoubleCheck() {
          if (pattern.test(this.user.uEmail)) {
            axios.get(`${server_URL}/letsmeet/user/checkemail?email=${this.user.uEmail}`)
            .then((res)=> {
              if (res.data === true) {
                // 사용가능한 아이디
                this.sendCode(this.user.uEmail)
                this.emailNotDup = true
              } else {
                alert('중복된 아이디입니다!')
                this.emailNotDup = false
              }
            })
            .catch((error)=> {
              console.log(error+'안됨')
            })
          } else {
            return false
          }
        }, 

      // 회원가입 제출
      submit () {
        if (this.$refs.form.validate()) {
          // sprin url 받기
          const uImage = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
          axios.post(`${server_URL}/letsmeet/user/join?uEmail=${this.user.uEmail}&uName=${this.user.uName}&uPassword=${this.user.uPassword}&uImage=${uImage}`)
          
            .then(()=> {
              alert('회원가입 완료되었습니다.')
              this.$router.push({ name: 'Login'});
            })
            .catch(() => {
              alert('회원가입에 실패하셨습니다.')
            })
        }
        else {
          console.log('정보가 다 안채워짐')
        }

      },
      // 인증코드 보내기
      sendCode (email) {
        axios.post(`${server_URL}/letsmeet/user/join/service/mail?uEmail=${email}`)
        .then(() => {
          console.log('코드 전송 성공')
          this.button_color = 'indigo accent-2'
          alert('인증 번호가 발송되었습니다. 확인해주세요.')
        })
        .catch(() => {
          console.log('코드 전송 실패 ')
        })
      },

      // 인증이메일 코드가 맞는지 확인
    authCodeCheck () { 
      if (this.authCode.length > 0) {

        axios.post(`${server_URL}/letsmeet/user/join/service/verifyCode?eConfirm=${this.authCode}&eEmail=${this.user.uEmail}`)
        .then((res) => {
          console.log(res)
          if (res.data === '코드 인증 성공'){
            this.authCodeIsRight = true
            if (this.emailNotDup === true && this.authCodeIsRight === true) {
              this.dialog = false
              this.dup2 = true
              this.auth_button_color = 'indigo accent-2'
            } else {
              this.dialog = true
              console.log('????')
            }
          }
          else {
            this.authCodeIsRight = false
            this.dialog = true
            alert('인증 코드가 맞지 않습니다.')
          }
          this.authCode = ''
        })
        .catch(() => {
          console.log('불일치')
        })

      }
    },
    disagree() {
      this.dup2 = false
      this.dialog = false
      this.authCode = ''
    }
  }
}

</script>