#!/bin/bash

#export DATA_PATTERN=$1
##echo DATA_PATTERN=$DATA_PATTERN
#if [ "$DATA_PATTERN" = "" ]; then
#	export DATA_PATTERN="*"
#fi
#echo DATA_PATTERN=$DATA_PATTERN

#TODO: move general properties declaration in to environment as prerequisites
export DARKFLOW_HOME=/home/ubuntu/darkflow

export DATA_HOME=$HOME/tools/alice/data/2.0
echo DATA_HOME: $DATA_HOME

#make temporary folder with symlinks onto the train set data (img and ann)
export TMP_DATA=/tmp/train

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

#nohup $DARKFLOW_HOME/flow --train --annotation $ANNOTATION_HOME --dataset $IMAGE_HOME --model $DARKFLOW_HOME/cfg/ai-2.0.cfg --load $DARKFLOW_HOME/ckpt/2.0/ai-2.0.weights --trainer adam --gpu 0.9 --lr 1e-3 --keep 15 --backup $DARKFLOW_HOME/ckpt/2.0/ --batch 8 --save 2900 --epoch 3000 > ../logs/train.log &

nohup $DARKFLOW_HOME/flow --train --annotation $TMP_DATA/ann --dataset $TMP_DATA/img --model $DARKFLOW_HOME/cfg/ai-2.0.cfg  --load -1 --trainer adam --gpu 0.9 --lr 1e-5 --keep 15 --backup $DARKFLOW_HOME/ckpt/2.0/ --batch 8 --save 2900 --epoch 3000 > ../logs/train.log &
