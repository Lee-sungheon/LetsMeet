<template>
    <v-container>
      <div class="d-flex justify-center pt-4 mx-5" style="border-bottom:2px solid black">
        <h5> 나의 일정</h5>
      </div>
        <div>
    <v-sheet
      tile
      height="54"
      class="d-flex"
    >
      <v-btn
        icon
        class="ma-2"
        @click="$refs.calendar.prev()"
      >
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-spacer></v-spacer>
      <!-- <v-spacer class="pt-3 text-center">{{month}}</v-spacer> -->
      <v-btn
        icon
        class="ma-2"
        @click="$refs.calendar.next()"
      >
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-sheet>

    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        :weekdays="weekday"
        :type="type"
        :events="events"
        @click:event ='showEvent'
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
        @change="getDate"
      ></v-calendar>
    </v-sheet>
  </div>
</v-container>

</template>
<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
    name: 'MyCalendar',
data: () => ({
      selectedEvent: {},
      type: 'month',
      month: '',
      mode: 'stack',
      weekday: [0, 1, 2, 3, 4, 5, 6],
      weekdays: [
        { text: 'Sun - Sat', value: [0, 1, 2, 3, 4, 5, 6] },
        { text: 'Mon - Sun', value: [1, 2, 3, 4, 5, 6, 0] },
        { text: 'Mon - Fri', value: [1, 2, 3, 4, 5] },
        { text: 'Mon, Wed, Fri', value: [1, 3, 5] },
      ],
      value: '',
      events: [],
      colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
      names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party'],
      myDate: []
    }),
    mounted () {
      var monthNames = ["January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"
                        ];
      let today = new Date();
      let month = monthNames[today.getMonth()] ;
      this.month = month
    },
    methods: {
      showEvent({ event }){
        this.$router.push({ name: 'MeetingRoom', params: {'id': event.number}})
      },
      getDate() {
        axios.get(`${server_URL}/letsmeet/main?uNo=${this.$store.state.uNo}`)
        .then((res)=> {
          console.log(res.data)
          const events = []
          const date = res.data
          date.forEach(el => {
            if (el.mrDate !== null){
              events.push({
                name: el.mrName,
                  // name: this.names[this.rnd(0, this.names.length - 1)],
                  start: el.mrDate,
                  number: el.mrNo,
                  color: this.colors[this.rnd(0, this.colors.length - 1)],
                })
            }
          this.events = events
          });
          // console.log(this.myDate)
        })
        .catch(()=> {
          console.log("안들오감")
        })
      },
      getEvents () {
        // const events = []
        // const min = new Date(`${start.date}T00:00:00`)
        // const max = new Date(`${end.date}T23:59:59`)
        // const days = (max.getTime() - min.getTime()) / 86400000
        // const eventCount = this.rnd(days, days + 20)
        
        // for (let i = 0; i < eventCount; i++) {
        //   const allDay = this.rnd(0, 3) === 0
        //   const firstTimestamp = this.rnd(min.getTime(), max.getTime())
        //   const first = new Date(firstTimestamp - (firstTimestamp % 900000))
        //   const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
        //   const second = new Date(first.getTime() + secondTimestamp)
        // this.myDate.forEach(el => {})
        //   events.push({
        //     name: this.myDate[0].mrName,
        //     // name: this.names[this.rnd(0, this.names.length - 1)],
        //     start: this.myDate[0].mrDate,
        //     // end: second,
        //     color: this.colors[this.rnd(0, this.colors.length - 1)],
        //   })
        // this.events = events
      },
      getEventColor (event) {
        return event.color
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },
    },
  }
</script>