function FraudGauge({ clicks }) {
  const total = clicks.length
  const anomalies = clicks.filter(click => click.isAnomaly).length
  const rate = total > 0 ? (anomalies / total) * 100 : 0
  const color = rate > 20 ? '#ef4444' : rate > 8 ? '#f59e0b' : '#22c55e'

  return (
    <section style={{ padding: 20, background: '#1e293b', borderRadius: 12 }}>
      <div style={{ fontSize: 14, opacity: 0.75 }}>
        Tasa de anomalías en los últimos 100 clics
      </div>
      <div style={{ fontSize: 48, fontWeight: 'bold', color }}>
        {rate.toFixed(1)}%
      </div>
      <div style={{ fontSize: 14, opacity: 0.75 }}>
        {anomalies} de {total} clics clasificados como anómalos
      </div>
    </section>
  )
}

export default FraudGauge