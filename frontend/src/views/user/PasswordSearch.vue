<template>
    <div>
      <v-form ref='form'>
        <v-card-title>
          비밀번호 찾기
        </v-card-title>
        <v-card-text>
          <v-text-field
          ref="email"
          v-model="user.uEmail"
          label= "이메일"
          prepend-icon="email"
          >

          </v-text-field>
        </v-card-text>
      </v-form>
    </div>
</template>
<script>
export default {
    name: 'PasswordSearch',
    data: () => {
      return {
        user: {
          uEmail: '',
          
        }
      }
    }
}
</script>