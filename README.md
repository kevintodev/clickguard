# ClickGuard
Prototipo educativo de detección de anomalías en clics. Utiliza tráfico
simulado y un modelo Isolation Forest entrenado con datos sintéticos.
No es un sistema antifraude validado para producción.
## Arquitectura
```text
Simulador ─┐
├─> Spring Boot (8080) ─> FastAPI (8000) ─> Isolation Forest
React ─────┘ │
└─> H2 en memoria
```

- **Python:** puntuación de anomalías.
- **Spring Boot:** validación, orquestación y persistencia.
- **React:** visualización de los últimos 100 clics.
## Requisitos
Python 3.10+, Java 27, Node.js 18+, npm y Git.
## Configuración

Copia `python-service/.env.example` como `python-service/.env` y 
`frontend-react/.env.example` como `frontend-react/.env`.
Revisa `backend-java/src/main/resources/application.properties`.

## Puesta en marcha

### 1. Detector
```bash
cd python-service
python -m venv venv
source venv/bin/activate       # Windows: venv\Scripts\activate
pip install -r requirements.txt
uvicorn main:app --host 127.0.0.1 --port 8001
```
### 2. Backend
```bash
cd backend-java 
./mvnw spring-boot:run         # Windows: mvnw.cmd spring-boot:run
```

### 3. Frontend
```bash
cd frontend-react
npm install
npm install axios
npm run dev
```
Abre http://localhost:5173.
### 4. Simulador
```bash
cd python-service
source venv/bin/activate       # Windows: venv\Scripts\activate
python simulator.py
```
## Ráfaga de demostración
Detén el simulador normal con Ctrl+C y ejecuta:
```bash
python simulator.py bot
```
Después, ejecuta `python simulator.py`para volver al tráfico normal.
## Comprobaciones útiles
- Detector: http://localhost:8000/docs
- API: http://localhost:8080/api/clicks
- H2: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:clickguard`
## Limitaciones
- Los datos y el ataque son sintéticos.
- La puntuación no es una probabilidad calibrada.
- H2 pierde los datos al reiniciar Spring Boot.
- El sondeo cada dos segundos es una solución didáctica, no de alto tráfico.
- No se incluyen autenticación, autorización ni despliegue productivo.