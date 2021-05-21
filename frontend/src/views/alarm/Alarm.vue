<template>
  <v-container>
    <div class="pt-2 d-flex justify-center mb-3" style="border-bottom: 2px solid black">
      <h5>알람</h5>
      <v-badge
        v-if="len>0"
        color="indigo accent-2"
        :content="len"
      >
      </v-badge>
    </div>
    <v-card
      max-width="100%"
      class="mx-auto"
    >
      <v-list>
        <template v-for="(item, index) in alarms">
          <v-subheader
            v-if="item.header"
            :key="index"
            v-text="item.header"
          >
          <v-btn icon>
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
          </v-subheader>
  
          <v-divider
            v-else-if="item.divider"
            :key="index"
            :inset="item.inset"
          ></v-divider>

          <v-list-item
            v-else
            :key="index"
          >
            <v-list-item-avatar>
              <v-img :src="item.avatar"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title v-html="item.title"></v-list-item-title>
              <div v-html="item.subtitle" class="mt-1" style="color: gray; font-size: 0.9rem;"></div>
              <div class="mt-3">
                <v-row>
                  <v-col class="p-0 m-0 pl-1" cols=6>
                    <v-btn text small color="indigo accent-2" @click="goAction(item)">{{ btnType[item.title][0] }}<v-icon light>{{ btnType[item.title][1] }}</v-icon></v-btn>
                  </v-col>
                  <v-col class="p-0 m-0" cols=6>
                    <v-btn text small color="error"  @click="deleteAlarm(item.id)">알람 삭제 <v-icon light>mdi-close-thick</v-icon></v-btn>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </v-container>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "Alarm",
  data() {
    return {
      alarms: [],
      len: 0,
      btnType: {
        '방 초대 알림': ['방 바로가기', 'mdi-run'],
        '친구 추가 알림': ['친구 추가하기', 'mdi-account-plus'],
        '일정 선택 알림': ['최종 선택하기', 'mdi-calendar-multiselect'],
        '일정 확정 알림': ['방 바로가기', 'mdi-run']
      },
    }
  },
  mounted() {
    this.getAlarm()
  },
  methods: {
    getAlarm() {
      this.alarms = []
      axios.get(`${server_URL}/letsmeet/alarm?uNo=${this.$store.state.uNo}`)
      .then((res) => {
        const alarmLists = res.data
        this.len = res.data.length
        var header = alarmLists[alarmLists.length-1].aTime.slice(0,10)
        this.alarms.push({ header: header })
        for (var i=alarmLists.length-1 ; i >= 0 ; i--) {
          if (header !== alarmLists[i].aTime.slice(0,10)) {
            header = alarmLists[i].aTime.slice(0,10)
            this.alarms.push({ header: header })
          }
          const content = alarmLists[i].aContent.split('/')
          const data = {
            id: alarmLists[i].aNo,
            avatar: alarmLists[i].aSenderImage,
            title: alarmLists[i].aTitle,
            subtitle: content[0],
            subdata: content[1],
          }
          this.alarms.push(data)
          this.alarms.push({ divider: true, inset: true })
        }
        console.log(this.alarms)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    goAction(item) {
      if (item.title === '방 초대 알림' || item.title === '일정 선택 알림' || item.title === '일정 확정 알림') {
        this.$router.push({name:"MeetingRoom", params:{"id":item.subdata}})
      }else if (item.title === '친구 추가 알림') {
        this.addFreind(item)
      }
    },
    deleteAlarm(aNo) {
      axios.delete(`${server_URL}/letsmeet/alarm/delete?aNo=${aNo}`)
      .then(() => {
        this.getAlarm()
      })
      .catch((err) => {
        console.log(err)
      })
    },
    addFreind(item) {
      axios.post(`${server_URL}/letsmeet/mypage/friend/add?friend=${item.subdata}&myUNo=${this.$store.state.uNo}`)
      .then(()=> {
        alert('친구 추가하였습니다.')
      })
      .catch(()=> {
        alert('이미 친구입니다.')
      })
    }
  }
}
</script>

<style>

</style>