<script setup>
// import {Chart} from 'chart.js/auto';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
import {Line} from 'vue-chartjs'
import {onBeforeMount, onMounted, ref, watch} from "vue";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)
// const {data, options} = LineChart

// 임시 더미 데이터
const inputData = {
  labels: ['월', '화', '수', '목', '금', '토', '일'],
  datasets: [{
    label: '실거래가',
    data: [65, 59, 80, 81, 56, 55, 40],
    fill: false,
    borderColor: 'rgb(75, 192, 192)',
    tension: 0.1
  }]
}
const inputOption = {
  options: {
    responsive: false,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Chart.js Line Chart'
      }
    }
  }
}

//

const props = defineProps(['data', 'options']);

const chartData = ref()
const chartOptions = ref()




watch(()=>props.options, ()=> {
  console.log(`차트 데이터 변경`)
  chartData.value = props.data
  chartOptions.value = props.options
  // renderChart(chartData, chartOptions)
},{deep:true})

onMounted(() => {
  // renderChart(chartData, chartOptions)
})

onBeforeMount(() => {
  if (chartData.value == null) {
    chartData.value = inputData
    chartOptions.value = inputOption
  }
})

</script>

<template>
  <Line :data="chartData" :options="chartOptions"/>
</template>

<style scoped lang="scss">


</style>