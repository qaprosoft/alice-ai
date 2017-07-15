#!/bin/bash

# Start recognition for appropriate item(s) and calculate precision accuracy based on ideal metadata
export DARKFLOW_HOME=/home/ubuntu/darkflow/

export MODEL=$1
export IMAGE_HOME=$2
export ANNOTATION_HOME=$3

export MODEL=default
export IMAGE_HOME=$HOME/tools/alice/data/ideal/test
export ANNOTATION_HOME=$HOME/tools/alice/data/ideal/test/ann

export IMAGE_HOME=$HOME/tools/alice/data/ideal/img
export ANNOTATION_HOME=$HOME/tools/alice/data/ideal/ann

#clean existing $IMAGE_HOME/out folder if any
if [ -d $IMAGE_HOME/out ]; then
	echo Removing $IMAGE_HOME/out...
	rm -rf $IMAGE_HOME/out
fi

echo running recognition command against $IMAGE_HOME
python recognize.py --model $MODEL --folder $IMAGE_HOME --output xml > /dev/null 2>&1

echo running precision accuracy calculation script
python precise.py --truth $ANNOTATION_HOME --predicted $IMAGE_HOME/out 

