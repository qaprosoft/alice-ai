#!/bin/bash

# Start recognition for appropriate item(s) and calculate precision accuracy based on ideal metadata
export DARKFLOW_HOME=/home/ubuntu/darkflow/

export MODEL=default
export IMAGE_HOME=$1
export OUTPUT_TYPE=$2

echo running recognition command against $IMAGE_HOME
python recognize.py --model $MODEL --folder $IMAGE_HOME --output $OUTPUT_TYPE > /dev/null 2>&1

