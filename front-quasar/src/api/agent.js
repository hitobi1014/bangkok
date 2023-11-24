import { localAxios } from "@/utils/http-commons";

const agentAPI = localAxios();

const getAgentList = (success, fail) => {
  agentAPI.get('/api/agent/list').then(success).catch(fail)
}

const getAgent = (id, success, fail) => {
  agentAPI.get(`/api/agent/detail/${id}`).then(success).catch(fail)
}

const getAgentSchedules = (id, success, fail) => {
  agentAPI.get(`/api/agent/appointments/${id}`).then(success).catch(fail)
}

const joinAgent = (Agent, success, fail) => {
  agentAPI.post('/api/agent/join', Agent).then(success).catch(fail)
}

const AgentLogin = (Agent, success, fail) => {
  agentAPI.post('/api/agent/login', Agent).then(success).catch(fail)
}

const AgentLogout = (success, fail) => {
  agentAPI.get('/api/agent/logout').then(success).catch(fail)
}

export default { getAgentList, getAgent, getAgentSchedules, joinAgent, AgentLogin, AgentLogout }
