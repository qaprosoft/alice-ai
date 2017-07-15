#!/bin/bash

#TODO: move general properties declaration in to environment as prerequisites
export DARKFLOW_HOME=/home/ubuntu/darkflow/

export IMAGE_HOME=$HOME/tools/alice/data/ideal/img
echo IMAGE_HOME: $IMAGE_HOME

export ANNOTATION_HOME=$HOME/tools/alice/data/ideal/ann
echo ANNOTATION_HOME: $ANNOTATION_HOME

nohup $DARKFLOW_HOME/flow --train --annotation $ANNOTATION_HOME --dataset $IMAGE_HOME --model $DARKFLOW_HOME/cfg/tiny-ai-weights-lc-mlb.cfg --load -1 --trainer adam --gpu 0.9 --lr 1e-5 --keep 15 --backup $DARKFLOW_HOME/ckpt/mlb_branch/ --batch 8 --save 2900 --epoch 3000 > ../logs/train.log &
