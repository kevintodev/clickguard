from fastapi import FastAPI
from pydantic import BaseModel, Field

from model import score_click

app = FastAPI(
    title="Clickguard detector",
    description="Servicio didáctico de puntuación de anomalías",
    version="1.0.0"
)

class ClickFeatures(BaseModel):
    clicks_per_minute: float = Field(ge=0)
    amount_vs_mean: float = Field(ge=0)
    unique_ips: int = Field(ge=1)
    seconds_since_last_click: float = Field(ge=0)
    user_agent_entropy: float = Field(ge=0, le=1)
    
@app.get("/health")
def health():
    return {"status": "ok"}

@app.post("/score")
def score(features: ClickFeatures):
    return score_click(features.model_dump())