import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import createPersistedState from 'vuex-persistedstate'
export const store = new Vuex.Store({
  plugins: [
    // storage 에 넣어준당
    createPersistedState()
  ],
  state: {
    uEmail: null,
    uName: null,
    isLogin: false,
    // default 이미지 값
    uImage: null,
    uNo: null,
    uSalt: null,
    mrNo: null,
  },
  mutations: {
    SET_USER_AUTH_DATA(state, payload){
      state.isLogin = true;
      state.uEmail = payload['uEmail']
      state.uName = payload['uName']
      state.uNo = payload['uNo']
      if (!payload['uImage']) {
        state.uImage = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
      } else{
        state.uImage = payload['uImage']
      }
      state.uSalt = payload['uSalt']
      // console.log(state.uSalt)
    },
    SET_USER_AUTH_DATA_LOGOUT(state) {
      state.isLogin = false
      state.email = null
      state.name = null
      state.uNo = null
      state.uImage= null
      state.uSalt = null
    },
    SET_USER_NAME(state, uName) {
      state.uName = uName
    },
    SET_USER_IMAGE(state, uImage) {
      state.uImage = uImage
    }

  },
  actions: {
    FETCH_USER_NAME(context, uName){
      context.commit('SET_USER_NAME', uName)
    },
    FETCH_USER_IMAGE(context, uImage){
      context.commit('SET_USER_IMAGE', uImage) 
    },
    LOGOUT(context) {
      context.commit('SET_USER_AUTH_DATA_LOGOUT')
      localStorage.removeItem('auth-token')
    },
    DELETE_ACCOUNT(context) {
      context.commit('SET_USER_AUTH_DATA_LOGOUT')
      localStorage.removeItem('auth-token')
    }
    }
});