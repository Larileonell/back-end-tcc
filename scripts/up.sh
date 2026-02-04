#!/usr/bin/env bash
docker compose up -d --build
sleep 5
docker compose ps
