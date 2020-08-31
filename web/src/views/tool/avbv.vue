<template>
  <mu-container>
    <mu-flex>
      <mu-text-field v-model="av" placeholder="请填写av号"></mu-text-field>
    </mu-flex>
    <mu-flex>
      <mu-text-field v-model="bv" placeholder="请填写bv号"></mu-text-field>
    </mu-flex>
    <mu-flex>
      <mu-button color="secondary" @click="av2bv">av=>bv</mu-button>
      <mu-button color="secondary" @click="bv2av" style="margin-left: 20px;">bv=>av</mu-button>
    </mu-flex>
  </mu-container>
</template>

<script>
  export default {
    name: "avbv",
    data() {
      return {
        bv: "",
        av: "",
        table: "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF",
        tr: {},
        s: [11, 10, 3, 8, 4, 6],
        xor: 177451812,
        add: 8728348608,
      }
    },
    methods: {
      bv2av() {
        const x = this.bv;
        let r = 0;
        for (let i = 0; i < 6; i++) {
          r += this.tr[x[this.s[i]]] * 58 ** i
        }
        this.av = "av".concat((r - this.add) ^ this.xor);
      },
      av2bv() {
        let x = this.av.replace("av", "");
        x = (x ^ this.xor) + this.add
        let r = 'BV1  4 1 7  '.split('')
        for (let i = 0; i < 6; i++) {
          r[this.s[i]] = this.table[parseInt(x / 58 ** i) % 58]
        }
        this.bv = r.join("");
      }
    },
    created() {
      for (let i = 0; i < 58; i++) {
        this.tr[this.table[i]] = i;
      }
    }
  }
</script>

<style scoped>

</style>
