function LiveFeed({ clicks }) {
  return (
    <section style={{
      background: '#1e293b',
      borderRadius: 12,
      padding: 16,
      maxHeight: 600,
      overflowY: 'auto'
    }}>
      <h2 style={{ marginTop: 0 }}>Feed en vivo</h2>

      {clicks.length === 0 && <p style={{ opacity: 0.6 }}>Esperando clics...</p>}

      {clicks.map(click => (
        <div key={click.id} style={{
          padding: 10,
          borderBottom: '1px solid #334155',
          display: 'grid',
          gridTemplateColumns: 'minmax(110px, 1fr) auto minmax(180px, 2fr)',
          gap: 12,
          background: click.isAnomaly ? 'rgba(239,68,68,0.15)' : 'transparent'
        }}>
          <span>{click.ip}</span>
          <span>{click.amount?.toFixed(2)} €</span>
          <span style={{ color: click.isAnomaly ? '#f87171' : '#4ade80' }}>
            {click.isAnomaly ? `Anomalía: ${click.reason}` : 'Normal'}
          </span>
        </div>
      ))}
    </section>
  )
}

export default LiveFeed