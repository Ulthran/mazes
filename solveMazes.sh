#!/bin/sh

for f in Mazes/*.txt
do
  echo $f
  java Maze $f
done
