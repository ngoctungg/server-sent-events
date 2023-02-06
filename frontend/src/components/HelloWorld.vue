<template>
  <div class="hello">
    <textarea name="" id="textarea" cols="30" rows="10" :value="txtArea"></textarea>
    <input type="button" @click="connection" value="Connection" />
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  data() {
    return {
      txtArea: "Hello",
    };
  },
  methods: {
    connection() {
      const evtSource = new EventSource(
        "http://localhost:8080/api/v1/events/hello"
      );
      let that = this;
      const container = this.$el.querySelector('#textarea');
      evtSource.onmessage = function (event) {
        that.txtArea += "\n" + event.data;
      };
      evtSource.addEventListener("hello",(event)=>{
        that.txtArea += "\n" + event.data;
        container.scrollTop = container.scrollHeight;
      });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
