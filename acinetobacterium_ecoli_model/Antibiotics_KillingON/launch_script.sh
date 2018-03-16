#!/bin/bash

# $1 Renew Path
# $2 Output Folder

counter=1
while [ $counter -le 100 ]
do 
    java -Dde.renew.netPath=./launch_script.sh -jar $1/renew2.5/loader.jar script simulation_run
    
    mv track_high_dosage_antibiotics.csv $2/track_high_dosage_antibiotics_$counter.csv
    ((counter++))
    
done
    
echo "all done"
