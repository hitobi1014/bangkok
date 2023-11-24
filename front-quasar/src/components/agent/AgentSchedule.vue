<script setup>
import { ref, inject } from 'vue'
import agentAPI from '@/api/agent'

const currentDate = new Date()
const date = ref(
  `${currentDate.getFullYear()}/${currentDate.getMonth() + 1}/${currentDate.getDate()}`
)
const agent = inject('agent')
const appointments = ref([])
const appointmentDates = ref([])

const amSlots = []
for (let hour = 9; hour <= 12; hour++) {
  const time = `${hour.toString().padStart(2, '0')}:00`
  amSlots.push(time)
}

const pmSlots = []
for (let hour = 13; hour <= 20; hour++) {
  const time = `${hour.toString().padStart(2, '0')}:00`
  pmSlots.push(time)
}

const isButtonDisabled = (timeSlot) => {
  const matchingAppointment = appointments.value.find(
    (appointment) => appointment.date === date.value && appointment.time === timeSlot
  )
  return matchingAppointment !== undefined
}

const getButtonColor = (timeSlot) => {
  const matchingAppointment = appointments.value.find(
    (appointment) => appointment.date === date.value && appointment.time === timeSlot
  )
  return matchingAppointment ? 'gray' : 'white'
}

const getAppointments = () => {
  agentAPI.getAgentSchedules(
    agent.value.id,
    (data) => {
      appointments.value = data.data.map((appointment) => ({
        ...appointment,
        date: appointment.date.replace(/-/g, '/')
      }))
      console.log(appointments.value)
      for (const key in appointments.value) {
        appointmentDates.value.push(appointments.value[key].date)
      }
      console.log(appointmentDates.value)
    },
    () => {
      console.log('약속 조회에 실패하였습니다.')
    }
  )
}

getAppointments()
</script>

<template>
  <div class="map-left">
    <div class="q-pa-md">
      <h2 class="text-h6 text-bold">예약 일정</h2>
      <q-date v-model="date" :events="appointmentDates" :event-color="$primaryColor" />
    </div>

    <div class="q-pa-md appoint-board">
      <div class="time-slot-header">오전</div>
      <div class="q-gutter-x-md">
        <q-btn
          v-for="timeSlot in amSlots"
          :key="timeSlot"
          :disable="isButtonDisabled(timeSlot)"
          :style="{ backgroundColor: getButtonColor(timeSlot) }"
          text-color="black"
          @click="handleTimeSlotClick(timeSlot)"
        >
          {{ timeSlot }}
        </q-btn>
      </div>
      <div class="time-slot-header">오후</div>
      <div class="q-gutter-x-md">
        <q-btn
          v-for="timeSlot in pmSlots"
          :key="timeSlot"
          :disable="isButtonDisabled(timeSlot)"
          text-color="black"
          @click="handleTimeSlotClick(timeSlot)"
        >
          {{ timeSlot }}
        </q-btn>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.q-gutter-x-md {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 5px;
  width: 100px;
  margin: 5px;
  padding: 5px;

  .q-btn {
    width: 70px !important;
    height: 30px !important;
    margin: 0 !important;
    padding: 0 !important;
  }
}

.time-slot-header {
  font-weight: bold;
  margin-right: 100px;
}

.appoint-board {
  max-width: 300px; /* 원하는 크기로 조절하세요 */
}
</style>
