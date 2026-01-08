<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'JADoc',
  data() {
    return {
      url: 'https://doc.gtja.com/ja-manage'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>