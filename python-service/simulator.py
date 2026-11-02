import os
import random
import sys
import time

import requests
from dotenv import load_dotenv

load_dotenv()

BACKEND = os.getenv("BACKEND_URL", "http://localhost:8080/api/clicks")

def random_ip():
    return ".".join([
        str(random.randint(1, 255)),
        str(random.randint(0, 255)),
        str(random.randint(0, 255)),
        str(random.randint(0, 255)),
    ])

def random_user_agent():
    return random.choice([
        "Mozilla/5.0",
        "Chrome/120",
        "Safari/17",
        "Firefox/121",
    ])

def send_click(
    cpm=2,
    amount_mult=1.0,
    ips=1,
    secs=60,
    entropy=0.8,
    ip=None):
    payload = {
        "ip": ip or random_ip(),
        "userAgent": random_user_agent(),
        "amount": round(random.uniform(0.1, 3.0) * amount_mult, 2),
        "features": {
            "clicksPerMinute": cpm + random.uniform(-0.5, 0.5),
            "amountVsMean": max(0, amount_mult + random.uniform(-0.2, 0.2)),
            "uniqueIps": ips,
            "secondsSinceLastClick": max(0.5, secs + random.uniform(-3, 3)),
            "userAgentEntropy": min(1, max(0.01, entropy + random.uniform(-0.05, 0.05))),
        },
    }

    try:
        response = requests.post(BACKEND, json=payload, timeout=3)
        response.raise_for_status()
    except requests.RequestException as exc:
        print(f"No se pudo enviar el clic: {exc}")

def run_normal():
    print("Generando tráfico normal. Pulsa Ctrl+C para detenerlo.")
    while True:
        send_click()
        time.sleep(random.uniform(0.3, 1.5))

def run_bot_burst(n=60):
    bot_ip = "203.0.113.50"
    print(f"Enviando una ráfaga de {n} clics desde {bot_ip}")

    for _ in range(n):
        send_click(
            cpm=40,
            amount_mult=8,
            ips=1,
            secs=1,
            entropy=0.1,
            ip=bot_ip,
        )
        time.sleep(0.1)

    print("Ráfaga terminada.")

if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] == "bot":
        run_bot_burst()
    else:
        run_normal()