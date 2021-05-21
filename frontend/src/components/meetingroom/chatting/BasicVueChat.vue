<template>
  <div
    class="basic-vue-chat" >
    <section class="window">
      <header class="window__header__container">
        <slot name="header">
          {{ title }}
        </slot>
      </header>
      <section
        id="window__messages__container"
        class="window__messages__container">
        <slot>
          <messages-list
            :feed="feed"
            :author-id="authorId"
            class="messages-list" />
        </slot>
      </section>
      <div class="window__input__container">
        <slot name="input-container">
          <input-container
            @newOwnMessage="onNewOwnMessage"
          />
        </slot>
      </div>
    </section>
  </div>
</template>

<script>
import { scrollToBottom } from '@/helpers/scroll.js'
import MessagesList from './messages/MessagesList.vue'
import InputContainer from './input/InputContainer.vue'

export default {
  name: 'BasicVueChat',
  components: {
    MessagesList,
    InputContainer
  },
  props: {
    title: {
      type: String,
      default: 'Team Maczan',
      required: false
    },
    initialAuthorId: {
      type: Number,
      default: 0,
      required: false
    },
    initialFeed: {
      type: Array,
      default: function () {
        return []
      },
      required: false
    },
    newMessage: {
      type: Object,
      default: function () {
        return {}
      },
      required: false
    }
  },
  data: function () {
    return {
      feed: [],
      authorId: 0,
    }
  },
  watch: {
    newMessage: function (newValue) {
      this.pushToFeed(newValue)
      scrollToBottom()
    }
  },
  mounted () {
    this.feed = this.initialFeed
    this.authorId = this.initialAuthorId
  },
  methods: {
    pushToFeed (element) {
      this.feed.push(element)
    },
    onNewOwnMessage (message) {
      scrollToBottom()
      this.$emit('newOwnMessage', message)
    }
  }
}
</script>

<style lang="scss">
@import "../../../assets/scss/main.scss";
</style>
