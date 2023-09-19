#!/bin/sh
mkdir delivery
rm -rf ./delivery/*

mvn clean install

cd report
docker run --rm --volume "$(pwd):/data" pandoc/latex report.md -o IN5020-Group7-Report.pdf
mv IN5020-Group7-Report.pdf ../delivery
cd ..

mkdir ./target/dataset
cp ./dataset/dataset.csv ./target/dataset
cp ./dataset/input.txt ./target/dataset

# zip ./src to source.zip
cd ./src
zip -r ../delivery/source.zip ./*
cd ..

# zip Client.jar Server.jar and ./delivery/dataset to artifact.zip
cd ./target
zip -j ../delivery/artifact.zip Server.jar Client.jar
zip -r ../delivery/artifact.zip ./dataset
cd ..

