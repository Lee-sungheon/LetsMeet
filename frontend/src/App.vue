<template>
  <v-app style="max-width:540px;">
    <div>
      <v-app-bar
        v-if='isLogin'
        color="white"
        dense
        dark
        fixed
        style="z-index:10; max-width:540px;"
      >

        <img src="./assets/images/logo.png" class="mb-1"
        style="width: 35%; max-height: 50px; max-width: 80px;">

        <v-spacer></v-spacer>

        <div v-if="isSearch">
          <b-form-input type="text" size="sm" placeholder="방이름을 검색해주세요." v-model="search" @keydown.enter="searchInput"></b-form-input>
        </div>
        <v-btn icon @click="searchInput">
          <v-icon style="color: black;">mdi-magnify</v-icon>
        </v-btn>
      </v-app-bar>
    </div>
    
    <v-main>
      <router-view class="mt-14 mb-3" :key="$route.fullPath"></router-view>
    </v-main>

    <v-bottom-navigation
      :value="value"
      v-if='isLogin'
      color="indigo accent-2"
      app
      fixed
      grow
      width="100%"
      style="z-index:10; max-width:540px;"
    >
      <v-btn style="height: 56px;">
        <router-link class="ro" :to="{ name: 'Main' }"><v-icon>mdi-home</v-icon></router-link>
      </v-btn>
      <v-btn style="height: 56px;">
        <router-link class="ro" :to="{ name: 'MyCalendar'}"><v-icon>mdi-calendar</v-icon></router-link>
      </v-btn>
      <v-btn style="height: 56px;">
        <router-link class="ro" :to="{ name: 'MakeMeeting' }"><v-icon>mdi-plus-circle</v-icon></router-link>
      </v-btn>
      <v-btn style="height: 56px;">
        <router-link class="ro" :to="{ name: 'Alarm' }"><v-icon>mdi-bell</v-icon></router-link>
      </v-btn>
      <v-btn style="height: 56px;">
        <router-link class="ro" :to="{ name: 'MyPage' }"><v-icon>mdi-account-circle</v-icon></router-link>
      </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>


<script>
import { mapState } from 'vuex'

export default {
  name: 'App',
  data: () => {
    return {
      value: null,
      isSearch: false,
      search: '',
    }
  },
  watch: {
    $route() {
      var ro = this.$route.name
      if (ro === "Main") {
        this.value = 0
      } else if (ro === "MyCalendar") {
        this.value = 1
      } else if (ro === "MakeMeeting") {
        this.value = 2
      } else if (ro === "Alarm") {
        this.value = 3
      } else if (ro === "MyPage") {
        this.value = 4
      }
    },
  },
  methods: {
    searchInput() {
      if (this.search === ''){
        this.isSearch = !this.isSearch
      } else {
        this.$router.push({name:"SearchMeetingRoom", params:{"search":this.search}})
        this.search = ''
        this.isSearch = false
      }
    },
  },
  computed: {
    ...mapState(['isLogin'])
  }
};
</script>

<style>

@import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap');
  @font-face {
    font-family: 'LotteMartHappy';
    font-style: normal;
    font-weight: 700;
    src: url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartHappy/LotteMartHappyBold.woff2') format('woff2'), url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartHappy/LotteMartHappyBold.woff') format('woff');
  }

  #app {
    font-family: 'LotteMartHappy', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    /* text-align: center; */
  }

  .ro {
    text-decoration: none !important;
    color: inherit !important;
  }
</style>