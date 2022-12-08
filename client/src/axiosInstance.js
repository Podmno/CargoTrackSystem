import axios from 'axios'


const API = axios.create({
	baseUrl:'http://localhost:8088',
	timeout: 2000,
	withCredentials: true,

})

API.defaults.withCredentials = true
export default API
