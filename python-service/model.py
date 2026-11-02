import os
from functools import lru_cache

import joblib
import numpy as np
from dotenv import load_dotenv
from sklearn.ensemble import IsolationForest

load_dotenv()

MODEL_PATH = os.getenv("MODEL_PATH", "model.pkl")
CONTAMINATION = float(os.getenv("CONTAMINATION", "0.05"))

def train_model():
    """ Crea datos sintéticos, entrena el modelo y lo guarda en disco. """
    rng = np.random.default_rng(42)
    n_normal, n_anomaly = 5000, 200
    
    normal = np.column_stack([
        rng.normal(2, 1, n_normal),
        rng.normal(1, 0.3, n_normal),
        rng.integers(1, 5, n_normal),
        rng.normal(60, 20, n_normal),
        rng.normal(0.8, 0.1, n_normal)
    ])
    
    anomaly = np.column_stack([
        rng.normal(40, 10, n_anomaly),
        rng.normal(8, 2, n_anomaly),
        rng.integers(10, 50, n_anomaly),
        rng.normal(2, 1, n_anomaly),
        rng.normal(0.1, 0.05, n_anomaly)
    ])
    
    X = np.vstack([normal, anomaly])
    model = IsolationForest(
        contamination=CONTAMINATION,
        random_state=42,
        n_estimators=100
    )
    
    model.fit(X)
    joblib.dump(model, MODEL_PATH)
    return model

@lru_cache(maxsize=1)
def load_model():
    """Carga el modelo una sola vez por proceso."""
    if not os.path.exists(MODEL_PATH):
        return train_model()
    return joblib.load(MODEL_PATH)

def score_click(features: dict):
    model = load_model()
    X = np.array([[
        features["clicks_per_minute"],
        features["amount_vs_mean"],
        features["unique_ips"],
        features["seconds_since_last_click"],
        features["user_agent_entropy"],
    ]])

    raw = model.decision_function(X)[0]
    prediction = model.predict(X)[0]

    # índice didáctico acotado entre 0 y 1; no es una probabilidad.
    score = max(0.0, min(1.0, 0.5 - float(raw)))
    is_anomaly = bool(prediction == -1)

    reasons = []
    if features["clicks_per_minute"] > 20:
        reasons.append(f"{features['clicks_per_minute']:.0f} clics/min")
    if features["amount_vs_mean"] > 3:
        reasons.append(f"importe {features['amount_vs_mean']:.1f}x media")
    if features["unique_ips"] > 8:
        reasons.append(f"{features['unique_ips']} IPs")
    if features["seconds_since_last_click"] < 5:
        reasons.append(f"{features['seconds_since_last_click']:.1f}s entre clics")
    if features["user_agent_entropy"] < 0.3:
        reasons.append("user-agent muy repetido")

    if is_anomaly:
        reason = ", ".join(reasons) if reasons else "patrón atípico"
    else:
        reason = "normal"

    return {
        "score": round(score, 3),
        "is_anomaly": is_anomaly,
        "reason": reason,
    }
    
    