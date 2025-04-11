import axios, { AxiosInstance } from 'axios'
import { toast } from 'react-toastify'

const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/products',
  headers: {
    'Content-Type': 'application/json',
  },
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message = error.response?.data?.message || 'Ocorreu um erro'
    toast.error(message)
    return Promise.reject(error)
  }
)

export default api