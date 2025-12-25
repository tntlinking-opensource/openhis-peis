<template>
  <div class="body-main">
    <div class="body-middle">请将身份证放在识别区</div>
    <div class="body-bottom">
      <img src="@/assets/images/selfHelp/IDcard.png" />
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style lang="scss" scoped>
.body-main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding-top: 20px;

  .body-middle {
    text-align: center;
    padding: 100px 0 56px;
    font-weight: 400;
    font-size: 48px;
    color: #000000;
  }

  .body-bottom {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 550px;
    color: #333333;
    img {
      height: 100%;
      padding-right: 200px;
    }
  }
}
@media screen and (max-width: 1950px) {
  .body-main {
  padding-top: 14px;

  .body-middle {
    padding: 60px 0 40px;
    font-size: 40px;
  }

  .body-bottom {
    height: 450px;
    img {
      height: 100%;
      padding-right: 200px;
    }
  }
}
}
@media screen and (max-width: 1200px) {
  .body-main {
  .body-bottom {
    img {
      padding-right: 150px;
    }
  }
}
}
</style>
