<!--职业复查报告 开发人：麦沃德科技矢北 -->
<template>
  <div class="preview-group-health-report">
    <div class="group-report">

    </div>

  </div>
</template>

<script>

export default {

}
</script>

<style lang="scss" scoped>
.preview-group-health-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .group-report {
    width: 210mm;
    margin: 0 auto;
  }

  * {
    font-family: 'Song';
  }
}

@media print {
  .preview-group-health-report {
    background: #fff;

    .group-report .report-conclusion,
    .group-report .report-end {
      min-height: 297mm;
    }
  }

  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }
}</style>