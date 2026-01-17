#!/bin/sh
set -e

BUCKET_NAME="files"
ALIAS="myminio"
MINIO_URL="http://minio:9000"
ACCESS_KEY="minioadmin"
SECRET_KEY="minioadminpw"

echo "⏳ Warte auf MinIO..."
until mc alias set $ALIAS $MINIO_URL $ACCESS_KEY $SECRET_KEY 2>/dev/null; do
  sleep 2
done

echo "📦 Stelle sicher, dass Bucket existiert..."
mc mb --ignore-existing $ALIAS/$BUCKET_NAME

echo "🔍 Prüfe, ob Bucket bereits Dateien enthält..."
if mc ls $ALIAS/$BUCKET_NAME | grep -q .; then
  echo "ℹ️ Bucket enthält bereits Dateien – überspringe Upload"
else
  echo "⬆️ Bucket ist leer – lade Dateien hoch"
  mc cp --recursive /files $ALIAS/$BUCKET_NAME
fi

echo "✅ MinIO Init abgeschlossen"