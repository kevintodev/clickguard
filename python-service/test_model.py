from model import score_click

normal = {
    "clicks_per_minute": 2,
    "amount_vs_mean": 1,
    "unique_ips": 2,
    "seconds_since_last_click": 60,
    "user_agent_entropy": 0.8,
}

atipico = {
    "clicks_per_minute": 40,
    "amount_vs_mean": 8,
    "unique_ips": 40,
    "seconds_since_last_click": 2,
    "user_agent_entropy": 0.1,
}

print("NORMAL:", score_click(normal))
print("ATÍPICO:", score_click(atipico))

