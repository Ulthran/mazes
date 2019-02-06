#!/bin/sh

i=10
j=10
k=1

echo "$i"

java MazeGenerator $i $j Mazes/$i-$j-$k-maze.txt
