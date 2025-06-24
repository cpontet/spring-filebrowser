#!/bin/bash

# Usage: ./create_cc_fs_maven.sh <ORG> [REGION]
# ORG is required, REGION defaults to 'par'

if [ -z "$1" ]; then
  echo "Usage: $0 <ORG> [REGION]"
  exit 1
fi

ORG="$1"
REGION="${2:-par}"

# Create FS bucket addon
FS_BUCKET_ID=$(clever addon create fsbucket --region "$REGION" --org "$ORG" --alias my-fs-bucket | grep 'id:' | awk '{print $2}')

# Create Maven app
clever create -t maven -o "$ORG" -r "$REGION" my-maven-app

# Link FS bucket to Maven app
clever service link-addon my-maven-app $FS_BUCKET_ID

# Mount the FS bucket on /myfiles
clever env set --add "CC_FS_BUCKET=/myfiles:$(clever addon info $FS_BUCKET_ID | grep 'host:' | awk '{print $2}')" --app my-maven-app

echo "FS bucket and Maven app created, linked, and mounted at /myfiles."
