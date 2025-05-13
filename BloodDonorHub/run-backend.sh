#!/usr/bin/env bash
# -----------------------------------------------
# Starts the Spring Boot backend with a 32‑byte JWT
# secret. Generates .jwt-secret on first run.
# -----------------------------------------------

if [ ! -f ".jwt-secret" ]; then
  # 64 hex chars = 32 bytes
  openssl rand -hex 32 > .jwt-secret
  echo "Generated new JWT secret: $(cat .jwt-secret)"
fi

export JWT_SECRET=$(cat .jwt-secret)
export DB_PASSWORD=GLUTTiSH18!

mvn -q spring-boot:run
