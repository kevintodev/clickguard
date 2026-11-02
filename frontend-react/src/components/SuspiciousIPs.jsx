function SuspiciousIPs({ clicks }) {
  const countsByIp = {}

  clicks
    .filter(click => click.isAnomaly)
    .forEach(click => {
      countsByIp[click.ip] = (countsByIp[click.ip] || 0) + 1
    })

  const sorted = Object.entries(countsByIp)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 5)

  return (
    <section style={{ background: '#1e293b', borderRadius: 12, padding: 16 }}>
      <h2 style={{ marginTop: 0 }}>IP con más anomalías</h2>

      {sorted.length === 0 && <p style={{ opacity: 0.6 }}>Ninguna todavía</p>}

      {sorted.map(([ip, count]) => (
        <div key={ip} style={{
          padding: 10,
          borderBottom: '1px solid #334155',
          display: 'flex',
          justifyContent: 'space-between',
          gap: 12
        }}>
          <span>{ip}</span>
          <span style={{ color: '#f87171' }}>{count} alertas</span>
        </div>
      ))}
    </section>
  )
}

export default SuspiciousIPs