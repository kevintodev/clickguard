import { useEffect, useState } from 'react'
import { getRecentClicks } from './api'
import FraudGauge from './components/FraudGauge'
import LiveFeed from './components/LiveFeed'
import SuspiciousIPs from './components/SuspiciousIPs'

function App() {
  const [clicks, setClicks] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    let active = true

    const tick = async () => {
      try {
        const data = await getRecentClicks()
        if (active) {
          setClicks(data)
          setError('')
        }
      } catch (requestError) {
        console.error(requestError)
        if (active) {
          setError('No se puede conectar con el backend. Revisa Spring Boot.')
        }
      }
    }

    tick()
    const intervalId = setInterval(tick, 2000)

    return () => {
      active = false
      clearInterval(intervalId)
    }
  }, [])

  return (
    <main style={{
      padding: 24,
      fontFamily: 'system-ui, sans-serif',
      background: '#0f172a',
      minHeight: '100vh',
      color: '#e2e8f0',
      boxSizing: 'border-box'
    }}>
      <h1>ClickGuard — Detección de anomalías en clics</h1>
      <p style={{ opacity: 0.75 }}>
        Prototipo educativo con tráfico simulado. La puntuación no es una probabilidad de fraude.
      </p>

      {error && <p role="alert" style={{ color: '#fca5a5' }}>{error}</p>}

      <FraudGauge clicks={clicks} />

      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))',
        gap: 24,
        marginTop: 24
      }}>
        <LiveFeed clicks={clicks} />
        <SuspiciousIPs clicks={clicks} />
      </div>
    </main>
  )
}

export default App