import axios from 'axios'

const API = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',
    timeout: 5000
})

export const getRecentClicks = () =>
    API.get('/clicks').then(response => response.data)