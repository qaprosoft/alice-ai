#!/bin/bash

# Start recognition for appropriate item(s) and calculate precision accuracy based on ideal metadata
export DARKFLOW_HOME=/home/ubuntu/darkflow/

export MODEL=$1
export IMAGE_HOME=$2
export ANNOTATION_HOME=$3

export MODEL=2.0

export DATA_HOME=$HOME/tools/alice/data/2.0
echo DATA_HOME: $DATA_HOME

#make temporary folder with symlinks onto the train set data (img and ann)
export TMP_DATA=/tmp/precise

#clean existing $TMP_DATA folder if any
if [ -d $TMP_DATA ]; then
        echo Removing $TMP_DATA...
        rm -rf $TMP_DATA
        echo creating clean $TMP_DATA folder
fi


mkdir $TMP_DATA
#recursively create symlinks from $DATA_HOME to $TMP_DATA
mkdir $TMP_DATA/img
mkdir $TMP_DATA/ann

find $DATA_HOME -name '*.png' -exec ln -vs "{}" $TMP_DATA/img/ ';'
find $DATA_HOME -name '*.xml' -exec ln -vs "{}" $TMP_DATA/ann/ ';'






export IMAGE_HOME=$TMP_DATA/img
export ANNOTATION_HOME=$TMP_DATA/ann

echo running recognition command against $IMAGE_HOME
python recognize.py --model $MODEL --folder $IMAGE_HOME --output xml

echo running precision accuracy calculation script
python precise.py --truth $ANNOTATION_HOME --predicted $IMAGE_HOME/out 

