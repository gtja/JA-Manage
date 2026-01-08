<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'jignan',
  data() {
    return {
      url: 'https://github.com/gtja/JA-Manage'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>