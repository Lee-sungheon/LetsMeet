<template>
  <transition-group
    name="messages-list"
    tag="div">
    <div
      v-for="(message, index) in feed"
      :key="index"
      class="messages-list-item">
      <message-own
        v-if="message.id === authorId"
        :date="message.date"
        :contents="message.contents" />
      <message-foreign
        v-else
        :author="message.author"
        :date="message.date"
        :contents="message.contents"
        :image="message.uImage" />
    </div>
  </transition-group>
</template>

<script>
import MessageOwn from './MessageOwn.vue'
import MessageForeign from './MessageForeign.vue'

export default {
  name: 'MessagesList',
  components: {
    MessageOwn,
    MessageForeign
  },
  props: {
    feed: {
      type: Array,
      required: false
    },
    authorId: {
      type: Number,
      default: 0,
      required: false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
